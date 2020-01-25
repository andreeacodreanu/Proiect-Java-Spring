package com.springProject.controller;

import javax.validation.Valid;

import com.springProject.model.Project;
import com.springProject.model.Role;
import com.springProject.model.User;
import com.springProject.model.WorkLog;
import com.springProject.service.ProjectService;
import com.springProject.service.UserService;
import com.springProject.service.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkLogService workLogService;
    @Autowired
    private ProjectService projectService;

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

        modelAndView.addObject("employee", user);


        modelAndView.setViewName("user/worklog-index");

        return modelAndView;
    }

}
