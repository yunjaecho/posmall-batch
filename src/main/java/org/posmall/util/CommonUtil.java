package org.posmall.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by USER on 2018-02-06.
 */
public class CommonUtil {

    /**
     * 현재 일시 날짜 포맷
     * @param format
     * @return
     */
    public static String formatNow(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }


    /**
     * 문자열 바이트 길이로 substr 처리
     * @param str
     * @param startLen
     * @param endLen
     * @return
     */
    public static String substr(String str, int startLen, int endLen)
    {
        if (str.length() < endLen) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int curLen = 0;
        String curChar;

        for (int i = 0; i < str.length(); i++)
        {
            curChar = str.substring(i, i + 1);
            curLen += curChar.getBytes().length;
            if (curLen > endLen)
                break;
            else {
                if (curLen > startLen) sb.append(curChar);
            }

        }
        return sb.toString();
    }

    /**
     * Resultset 객체 List로 변환
     * @param rs
     * @return
     * @throws SQLException
     */
    public static ArrayList<HashMap<String,Object>> convertResultSetToArrayList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

        while(rs.next()) {
            HashMap<String,Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    /**
     * 키, 값 쌍으로 Map 객체 return
     * @param key
     * @param value
     * @return
     */
    public static Map<String, Object> retHashMap(String key, Object value) {
        Map map = new HashMap<String, Object>();
        map.put(key, value);
        return map;
    }

    /**
     * Object Field 정보 조회
     * @param object
     * @return
     */
    public static List getFieldObject(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        return Arrays.stream(fields)
                .map(s -> {
                    Map<String, String> map = new HashMap<String, String>();
                    s.setAccessible(true);
                    try {
                        map.put(s.getName(), String.valueOf(s.get(object)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return map;
                }).collect(toList());
    }

}
