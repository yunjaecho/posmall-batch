package org.posmall.domain;

import lombok.Data;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by USER on 2018-02-09.
 */
@Data
public class CustomScheduledFuture {
    ScheduledFuture<?> scheduledFuture;

    private Long jobNo;

    private String jobNm;

    private String jobCron;

    private String useYn;

    private String jobDesc;

    private String execServiceName;

    private String execServiceMethod;

    private String jobStatus;
}
