package com.springProject.repository;

import com.springProject.model.Role;
import com.springProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();
    @Query( "select u from User u inner join u.roles r where r.role = 'USER'")
    List<User> findAllByRoles(String role);


}