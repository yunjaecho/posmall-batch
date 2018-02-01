package org.posmall.mapper.posmall;

import org.posmall.domain.TbRvasVo;
import org.posmall.util.CamelCaseMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2017-11-21.
 */
public interface PartnerDataMapper {
    public List<Map> getPtCategoryList();

    public void updateTbRvasListIf(TbRvasVo tbRvasVo);


    public void updateTbRvasListIfTest();

    public void updateTbRvasListIfTest1();

    public void saveVacctOrderCancle(CamelCaseMap map);

    public List<CamelCaseMap> selectVacctOrderCancle();
}
