package org.example.fullstack.controller;


import com.oracle.svm.core.annotate.Delete;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.fullstack.dto.user.User;
import org.example.fullstack.services.user.PasswordChange;
import org.example.fullstack.services.user.UserService;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@Path("/api/v1/users")
@RolesAllowed("admin")
public class UserResource {

    private final UserService userService;

    @Inject
    public UserResource(UserService userService){
        this.userService = userService;
    }


    @GET
    public Uni<List<User>> get(){
        return userService.list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    @PermitAll
    public Uni<User> createUser ( User user){
      return   userService.create(user);
    }


    @GET
    @Path("{id}")
    public Uni<User> getById (@PathParam("id") long id){
        return userService.findById(id);
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Uni<User> updateUser (@PathParam("id") long id, User user){
        user.id = id;
        return userService.update(user);
    }


    @Delete
    @Path("{id}")
    public Uni<Void> deleteUser (@PathParam("id") long id){
        return userService.delete(id);
    }


    @GET
    @Path("self")
    public Uni<User> getCurrentUser (){
        return userService.getCurrentUser();
    }



    @PUT
    @Path("self/password")
    @RolesAllowed("user")
    public Uni<User> changePassword(PasswordChange
                                            passwordChange) {
        return userService
                .cambiarContrasena(passwordChange.currentPassword(),
                        passwordChange.newPassword());
    }
}
