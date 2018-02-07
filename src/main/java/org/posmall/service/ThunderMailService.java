package org.posmall.service;

import org.posmall.mapper.posmall.ThunderMailMapper;
import org.posmall.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2018-02-06.
 */
@Service
public class ThunderMailService {
    private Connection conn;

    @Autowired
    private ThunderMailMapper thunderMailMapper;

    private JdbcTemplate jdbcTemplate;

    /**
     * SMS 발송내역 인터페이스
     */
    public void saveScTranProcess(Map jobInfo) throws SQLException {
        String sql = "SELECT * FROM SC_LOG_" + CommonUtil.formatNow("yyyyMM") + " WHERE TR_SENDDATE >  DATE_ADD(NOW(), INTERVAL -1 HOUR) AND TR_ETC6 IN ('POS-MALL', 'FARMDB')";

        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);

        ArrayList<HashMap<String,Object>> list = CommonUtil.convertResultSetToArrayList(result);

        list.stream().forEach(s -> thunderMailMapper.insertScTran(s));
    }

    public void saveOrdDelvGuideSMS(Map jobInfo) {

    }



    @Autowired
    public void setDataSource(@Qualifier("thunderMailDataSource") DataSource thunderMailDataSource) throws SQLException {
        this.conn = thunderMailDataSource.getConnection();
        this.jdbcTemplate = new JdbcTemplate(thunderMailDataSource);
    }

}
