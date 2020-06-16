package com.springProject.controllers;

import com.springProject.controller.AdminController;
import com.springProject.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    AdminController adminController;

    @Autowired
    UserController userController;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkHolidays() throws Exception {
//        http://localhost:8080/pendingHolidays?param=15
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        mockMvc.perform(post("/pendingHolidays").param("param","15"))
                .andExpect(status().isOk());
    }


    @Test
    public void checkWorklog() throws Exception {
//        /addProject
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        mockMvc.perform(get("/addProject"))
                .andExpect(status().isOk());
}
}
