package org.energygrid.east.authenticationservice.service;

public interface IAuthenticationService {
    boolean Login(String email, String password);
    void AddUser(String email, String password);
}
