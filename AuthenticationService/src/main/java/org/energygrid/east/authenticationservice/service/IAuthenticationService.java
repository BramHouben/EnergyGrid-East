package org.energygrid.east.authenticationservice.service;

public interface IAuthenticationService {
    boolean login(String email, String password);
}
