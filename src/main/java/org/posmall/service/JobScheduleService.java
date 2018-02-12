package org.posmall.service;

import org.posmall.config.schedule.ScheduledProcessor;
import org.posmall.jpa.entity.JobSchedule;
import org.posmall.jpa.repositores.JobScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by USER on 2018-02-09.
 */
@Service
public class JobScheduleService {
    @Autowired
    private JobScheduleRepository repository;

    @Autowired
    private ScheduledProcessor scheduledProcessor;

    /**
     * 데이터에 등록된 Job Schedule 정보 가져오기
     * @return
     */
    public List<JobSchedule> getJobSchedules() {
        return repository.findAll();
    }

    /**
     * 특정 Job 중지
     * @param jobNo
     */
    public void stopJobSchedule(Long jobNo) {
        scheduledProcessor.stopJobSchedule(jobNo);
    }

    /**
     * 스케쥴 서비스 ThreadPool Shutdown
     */
    public void shutdownSchedule() {
        scheduledProcessor.shutdownSchedule();
    }

    /**
     * 데이터에 등록된 Job Schedule ThreadPool 등록
     */
    public void createScheduleAll() {
        scheduledProcessor.createScheduleAll();
    }

}
