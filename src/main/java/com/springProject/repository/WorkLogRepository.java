package com.springProject.repository;

import com.springProject.model.User;
import com.springProject.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("workLogRepository")
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    List<WorkLog> findAllByCommentContains(String name);
    List<WorkLog> findAllByUser(User user);
    List<WorkLog> findAllByCommentContainsAndUser(String projectName,User user);

}