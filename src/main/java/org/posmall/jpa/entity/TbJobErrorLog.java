package org.posmall.jpa.entity;

import lombok.Data;
import org.hibernate.engine.jdbc.CharacterStream;

import javax.persistence.*;
import java.io.StringReader;
import java.sql.Clob;
import java.util.Date;

/**
 * Created by USER on 2018-02-02.
 */
@Data
@Entity
@Table(name = "TB_JOB_ERROR_LOG")
public class TbJobErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_JOB_ERROR_LOG_SEQ")
    @SequenceGenerator(
            name="TB_JOB_ERROR_LOG_SEQ",
            sequenceName="TB_JOB_ERROR_LOG_SEQ",
            allocationSize = 1
    )
    @Column(name = "SEQ_NO")
    private Long seqNo;

    @Column(name = "JOB_NO")
    private Long jobNo;

    //@Lob
    //@Column(name = "ERR_MSG" , columnDefinition="CLOB", nullable=true)
    //@Basic(fetch = FetchType.LAZY)
    @Column(name = "ERR_MSG'")
    private String errMsg;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_DT")
    private Date cDt;
}
