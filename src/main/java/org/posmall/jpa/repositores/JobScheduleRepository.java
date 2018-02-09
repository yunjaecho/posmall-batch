package org.posmall.jpa.repositores;

import org.posmall.jpa.entity.JobSchedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by USER on 2018-02-09.
 */
public interface JobScheduleRepository extends CrudRepository<JobSchedule, Long> {
    @Override
    public List<JobSchedule> findAll();
}
