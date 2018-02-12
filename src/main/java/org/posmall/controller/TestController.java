package org.posmall.controller;

import org.posmall.jpa.entity.JobSchedule;
import org.posmall.service.SystemMoniterService;
import org.posmall.service.TestService;
import org.posmall.service.ThunderMailService;
import org.posmall.service.VirtualVaccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2017-12-20.
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private VirtualVaccService virtualVaccService;

    @Autowired
    private ThunderMailService thunderMailService;

    @Autowired
    private SystemMoniterService systemMoniterService;

    @GetMapping("/getTest")
    public Map<String, String> getTest() {
        return testService.updateTestPosmall();
    }

    @GetMapping("/saveCompositTest")
    public Map<String, String> saveCompositTest() {
        return testService.saveCompositTest();
    }

    @GetMapping("/saveCompositMethodTest")
    public Map<String, String> saveCompositMethodTest() {
        return testService.saveCompositMethodTest();
    }

    @GetMapping("/saveCompositMethodTest2")
    public Map<String, String> saveCompositMethodTest2() {
        return testService.saveCompositMethodTest2();
    }

    @GetMapping("/getSystemMXBean")
    public List getSystemMXBean() {
        return systemMoniterService.getSystemMXBean();
    }

    @GetMapping("/getDatabasePoolInfo")
    public Map getDatabasePoolInfo() {
        return systemMoniterService.getDatabasePoolInfo();
    }




    /**
     * 가상계좌 입금기한 초과 데이터 취소 처리
     */
    @GetMapping("/processVacctOrderCancle")
    public void processVacctOrderCancle() {
        //Map jobInfo = new HashMap<String, String>();
        //jobInfo.put("jobNo", "1");
        JobSchedule jobSchedule = new JobSchedule();
        jobSchedule.setJobNo(1L);

        virtualVaccService.saveVacctOrderCancleProcess(jobSchedule);
    }

    /**
     * SMS 발송내역 인터페이스
     * @throws SQLException
     */
    @GetMapping("/saveScTranProcess")
    public void saveScTranProcess() throws SQLException {
        //Map jobInfo = new HashMap<String, String>();
        //jobInfo.put("jobNo", "2");
        JobSchedule jobSchedule = new JobSchedule();
        jobSchedule.setJobNo(2L);

        thunderMailService.saveScTranProcess(jobSchedule);
    }

}
