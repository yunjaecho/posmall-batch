package org.posmall.service.impl;

import org.posmall.domain.TbRvasVo;
import org.posmall.mapper.posmall.PartnerDataMapper;
import org.posmall.mapper.webcache.WebCacheaMapper;
import org.posmall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2017-12-20.
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private WebCacheaMapper webCacheaMapper;

    @Autowired
    private PartnerDataMapper partnerDataMapper;

    @Value("${webcache.parameter.orgBank}")
    private String orgBank;

    @Value("${webcache.parameter.orgCd}")
    private String orgCd;


    public Map<String, String> getTest() {
        insertPosmall(getWebcacheData());

        updateWebcache();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "성공");
        return resultMap;
    }

    @Transactional(transactionManager="webcacheTransactionManager")
    public List<TbRvasVo> getWebcacheData() {
        return webCacheaMapper.getTbRvasList();
    }

    @Transactional(transactionManager="posmallTransactionManager")
    public void insertPosmall(List<TbRvasVo> list) {
        list.stream().forEach(s -> partnerDataMapper.updateTbRvasListIf(s));
    }

    @Transactional(transactionManager="webcacheTransactionManager")
    public void updateWebcache() {
        webCacheaMapper.test();
    }
}