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
@RolesAllowed("user")
public class TaskResource {


    private final TaskService taskService;

    @Inject
    public TaskResource( TaskService taskService){
        this.taskService=taskService;
    }


    @GET
    public Uni<List<Task>> listaTareas (){
        return taskService.listForUser();
    }

    @GET
    @Path("{id}")
    public Uni<Task> tareasPorId(@PathParam("id") long id){
        return taskService.findById(id);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<Task> crearTarea(Task task){
        return taskService.create(task);
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Uni<Task> actualizarTarea (@PathParam("id")long id, Task task){
        task.id=id;
        return taskService.update(task);
    }

    @Delete
    @Path("{id}")
    public Uni<Void> eliminarTarea(@PathParam("id") long id){
        return taskService.delete(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}/complete")
    public Uni<Boolean> completarTarea(@PathParam("id")long id, boolean complete){
       return  taskService.setComplete(id, complete);
    }
}
