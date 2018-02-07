package org.posmall.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by USER on 2018-02-05.
 */
@Data
@Entity
@Table(name = "TB_JOB_EXEC_LOG")
public class TbJobExecLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_JOB_EXEC_LOG_SEQ")
    @SequenceGenerator(
            name="TB_JOB_EXEC_LOG_SEQ",
            sequenceName="TB_JOB_EXEC_LOG_SEQ",
            allocationSize = 1
    )
    @Column(name = "JOB_EXEC_NO")
    private Long jobExecNo;

    @Column(name = "JOB_NO")
    private Long JobNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DT")
    private Date startDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DT")
    private Date endDt;

    @Column(name = "STATUS")
    private String status;
}
