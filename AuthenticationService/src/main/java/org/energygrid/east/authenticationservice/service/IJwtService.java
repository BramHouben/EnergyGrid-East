package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.dto.UserDto;

import java.util.Map;

public interface IJwtService {
    String create(UserDto user);
    boolean validate(String jwtToken);
}
