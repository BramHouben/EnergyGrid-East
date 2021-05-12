package org.energygrid.east.authenticationservice.service;

import javax.validation.constraints.NotNull;

public interface ISecurityService {
    String hashPassword(@NotNull String password);
    boolean verifyHash(@NotNull String password, @NotNull String passwordHash);
}
