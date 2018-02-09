package org.posmall.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * JOB 및 스케줄 정보
 * Created by USER on 2018-02-09.
 */
@Data
@Immutable
@Entity
@Table(name = "TB_JOB_SCHEDULE")
public class JobSchedule {
    @Id
    @Column(name = "JOB_NO")
    private Long jobNo;

    @Column(name = "JOB_NM")
    private String jobNm;

    @Column(name = "JOB_CRON")
    private String jobCron;

    @Column(name = "USE_YN")
    private String useYn;

    @Column(name = "JOB_DESC")
    private String jobDesc;

    @Column(name = "EXEC_SERVICE_NAME")
    private String execServiceName;

    @Column(name = "EXEC_SERVICE_METHOD")
    private String execServiceMethod;

    @Override
    public String toString() {
        return "JobSchedule{" +
                "jobNo=" + jobNo +
                ", jobNm='" + jobNm + '\'' +
                ", jobCron='" + jobCron + '\'' +
                ", useYn='" + useYn + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", execServiceName='" + execServiceName + '\'' +
                ", execServiceMethod='" + execServiceMethod + '\'' +
                '}';
    }
}
