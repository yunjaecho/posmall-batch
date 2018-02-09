package org.posmall.service;

import org.posmall.jpa.entity.JobSchedule;
import org.posmall.jpa.entity.TbJobErrorLog;
import org.posmall.jpa.repositores.JobScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by USER on 2018-02-09.
 */
@Service
public class JobScheduleService {
    @Autowired
    private JobScheduleRepository repository;

    public List<JobSchedule> getJobSchedules() {
        return repository.findAll();
    }
}
