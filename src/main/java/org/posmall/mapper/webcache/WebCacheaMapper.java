package org.posmall.mapper.webcache;

import org.posmall.domain.TbRvasVo;
import org.posmall.domain.WebCacheVacctData;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2017-11-30.
 */
public interface WebCacheaMapper {
    public List<WebCacheVacctData> getWebCacheVacctData(Map params);

    public List<TbRvasVo> getTbRvasList();

    public void test();
}
