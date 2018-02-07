package org.posmall.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.posmall.jpa.entity.TbJobErrorLog;
import org.posmall.jpa.entity.TbJobExecLog;
import org.posmall.jpa.repositores.TbJobErrorLogRepository;
import org.posmall.jpa.repositores.TbJobExecLogRepository;
import org.posmall.mapper.posmall.CommonMapper;
import org.posmall.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by USER on 2018-02-01.
 */
@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class BatchLoggingAspect {

    @Autowired
    private CommonMapper commonMapper;

    private Connection connection;

    @Autowired
    private TbJobErrorLogRepository errorLogRepos;

    @Autowired
    private TbJobExecLogRepository execLogRepos;

    /**
     *
     * @param joinPoint
     */
    @Before("execution(* org.posmall.service.*.*Process(..))")
    public void beforeLogging(JoinPoint joinPoint) {
        try {
            HashMap<String, String> paramMap = getJoinPointValues(joinPoint);
            String jobNo = "";

            jobNo = paramMap.get("jobNo");

            TbJobExecLog tbJobExecLog = new TbJobExecLog();
            tbJobExecLog.setJobNo(Long.parseLong(jobNo));
            tbJobExecLog.setStartDt(new Date());
            tbJobExecLog.setStatus("START");
            execLogRepos.save(tbJobExecLog);

            paramMap.put("jobExecNo", String.valueOf(tbJobExecLog.getJobExecNo()));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param joinPoint
     */
    @AfterReturning(pointcut="execution(* org.posmall.service.*.*Process(..))")
    public void afterLogging(JoinPoint joinPoint) {
        try {
            HashMap<String, String> paramMap = getJoinPointValues(joinPoint);
            String jobNo = "";
            String jobExecNo = "";

            jobNo = paramMap.get("jobNo");
            jobExecNo = paramMap.get("jobExecNo");

            TbJobExecLog tbJobExecLog = execLogRepos.findOne(Long.parseLong(jobExecNo));
            tbJobExecLog.setStatus("FINISH");
            tbJobExecLog.setEndDt(new Date());
            execLogRepos.save(tbJobExecLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param joinPoint
     * @param ex
     */
    //@AfterThrowing(pointcut="execution(* org.posmall.service.*.*(..))",throwing = "ex")
    @AfterThrowing(pointcut="execution(* org.posmall.service.*.*Process(..))",throwing = "ex")
    public void errorLogging(JoinPoint joinPoint, Throwable ex) {
        try {
            String jobNo = "";
            String errMsg = ex.getMessage();
            String errMsg1 = "";
            String errMsg2 = "";
            String jobExecNo = "";

            HashMap<String, String> paramMap = getJoinPointValues(joinPoint);
            jobNo = paramMap.get("jobNo");
            jobExecNo = paramMap.get("jobExecNo");

            errMsg1 = CommonUtil.substr(errMsg, 0,4000);
            errMsg2 = CommonUtil.substr(errMsg, 4001,8000);

            /*  JOB 에러 로그 기록(Insert)  */
            TbJobErrorLog tbJobErrorLog = new TbJobErrorLog();
            tbJobErrorLog.setJobNo(Long.parseLong(jobNo));
            tbJobErrorLog.setErrMsg(errMsg1);
            tbJobErrorLog.setErrMsg2(errMsg2);
            tbJobErrorLog.setCDt(new Date());
            //tbJobErrorLog.setErrContext(errMsg);
            errorLogRepos.save(tbJobErrorLog);


            /* JOB 실행 로그에 오류 완료 업데이트 처리 */
            TbJobExecLog tbJobExecLog = execLogRepos.findOne(Long.parseLong(jobExecNo));
            tbJobExecLog.setStatus("ERROR");
            tbJobExecLog.setEndDt(new Date());
            execLogRepos.save(tbJobExecLog);

            /*Map map = new HashMap<String, String>();
            map.put("jobNo", "1");
            map.put("errMsg", ex.getMessage());

            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO TB_JOB_ERROR_LOG (SEQ_NO,JOB_NO,ERR_MSG,C_DT)");
            sb.append(" SELECT");
            sb.append("       NVL(MAX(SEQ_NO),0) + 1 AS SEQ_NO,");
            sb.append("       ?,");
            sb.append("       ?,");
            sb.append("       SYSDATE");
            sb.append(" FROM  TB_JOB_ERROR_LOG");

            PreparedStatement pstmt = connection.prepareStatement(sb.toString());
            pstmt.setString(1, jobNo);
            pstmt.setString(2, ex.getMessage());
            pstmt.execute();

            connection.commit();*/

            //commonMapper.insertJobErrorLog(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //commonMapper.insertJobErrorLog(map);
    }

    /*@Autowired
    public void setDataSource(DataSource posmallDataSource) throws SQLException {
        this.connection = posmallDataSource.getConnection();
    }*/


    /**
     *
     * @param joinPoint
     * @return
     */
    private HashMap<String, String> getJoinPointValues(JoinPoint joinPoint) {
        Object[] signatureArgs = joinPoint.getArgs();

        if (signatureArgs != null && ("java.util.HashMap").equals(signatureArgs[0].getClass().getName())) {
            return (HashMap)signatureArgs[0];
        } else {
            return null;
        }
    }


}
