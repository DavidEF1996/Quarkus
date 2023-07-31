package org.example.fullstack.controller;


import com.oracle.svm.core.annotate.Delete;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.fullstack.dto.project.Project;
import org.example.fullstack.dto.user.User;
import org.example.fullstack.services.project.ProjectService;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Path("/api/v1/projects")
@RolesAllowed("ROLE_USER")
public class ProjectResource {

    private final ProjectService projectService;

    @Inject
    public ProjectResource(ProjectService projectService){
        this.projectService=projectService;
    }


    @GET
    @RolesAllowed("ROLE_USER")
    public Uni<List<Project>> get(){
        return projectService.listForUser();
    }

    @GET
    @Path("{id}")
    @RolesAllowed("ROLE_USER")
    public Uni<Project> getById (@PathParam("id") long id){
        return projectService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    @RolesAllowed("ROLE_USER")
    public Uni<Project> createProject (Project project){
        return projectService.create(project);
    }


    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Path("{id}")
    @RolesAllowed("ROLE_USER")
    public Uni<Project> updateProject (@PathParam("id") long id, Project project){

        return projectService.update(project, id);
    }


    @Delete
    @RolesAllowed("ROLE_USER")
    @Path("{id}")
    public Uni<Void> deleteProject(@PathParam("id")long id){
        return projectService.delete(id);
    }
}
