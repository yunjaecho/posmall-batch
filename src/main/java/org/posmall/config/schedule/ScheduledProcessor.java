package org.posmall.config.schedule;

import org.posmall.jpa.entity.JobSchedule;
import org.posmall.jpa.entity.JobStatus;
import org.posmall.service.JobScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

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

    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();


    /**
     * ScheduledProcessor 생성후 초기 실행
     */
    @PostConstruct
    public void init() {
        jobSchedules = jobScheduleService.getJobSchedules();
        scheduler.setPoolSize(5);
        scheduler.initialize();

        createScheduleAll();
    }

    /**
     * 사용여부 Y인 스케쥴 정보 등록
     */
    public void createScheduleAll() {
        jobSchedules.stream()
                .filter(s -> ("Y").equals(s.getUseYn()))
                .forEach(s -> {
                    //setCustomScheduledFuture(scheduler.schedule(invokeServiceMethod(s), new CronTrigger(s.getJobCron())), s);
                    scheduler.schedule(invokeServiceMethod(s), new CronTrigger(s.getJobCron()));
                });
    }

    /**
     * 특정 JOB 스케쥴 중지
      * @param jobNo
     */
    public void stopJobSchedule(Long jobNo) {
        for(JobSchedule schedule : jobSchedules) {
            if (schedule.getJobNo() == jobNo) {
                schedule.setJobStatus(JobStatus.STOP);
            }
        }
    }

    /**
     * 스케쥴러 Shutdown 처리
     */
    public void shutdownSchedule() {
        scheduler.getScheduledExecutor().shutdown();

        for(JobSchedule schedule : jobSchedules) {
            schedule.setJobStatus(JobStatus.SHUTDOWN);
        }
    }


    /**
     * 스케쥴 테스크 Runnable 메소드
     * @param jobSchedule
     * @return
     */
    private Runnable invokeServiceMethod(JobSchedule jobSchedule) {
        return () -> {
            if (jobSchedule.getJobStatus() == JobStatus.START) {
                try {
                    MethodInvokingFactoryBean mBean = new MethodInvokingFactoryBean();
                    mBean.setTargetObject(_applicationContext.getBean(jobSchedule.getExecServiceName()));
                    mBean.setTargetMethod(jobSchedule.getExecServiceMethod());
                    mBean.setArguments(jobSchedule);
                    mBean.prepare();
                    mBean.invoke();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
