package com.springProject.repository;

import com.springProject.model.Holiday;
import com.springProject.model.Project;
import com.springProject.model.Role;
import com.springProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Map;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAll();
    @Query( "select u from User u inner join u.roles r where r.role = 'USER'")
    List<User> findAllByRoles(String role);

/*    @Query( "select u from User u inner join u.projects r where r.project_id = id")*/
    List<User> findAllByProjects(Project project);
    List<User> findUsersByProjectsIsNotContaining(Project project);
    User findUserById(int id);

    @Transactional
    @Modifying
    @Query(value="delete from project_user where project_id = :p_id and user_id = :u_id", nativeQuery = true)
    void deleteProjectFromUser(@Param("p_id") int p_id, @Param("u_id") int u_id);

    @Transactional
    @Modifying
    @Query(value="insert into project_user values (:p_id, :u_id)", nativeQuery = true)
    void updateProjectAndUser(@Param("p_id") int p_id, @Param("u_id") int u_id);


}