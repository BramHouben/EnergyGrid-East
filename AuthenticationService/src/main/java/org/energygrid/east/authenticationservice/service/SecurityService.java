package org.energygrid.east.authenticationservice.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class SecurityService {
    private final Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            32,
            64
    );

    public String hashPassword(@NotNull String password) {
        return argon2.hash(22, 65536, 1, password);
    }

    public boolean verify(@NotNull String password, @NotNull String passwordHash) {
        return argon2.verify(passwordHash, password);
    }
}
