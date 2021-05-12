package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.fromFrontend.User;

public interface IAuthenticationService {
    String login(User user) throws IllegalAccessException;
}
