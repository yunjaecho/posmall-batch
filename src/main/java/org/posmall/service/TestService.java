package org.posmall.service;

import org.posmall.domain.TbRvasVo;
import org.posmall.mapper.posmall.PartnerDataMapper;
import org.posmall.mapper.webcache.WebCacheaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2017-12-20.
 */
@Service
public class TestService {
    @Autowired
    private WebCacheaMapper webCacheaMapper;

    @Autowired
    private PartnerDataMapper partnerDataMapper;

    @Value("${webcache.parameter.orgBank}")
    private String orgBank;

    @Value("${webcache.parameter.orgCd}")
    private String orgCd;

    public Map<String, String> updateTestPosmall() {

        Map<String, String> resultMap = new HashMap<>();

        partnerDataMapper.updateTbRvasListIfTest1();
        partnerDataMapper.updateTbRvasListIfTest();
        resultMap.put("result", "실폐");

        return resultMap;
    }

    public  Map<String, String> saveCompositTest() {

        List<TbRvasVo> list = webCacheaMapper.getTbRvasList();

        list.stream().forEach(s -> partnerDataMapper.updateTbRvasListIf(s));

        webCacheaMapper.test();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "성공");
        return resultMap;
    }

    public Map<String, String> saveCompositMethodTest() {
        List<TbRvasVo> list = selectWebcacheData();

        insertPosmall(list);

        //updateWebcache();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "성공");
        return resultMap;
    }



    public List<TbRvasVo> selectWebcacheData() {
        return webCacheaMapper.getTbRvasList();
    }


    public void insertPosmall(List<TbRvasVo> list) {
        list.stream().forEach(s -> partnerDataMapper.updateTbRvasListIf(s));
    }

    public void updateWebcache() {
        webCacheaMapper.test();
    }
}
