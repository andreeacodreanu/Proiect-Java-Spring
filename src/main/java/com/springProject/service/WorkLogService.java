package com.springProject.service;

import com.springProject.model.Role;
import com.springProject.model.User;
import com.springProject.model.WorkLog;
import com.springProject.repository.RoleRepository;
import com.springProject.repository.UserRepository;
import com.springProject.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.ccache.FileCredentialsCache;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service("workLogService")
public class WorkLogService {

    private WorkLogRepository workLogRepository;

    public WorkLogService(WorkLogRepository workLogRepository) {
        this.workLogRepository = workLogRepository;

    }

    public WorkLog saveWorkLog(WorkLog workLog, User user)  {

        LocalDate date = LocalDate.now();

        workLog.setDate(date.toString());
        workLog.setUser(user);
        return workLogRepository.save(workLog);
    }

    public List<WorkLog> findAllByCommentContains(String projectName) {

        return workLogRepository.findAllByCommentContains(projectName);
    }

}