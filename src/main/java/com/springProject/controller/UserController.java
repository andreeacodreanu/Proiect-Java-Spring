package com.springProject.controller;

import javax.validation.Valid;

import com.springProject.model.*;
import com.springProject.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkLogService workLogService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private ContactInfoService contactInfoService;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value="/worklog", method = RequestMethod.GET)
    public ModelAndView worklog(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        WorkLog workLog = new WorkLog();

        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName());
        modelAndView.addObject("projectsList", user.getProjects());
        modelAndView.addObject("worklog", workLog);
        modelAndView.setViewName("user/home");

        logger.info("WORKLOG");

        return modelAndView;
    }

    @RequestMapping(value = "/worklog", method = RequestMethod.POST)
    public ModelAndView addWorkLog(@RequestParam(value = "project", required = false) String project, @Valid WorkLog workLog) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Project project1 = projectService.findProjectById(Integer.parseInt(project));
        String s = " - ";
        workLog.setComment(project1.getName().concat(s.concat(workLog.getComment())));

        workLogService.saveWorkLog(workLog,user);

        return new ModelAndView("redirect:/worklog/index");
    }

    @RequestMapping(value = "/worklog/index", method = RequestMethod.GET)
    public ModelAndView workLogIndex() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Map<Project, List<WorkLog>> projectListWithWorkLog = new HashMap<Project, List<WorkLog> >();
        Set<Project> projects = user.getProjects();

        for (Project project :
             projects) {

            String s1 = " - ";

            List<WorkLog> workLogList = workLogService.findAllByCommentContains(project.getName().concat(s1));
            if(workLogList != null) {
                projectListWithWorkLog.put(project, workLogList);
            }

        }

        modelAndView.addObject("projectsWithWorklogs", projectListWithWorkLog);

        modelAndView.setViewName("user/worklog-index");

        return modelAndView;
    }

    @RequestMapping(value = "/holidays/index", method = RequestMethod.GET)
    public ModelAndView holidaysIndex() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        List<Holiday> holidaysList = holidayService.findHolidaysByUser(user);

        modelAndView.addObject("holidaysList", holidaysList);
        modelAndView.setViewName("user/holidays-index");
        logger.info("HOLIDAY INDEX");

        return modelAndView;
    }

    @RequestMapping(value = "/add-holidays", method = RequestMethod.GET)
    public ModelAndView holiday() {
        ModelAndView modelAndView = new ModelAndView();
        List<String> holidaysList = new ArrayList<String>();
        holidaysList.add("Concediu medical");
        holidaysList.add("Concediu de odihna");
        holidaysList.add("Concediu de eveniment special");
        Holiday holiday = new Holiday();

        modelAndView.addObject("holidaysList", holidaysList);
        modelAndView.addObject("holiday", holiday);
        modelAndView.setViewName("user/add-holidays");
        return modelAndView;
    }

    @RequestMapping(value = "/add-holidays", method = RequestMethod.POST)
    public ModelAndView addHolidays(Holiday holiday, @RequestParam(value = "holidayType", required = false) String holidayType) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        holiday.setType(holidayType);
        holiday.setStatus(0);
        holiday.setUser(user);
        holidayService.saveHoliday(holiday);
        return new ModelAndView("redirect:/holidays/index");
    }

    @RequestMapping(value="/editContactInfo", method = RequestMethod.GET)
    public ModelAndView editContactInfo() {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Integer contactInfoId = (user.getContactInfo()).getId();
        ContactInfo contactInfo = contactInfoService.findById(contactInfoId);

        modelAndView.addObject("info", contactInfo);

        modelAndView.setViewName("user/contactInfo");

        return modelAndView;
    }

    @RequestMapping(value="/editContactInfoSave", method = RequestMethod.POST)
    public ModelAndView editContactInfoSave(@Valid ContactInfo contactInfo, @RequestParam(value = "param", required=false) Integer id) {

        contactInfo.setId(id);
        contactInfoService.updateContactInfo(contactInfo);

        return new ModelAndView("redirect:/editContactInfo");
    }


}
