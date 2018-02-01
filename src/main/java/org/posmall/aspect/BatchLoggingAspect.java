package org.posmall.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.posmall.mapper.posmall.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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


    //@AfterThrowing(pointcut="execution(* org.posmall.service.*.*(..))",throwing = "ex")
    @AfterThrowing(pointcut="execution(* org.posmall.service.VirtualVaccService.saveVacctOrderCancle(..))",throwing = "ex")
    public void errorLogging(JoinPoint joinPoint, Throwable ex) {
        try {
            Object[] signatureArgs = joinPoint.getArgs();

            String jobNo = "";
            if (signatureArgs != null && ("java.util.HashMap").equals(signatureArgs[0].getClass().getName())) {
                jobNo = (String)((HashMap)signatureArgs[0]).get("jobNo");
            }

            Map map = new HashMap<String, String>();
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

            connection.commit();
            //commonMapper.insertJobErrorLog(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //commonMapper.insertJobErrorLog(map);
    }

    @Autowired
    public void setDataSource(DataSource posmallDataSource) throws SQLException {
        this.connection = posmallDataSource.getConnection();
    }


}
