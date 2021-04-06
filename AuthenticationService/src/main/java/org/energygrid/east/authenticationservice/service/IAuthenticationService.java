package org.energygrid.east.authenticationservice.service;

public interface IAuthenticationService {
    boolean login(String email, String password);
    void addUser(String email, String password);
}
