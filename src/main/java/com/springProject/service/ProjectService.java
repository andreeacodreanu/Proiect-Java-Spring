package com.springProject.service;


import com.springProject.model.Project;
import com.springProject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("projectService")
public class ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    public Project findProjectById(int parseInt) {
        return projectRepository.findProjectById(parseInt);
    }
    public List<Project> findAll() { return projectRepository.findAll(); }

    public boolean deleteProject(Integer id) {

        try {
            Project proj = projectRepository.findProjectById(id);
            projectRepository.delete(proj);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}