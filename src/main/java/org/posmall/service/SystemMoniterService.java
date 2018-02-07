package org.posmall.service;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.posmall.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by USER on 2018-02-07.
 */
@Service
public class SystemMoniterService {
    OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

    @Autowired
    private DataSource posmallDataSourceSpied;

    @Autowired
    private DataSource webcacheDataSourceSpied;

    @Autowired
    private DataSource thunderMailDataSource;

    public List getSystemMXBean() {
        return Arrays.stream(operatingSystemMXBean.getClass().getDeclaredMethods())
                .filter(s -> s.getName().startsWith("get") && Modifier.isPublic(s.getModifiers()))
                .map(s -> {
                    Map<String, String> map = new HashMap<String, String>();
                    s.setAccessible(true);
                    try {
                        map.put(s.getName(), String.valueOf(s.invoke(operatingSystemMXBean)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return map;
                }).collect(toList());
    }

    /**
     * 시스템에서 사용하는 Datasource Pool 정보 조회
     * @return
     */
    public Map getDatabasePoolInfo() {
        Map resultMap = new HashMap<String, List>();
        resultMap.put("PosmallDataSource", getPoolInfo(posmallDataSourceSpied));
        resultMap.put("WebcacheDataSource", getPoolInfo(webcacheDataSourceSpied));
        resultMap.put("ThunderMailDataSource", getPoolInfo(thunderMailDataSource));
        return resultMap;
    }

    /**
     * 하나의 Datasource Pool 정보 조회
     * @param dataSource
     * @return
     */
    private List getPoolInfo(DataSource dataSource) {
        List poolInfos = new ArrayList<HashMap<String, Object>>();

        PoolConfiguration poolConfig = dataSource.getPoolProperties();

        poolInfos.add(CommonUtil.retHashMap("Pool_Active", dataSource.getActive()));
        poolInfos.add(CommonUtil.retHashMap("Pool_Idle", dataSource.getIdle()));
        poolInfos.add(CommonUtil.retHashMap("Pool_BorrowedCount", dataSource.getBorrowedCount()));
        poolInfos.add(CommonUtil.retHashMap("Pool_Size", dataSource.getSize()));
        poolInfos.add(CommonUtil.retHashMap("Pool_WaitCount", dataSource.getWaitCount()));
        poolInfos.add(CommonUtil.retHashMap("Pool_MaxActive", dataSource.getMaxActive()));
        poolInfos.add(CommonUtil.retHashMap("Pool_MaxAge", dataSource.getMaxAge()));
        poolInfos.add(CommonUtil.retHashMap("Pool_MaxIdle", dataSource.getMaxIdle()));
        poolInfos.add(CommonUtil.retHashMap("Pool_MaxWait", dataSource.getMaxWait()));
        poolInfos.add(CommonUtil.retHashMap("Pool_Name", dataSource.getPoolName()));

        poolInfos.add(CommonUtil.retHashMap("Database_Username", poolConfig.getUsername()));
        poolInfos.add(CommonUtil.retHashMap("Database_Password", poolConfig.getPassword()));
        poolInfos.add(CommonUtil.retHashMap("Database_DriverClassName", poolConfig.getDriverClassName()));
        poolInfos.add(CommonUtil.retHashMap("Database_Url", poolConfig.getUrl()));

        return poolInfos;
    }

}
