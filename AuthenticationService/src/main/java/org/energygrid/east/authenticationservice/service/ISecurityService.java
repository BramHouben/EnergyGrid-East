package org.energygrid.east.authenticationservice.service;

import com.sun.istack.NotNull;

public interface ISecurityService {
    String hashPassword(@NotNull String password);
    boolean verifyHash(@NotNull String password, @NotNull String passwordHash);
}
