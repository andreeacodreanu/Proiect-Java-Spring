package com.springProject.service;

import com.springProject.model.Holiday;
import com.springProject.model.Role;
import com.springProject.model.User;
import com.springProject.model.WorkLog;
import com.springProject.repository.HolidayRepository;
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

@Service("holidayService")
public class HolidayService {

    private HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository =holidayRepository;

    }

    public List<Holiday> findHolidaysByUserAndStatusEquals(User user, Integer status) {

        return holidayRepository.findHolidaysByUserAndStatusEquals(user, status);
    }

    public Holiday saveHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }


}