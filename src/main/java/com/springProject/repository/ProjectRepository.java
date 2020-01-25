package com.springProject.repository;

import com.springProject.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ProjectRepository")
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findProjectById(Integer id);
}