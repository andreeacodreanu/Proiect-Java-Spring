package com.springProject.controller;

import com.springProject.model.Project;
import com.springProject.model.Role;
import com.springProject.model.User;
import com.springProject.service.ProjectService;
import com.springProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value="/adminPanel", method = RequestMethod.GET)
    public ModelAndView adminPanel(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<User> emp = userService.findAllByRoles("USER");

        modelAndView.addObject("adminMessage", "USER ADMINISTRATIVE PANEL");
        modelAndView.addObject("employees", emp);
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "param", required=false) String email) {

        userService.deleteUser(email);
        return new ModelAndView("redirect:/adminPanel");

    }

    @RequestMapping(value="/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        user.setPassword("Parola1");
        user.setActive(1);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/addUser");

        return modelAndView;
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ModelAndView addEmployee(@Valid User user, BindingResult bindingResult) {

        userService.saveUser(user);
        return new ModelAndView("redirect:/adminPanel");
    }

    @RequestMapping(value="/projectsPanel", method = RequestMethod.GET)
    public ModelAndView projectsPanel(){
        ModelAndView modelAndView = new ModelAndView();

        List<Project> projects = projectService.findAll();

        modelAndView.addObject("adminMessage", "PROJECTS ADMINISTRATIVE PANEL");
        modelAndView.addObject("projects", projects);
        modelAndView.setViewName("admin/projectsPanel");
        return modelAndView;
    }

    @RequestMapping(value="/deleteProject", method = RequestMethod.POST)
    public ModelAndView deleteProject(@RequestParam(value = "param", required=false) int id) {

        projectService.deleteProject(id);
        return new ModelAndView("redirect:/projectsPanel");
    }

    @RequestMapping(value="/editProject", method = RequestMethod.POST)
    public ModelAndView editProject(@RequestParam(value = "param", required=false) int id) {
        ModelAndView modelAndView = new ModelAndView();

        Project project = projectService.findProjectById(id);
        modelAndView.addObject("project", project);
        List<User> users = userService.findAll();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/editProject");

        return modelAndView;
    }

    @RequestMapping(value="/addProject", method = RequestMethod.GET)
    public ModelAndView addProject() {
        ModelAndView modelAndView = new ModelAndView();

        Project project = new Project();
        modelAndView.addObject("project", project);
        modelAndView.setViewName("admin/addProject");

        return modelAndView;
    }

    @RequestMapping(value="/addProject", method = RequestMethod.POST)
    public ModelAndView addProject(@Valid Project project, BindingResult bindingResult) {

        project.setActive(1);
        projectService.saveProject(project);
        return new ModelAndView("redirect:/projectsPanel");
    }

//    @RequestMapping(value="/editProject", method = RequestMethod.POST)
//    public ModelAndView editProject(@RequestParam(value = "param", required=false) Project project) {
//        ModelAndView modelAndView = new ModelAndView();
//
//
//        return modelAndView;
//    }
}
