package org.posmall.controller;

import org.posmall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by USER on 2017-12-20.
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

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

}
