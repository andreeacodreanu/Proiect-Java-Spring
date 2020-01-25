package com.springProject.service;


import com.springProject.model.Project;
import com.springProject.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("projectService")
public class ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;

    }

    public Project findProjectById(int parseInt) {
        return projectRepository.findProjectById(parseInt);
    }
}