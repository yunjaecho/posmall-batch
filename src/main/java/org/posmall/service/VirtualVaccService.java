package org.posmall.service;

import org.posmall.domain.WebCacheVacctVo;
import org.posmall.mapper.posmall.PartnerDataMapper;
import org.posmall.mapper.webcache.WebCacheaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2018-01-24.
 */
@Service
public class VirtualVaccService {

    @Autowired
    private WebCacheaMapper webCacheaMapper;

    @Autowired
    private PartnerDataMapper partnerDataMapper;

    @Value(value = "webcache.parameter.orgBank")
    private String orgBank;

    @Value(value = "webcache.parameter.orgCd")
    private String orgCd;


    /**
     * 가상계좌 입금처리
     */
    public void vaccDepositProcess(Map jobInfo) {
        Map params = new HashMap<String, String>();
        params.put("orgBank", orgBank);
        params.put("orgCd", orgCd);

        List<WebCacheVacctVo> list = webCacheaMapper.getWebCacheVacctData(params);
    }

    /**
     * 가상계좌 입금기간 일시 초과 데이터 조회 및 처리
     */
    public void saveVacctOrderCancleProcess(Map jobInfo) {
            partnerDataMapper.selectVacctOrderCancle()
                    .stream()
                    .forEach(s -> partnerDataMapper.saveVacctOrderCancle(s));
    }
}
