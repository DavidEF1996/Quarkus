package org.example.fullstack.services.user;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.example.fullstack.dto.project.Project;
import org.example.fullstack.dto.task.Task;
import org.example.fullstack.dto.user.User;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

@ApplicationScoped
public class UserService {

    private final JsonWebToken jwt;

    @Inject
    public UserService(JsonWebToken jwt) {
        this.jwt = jwt;
    }

    //Métodos para listar
    @WithTransaction
    public Uni<User> findById(long id){
        return User.<User>findById(id).onItem().ifNull().failWith(()-> new
                ObjectNotFoundException(id,"User"));
    }

    @WithTransaction
    public Uni<User> findByName(String name){
        return User.<User>find("name", name).firstResult();
    }


    @WithTransaction
    public Uni<List<User>> list (){
        return getCurrentUser().chain(u -> {
            if(u==null){
                throw new ClientErrorException("Las credenciales fallaron", Response.Status.CONFLICT);
            }
            return  User.listAll();
        });

    }




    @WithTransaction
    public Uni<User>create(User user){
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        return user.persistAndFlush();
    }

    @WithTransaction
    public Uni<User> update (User user){
        return findById(user.id)
                .chain(u -> {
                   user.setPassword(u.getPassword());
                   return User.getSession();
                })
                .chain(s -> s.merge(user));
    }

    @WithTransaction
    public Uni<Void> delete (long id){
        return findById(id)
                .chain(u -> Uni.combine().all().unis(
                        Task.delete("user.id",u.id),
                        Project.delete("user.id", u.id)
                ).asTuple()
                        .chain(t -> u.delete()));

    }

    @WithTransaction
    public Uni<User> cambiarContrasena (String contrasenaActual, String nuevaContrasena){
        return getCurrentUser()
                    .chain(u-> {
                   if(!matches(u, contrasenaActual)){
                       throw new ClientErrorException("Las credenciales fallaron", Response.Status.CONFLICT);
                   }
                   u.setPassword(BcryptUtil.bcryptHash(nuevaContrasena));
                   return u.persistAndFlush();
                });
    }


    public Uni<User> getCurrentUser(){
        return findByName(jwt.getName());
    }


    public static boolean matches (User user, String password){

        return BcryptUtil.matches(password,user.getPassword() );
    }


}
