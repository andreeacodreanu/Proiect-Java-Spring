package com.springProject.service;

import com.google.gson.JsonObject;
import com.springProject.model.Role;
import com.springProject.model.User;
import com.springProject.repository.RoleRepository;
import com.springProject.repository.UserRepository;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public int getUserRole (User user) {
        Set<Role> roles = user.getRoles();
        Role admin = new Role(1,"ADMIN");
        if (roles.contains(admin))
            return 1;
        else
            return 2;
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public List<User> getEmployees() {
        return userRepository.findAll();
    }

    public JsonArray getUsersAsJson(List<User> list) {

        JsonArray jsonArray = new JsonArray();

        for(User user : list) {
            JsonObject obj = new JsonObject();
            obj.addProperty("lastname", user.getLastName());
            obj.addProperty("firstname", user.getName());
            obj.addProperty("email", user.getEmail());
            obj.addProperty("active", user.getActive());
            obj.addProperty("id", user.getId());

            jsonArray.add(obj);
        }
        return jsonArray;
    }

    public boolean deleteUser(String email) {

        try {
            userRepository.delete(userRepository.findByEmail(email));
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}