package org.posmall.mapper.webcache;

import org.posmall.domain.TbRvasVo;
import org.posmall.domain.WebCacheVacctVo;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2017-11-30.
 */
public interface WebCacheaMapper {
    public List<WebCacheVacctVo> getWebCacheVacctData(Map params);

    public List<TbRvasVo> getTbRvasList();

    public void test();

    public void test2();
}
