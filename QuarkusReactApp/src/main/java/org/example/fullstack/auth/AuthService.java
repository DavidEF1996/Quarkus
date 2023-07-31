package org.example.fullstack.auth;

import io.quarkus.security.AuthenticationFailedException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.example.fullstack.services.user.UserService;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthService {

    private final String issuer;
    private final UserService userService;

    @Inject
    public AuthService(
            @ConfigProperty(name = "mp.jwt.verify.issuer") String issuer, UserService userService) {
        this.issuer = issuer;
        this.userService = userService;
    }

    public Uni<String> authenticate(AuthRequest authRequest) {
        return userService.findByName(authRequest.name())
                .onItem()
                .transform(user -> {
                    if (user == null || !UserService.matches(user, authRequest.password())) {
                        throw new AuthenticationFailedException("Invalid credentials");
                    }
                    Set<String> roles = user.getRoles().stream().map(role -> role.getRole()).collect(Collectors.toSet());
                    return Jwt.issuer(issuer)
                            .upn(user.getName())
                            .groups(roles)
                            .expiresIn(Duration.ofHours(1L))
                            .sign();
                });
    }
}