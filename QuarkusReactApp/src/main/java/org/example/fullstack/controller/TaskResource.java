package org.example.fullstack.controller;

import com.oracle.svm.core.annotate.Delete;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.fullstack.dto.task.Task;
import org.example.fullstack.services.task.TaskService;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Path("/api/v1/tasks/")
@RolesAllowed("ROLE_USER")
public class TaskResource {


    private final TaskService taskService;

    @Inject
    public TaskResource( TaskService taskService){
        this.taskService=taskService;
    }


    @GET
    @RolesAllowed("ROLE_USER")
    public Uni<List<Task>> listaTareas (){
        return taskService.listForUser();
    }

    @GET
    @Path("{id}")
    @RolesAllowed("ROLE_USER")
    public Uni<Task> tareasPorId(@PathParam("id") long id){
        return taskService.findById(id);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    @RolesAllowed("ROLE_USER")
    public Uni<Task> crearTarea(Task task){
        return taskService.create(task);
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @RolesAllowed("ROLE_USER")
    public Uni<Task> actualizarTarea (@PathParam("id")long id, Task task){

        return taskService.update(task, id);
    }

    @Delete
    @Path("{id}")
    @RolesAllowed("ROLE_USER")
    public Uni<Void> eliminarTarea(@PathParam("id") long id){
        return taskService.delete(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("complete/{id}/{complete}")
    @RolesAllowed("ROLE_USER")
    public Uni<Task> completarTarea(@PathParam("id")long id, @PathParam("complete")boolean complete){
        System.out.println("llego");
       return  taskService.setComplete(id, complete);
    }
}
