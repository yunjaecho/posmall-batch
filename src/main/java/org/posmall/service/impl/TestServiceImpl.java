package org.posmall.service.impl;

import org.posmall.domain.TbRvasVo;
import org.posmall.mapper.posmall.PartnerDataMapper;
import org.posmall.mapper.webcache.WebCacheaMapper;
import org.posmall.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

    @Autowired
    @Qualifier("posmallTransactionManager")
    private PlatformTransactionManager posmallTransactionManager;

    @Autowired
    @Qualifier("webcacheTransactionManager")
    private PlatformTransactionManager webcacheTransactionManager;


    @Transactional
    public Map<String, String> getTest() {
//        insertPosmall(getWebcacheData());
//
//        updateWebcache();

        TransactionDefinition transactionDefinition1 = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus1 = posmallTransactionManager.getTransaction(transactionDefinition1);
//
        try {
            List<TbRvasVo> list = webCacheaMapper.getTbRvasList();
            list.stream().forEach(s -> partnerDataMapper.updateTbRvasListIf(s));
            webCacheaMapper.test();

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("result", "성공");
            return resultMap;
        } catch (Exception e) {
            posmallTransactionManager.rollback();
            webcacheTransactionManager.rollback();
        }

    }


    public List<TbRvasVo> getWebcacheData() {
        return webCacheaMapper.getTbRvasList();
    }


    public void insertPosmall(List<TbRvasVo> list) {
        list.stream().forEach(s -> partnerDataMapper.updateTbRvasListIf(s));
    }

    public void updateWebcache() {
        webCacheaMapper.test();
    }
}
