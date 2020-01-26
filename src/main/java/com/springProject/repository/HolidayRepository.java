package com.springProject.repository;

import com.springProject.model.Holiday;
import com.springProject.model.User;
import com.springProject.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository("holidayRepository")
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    List<Holiday> findHolidaysByUserAndStatusEquals(User user, Integer status);
}