package com.springProject.controller;

import com.springProject.model.*;
import com.springProject.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;


@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private WorkLogService worklogService;
    @Autowired
    private ContactInfoService contactInfoService;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("/adminPanel/{pageNum}")
    public String viewPage(Model model,
                           @PathVariable(name = "pageNum") int pageNum,
                           @Param("sortField") String sortField,
                           @Param("sortDir") String sortDir) {

        Page<User> page = userService.listAll(pageNum, sortField, sortDir);

        List<User> emp = page.getContent();

        model.addAttribute("adminMessage", "USER ADMINISTRATIVE PANEL");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("employees", emp);
        logger.info("ADMIN PANEL");
        return "/admin/home";
    }


    @RequestMapping("/adminPanel")
    public String viewHomePage(Model model) {
        return viewPage(model, 1, "name", "asc");
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
    public ModelAndView addEmployee(User user) {

        user.setPassword("Parola1");
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setCity("");
        contactInfo.setStreet("");
        contactInfo.setPhone("");
        contactInfo.setStreetNumber(0);
        user.setContactInfo(contactInfo);
//        contactInfo.setUser(user);
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
        logger.info("PROJECTS PANEL");
        return modelAndView;
    }

    @RequestMapping(value="/deleteProject", method = RequestMethod.POST)
    public ModelAndView deleteProject(@RequestParam(value = "param", required=false) int id) {

        projectService.deleteProject(id);
        return new ModelAndView("redirect:/projectsPanel");
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

    @RequestMapping(value="/editProject", method = RequestMethod.POST)
    public ModelAndView editProject(@RequestParam(value = "param", required=false) Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        Project project = projectService.findProjectById(id);
        modelAndView.addObject("project", project);
        List<User> users = userService.findAllByRoles("USER");
        //modelAndView.addObject("users", users);

//        Set<User> l = project.getUsers();
//        for(User u: l) {
//            System.out.println(u.getId());
//        }
        List<User> allocUsers = userService.findAllByProjects(project);

        for (User u: users) {
            if (allocUsers.contains(u)){
                u.setActive(1);
            }
            else
                u.setActive(0);
        }

        modelAndView.addObject("users", users);
        modelAndView.setViewName("admin/editProject");

        return modelAndView;
    }

    @RequestMapping(value="/editProjectSave", method = RequestMethod.POST)
    public ModelAndView editProjectSave(@RequestParam(value="present[]") String[] myParams,@RequestParam(value = "param", required=false) Integer id,@Valid Project project) {

        projectService.saveProject(project);

        int idP = id;

        List<User> usersList = userService.findAll();
        List<String> idList = Arrays.asList(myParams);

        for (User user : usersList) {

            userService.deleteProjectFromUser(idP, user.getId());

            Integer userId = user.getId();

            if (idList.contains(userId.toString())) {
                Set<Project> userProjects = user.getProjects();
                userProjects.add(project);
                user.setProjects(userProjects);
                userService.updateUserProjects(user);
//              userService.updateProjectAndUser(idP,user.getId());
            }
        }

        return new ModelAndView("redirect:/projectsPanel");
    }

    @RequestMapping(value="/pendingHolidays", method = RequestMethod.POST)
    public ModelAndView checkHolidays(@RequestParam(value = "param", required=false) int id) {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.findUserById(id);
//        List<Holiday> holidays = holidayService.findHolidaysByUser(user);
        List<Holiday> holidays = holidayService.findHolidaysByUserAndStatus(user,0);
        modelAndView.addObject("holidays", holidays);
        modelAndView.addObject("adminMessage", "HOLIDAYS ADMINISTRATIVE PANEL");

        modelAndView.setViewName("admin/pendingHolidays");
        return modelAndView;
    }

    @RequestMapping(value="/approveHoliday", method = RequestMethod.POST)
    public ModelAndView approveHoliday(@RequestParam(value = "param", required=false) int id) {

        Holiday holiday = holidayService.findHolidayById(id);
        holiday.setStatus(1);
        holidayService.saveHoliday(holiday);

        return new ModelAndView("redirect:/adminPanel");
    }

    @RequestMapping(value="/rejectHoliday", method = RequestMethod.POST)
    public ModelAndView rejectHoliday(@RequestParam(value = "param", required=false) int id) {

        Holiday holiday = holidayService.findHolidayById(id);
        holiday.setStatus(2);
        holidayService.saveHoliday(holiday);

        return new ModelAndView("redirect:/adminPanel");
    }


    @RequestMapping(value = "/usersReports", method = RequestMethod.GET)
    public ModelAndView usersReports() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> emp = userService.findAllByRoles("USER");
        Map<String,Integer> employees = new HashMap<String,Integer>();

//        int m = Calendar.getInstance().get(Calendar.MONTH);
//        m++;
//        String mm = ((Integer) m).toString();
//        int y = Calendar.getInstance().get(Calendar.YEAR);
//        String yy = ((Integer) y).toString();
//        String date = new String();
//        if(m > 9) {
//            date = yy.concat("-").concat(mm).concat("-").concat("01");
//        }else {
//            date = yy.concat("-").concat("0").concat(mm).concat("-").concat("01");
//        }

        for (User user:
             emp) {
            List<WorkLog> worklogs = worklogService.findAllByUser(user);
            int sum = 0;
            for (WorkLog worklog:
                 worklogs) {
                    sum = sum + worklog.getHours();
            }

            employees.put(user.getName(),sum);
        }

        modelAndView.addObject("adminMessage", "USERS REPORTS");
        modelAndView.addObject("employees", employees);
        modelAndView.setViewName("admin/usersReports");
        return modelAndView;
    }
}
