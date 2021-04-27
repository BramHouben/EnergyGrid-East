package org.energygrid.east.authenticationservice.service;

public interface IUserService {
    /**
     * @param username from new user
     * @param password from new user
     *                 adds user in db
     */
    void addUser(String username, String password);
}
