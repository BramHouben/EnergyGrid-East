package org.energygrid.east.authenticationservice.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@Service
public class SecurityService implements ISecurityService {
    private final Argon2 argon2;

    public SecurityService() {
        argon2 = Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id,
                32,
                64
        );
    }

    @Override
    public String hashPassword(@NotNull String password) {
        return argon2.hash(22, 65536, 1, password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean verifyHash(@NotNull String password, @NotNull String passwordHash) {
        return argon2.verify(password, passwordHash.getBytes(StandardCharsets.UTF_8));
    }
}
