package org.posmall.schedule;

import org.posmall.domain.CustomScheduledFuture;
import org.posmall.jpa.entity.JobSchedule;
import org.posmall.service.JobScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by USER on 2018-02-09.
 */
@Component
//public class ScheduledProcessor implements SchedulingConfigurer {
public class ScheduledProcessor {

    @Autowired
    private JobScheduleService  jobScheduleService;

    @Autowired
    private ApplicationContext _applicationContext;

    private List<JobSchedule> jobSchedules;

    private List<CustomScheduledFuture> customScheduledFutures;

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

    /**
     * ScheduledProcessor 생성후 초기 실행
     */
    @PostConstruct
    public void init() {
        customScheduledFutures = new ArrayList<>();
        jobSchedules = jobScheduleService.getJobSchedules();
        scheduler.setPoolSize(5);
        scheduler.initialize();

        createScheduleAll();
    }

    /**
     * 사용여부 Y인 스케쥴 정보 등록
     */
    public void createScheduleAll() {
        jobSchedules.stream().filter(s -> ("Y").equals(s.getUseYn())).forEach(s -> {
            setCustomScheduledFuture(scheduler.schedule(invokeServiceMethod(s), new CronTrigger(s.getJobCron())), s);
        });
    }

    /**
     * 특정 JOB 스케쥴 중지
      * @param jobNo
     */
    public void stopSchedule(Long jobNo) {
        for(CustomScheduledFuture scheduledFuture : customScheduledFutures) {
            if (scheduledFuture.getJobNo() == jobNo) {
                if(!scheduledFuture.getScheduledFuture().isCancelled()) {
                    scheduledFuture.getScheduledFuture().cancel(false);
                    scheduledFuture.setJobStatus("STOP");
                }
            }
        }
    }

    /**
     * 스케쥴러 중지 처리
     */
    public void shutdownSchedule() {
        scheduler.getScheduledExecutor().shutdown();
    }


    /**
     * 스케쥴 테스크 Runnable 메소드
     * @param jobSchedule
     * @return
     */
    private Runnable invokeServiceMethod(JobSchedule jobSchedule) {
        return () -> {
            try {
                Map params = new HashMap<String, String>();
                params.put("jobNo", String.valueOf(jobSchedule.getJobNo()));
                params.put("jobNm", jobSchedule.getJobNm());

                MethodInvokingFactoryBean mBean = new MethodInvokingFactoryBean();
                mBean.setTargetObject(_applicationContext.getBean(jobSchedule.getExecServiceName()));
                mBean.setTargetMethod(jobSchedule.getExecServiceMethod());
                mBean.setArguments(params);
                mBean.prepare();
                mBean.invoke();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * 스케쥴 정보 데이터 셋팅
     * @param scheduledFuture
     * @param jobSchedule
     */
    private void setCustomScheduledFuture(ScheduledFuture<?> scheduledFuture, JobSchedule jobSchedule) {
        CustomScheduledFuture  customScheduledFuture = new CustomScheduledFuture();
        customScheduledFuture.setScheduledFuture(scheduledFuture);
        customScheduledFuture.setJobNo(jobSchedule.getJobNo());
        customScheduledFuture.setJobNm(jobSchedule.getJobNm());
        customScheduledFuture.setJobCron(jobSchedule.getJobCron());
        customScheduledFuture.setExecServiceName(jobSchedule.getExecServiceName());
        customScheduledFuture.setExecServiceMethod(jobSchedule.getExecServiceMethod());
        customScheduledFuture.setJobStatus("START");
        customScheduledFutures.add(customScheduledFuture);
    }



}
