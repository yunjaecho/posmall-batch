package org.posmall.controller;

import org.posmall.service.TestService;
import org.posmall.service.VirtualVaccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    /**
     * 가상계좌 입금기한 초과 데이터 취소 처리
     */
    @GetMapping("/processVacctOrderCancle")
    public void processVacctOrderCancle() {
        Map jobInfo = new HashMap<String, String>();
        jobInfo.put("jobNo", "1");

        virtualVaccService.saveVacctOrderCancle(jobInfo);
    }

}
