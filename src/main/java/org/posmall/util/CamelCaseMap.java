package org.posmall.util;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by USER on 2018-01-31.
 */
public class CamelCaseMap extends ListOrderedMap {
    /** serialVersionUID */
    private static final long serialVersionUID = -7700790403928325865L;

    /**
     * key 에 대하여 소문자로 변환하여 super.put
     * (ListOrderedMap) 을 호출한다.
     * @param key
     *        - '_' 가 포함된 변수명
     * @param value
     *        - 명시된 key 에 대한 값 (변경 없음)
     * @return previous value associated with specified
     *         key, or null if there was no mapping for
     *         key
     */
    public Object put(Object key, Object value) {
        return super.put(toCamelCase((String) key), value);
    }

    private String toProperCase(String s, boolean isCapital) {
        String rtnValue = "";

        if(isCapital){
            rtnValue = s.substring(0, 1).toUpperCase() +
                    s.substring(1).toLowerCase();
        }else{
            rtnValue = s.toLowerCase();
        }
        return rtnValue;
    }


    private String toCamelCase(String s){
        String[] parts = s.split("_");
        StringBuilder camelCaseString = new StringBuilder();

        for (int i = 0; i < parts.length ; i++) {
            String part = parts[i];
            camelCaseString.append(toProperCase(part, (i != 0 ? true : false))) ;
        }

        return camelCaseString.toString();
    }

}
