package org.energygrid.east.authenticationservice.service;

public interface IAuthenticationService {
    /**
     * @param email from user
     * @param password from user
     * @return true if credentials are correct
     * false if incorrect
     */
    boolean login(String email, String password);
}
