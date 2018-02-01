package org.posmall.service;

import org.posmall.domain.TbRvasVo;
import org.posmall.mapper.posmall.PartnerDataMapper;
import org.posmall.mapper.webcache.WebCacheaMapper;
import org.posmall.util.CamelCaseMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String  saveVaccDeposit() {
        List<TbRvasVo> list = webCacheaMapper.getTbRvasList();

        //list.stream().forEach(s -> partnerDataMapper.updateTbRvasListIf(s));

        return null;
    }

    /**
     * 가상계좌 입금기간 일시 초과 데이터 조회 및 처리
     */
    public void saveVacctOrderCancle(Map jobInfo) {
            partnerDataMapper.selectVacctOrderCancle()
                    .stream()
                    .forEach(s -> partnerDataMapper.saveVacctOrderCancle(s));
    }

    /**
     * 가상계좌 입금기간 일시 초과 데이터 취소 처리
     */
    /*public void saveVacctOrderCancle(CamelCaseMap map) {
        try {
            partnerDataMapper.saveVacctOrderCancle(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/


}
