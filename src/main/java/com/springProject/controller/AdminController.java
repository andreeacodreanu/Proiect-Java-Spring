package com.springProject.controller;

import com.google.gson.JsonArray;
import com.springProject.model.User;
import com.springProject.model.WorkLog;
import com.springProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/adminPanel", method = RequestMethod.GET)
    public ModelAndView adminPanel(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<User> emp = userService.getEmployees();
        JsonArray json = userService.getUsersAsJson(emp);

        modelAndView.addObject("employees", emp);
        modelAndView.addObject("adminMessage", "USER ADMINISTRATIVE PANEL");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "param", required=false) String email) {
        ModelAndView modelAndView = new ModelAndView();

        List<User> emp = userService.getEmployees();
        modelAndView.addObject("employees", emp);
        modelAndView.addObject("adminMessage", "USER ADMINISTRATIVE PANEL");
        userService.deleteUser(email);
        modelAndView.setViewName("admin/home");

        return modelAndView;

    }
}
