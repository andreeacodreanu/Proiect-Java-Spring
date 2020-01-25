package com.springProject.repository;

import com.springProject.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("workLogRepository")
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {


}