package org.energygrid.east.authenticationservice.service;

import io.jsonwebtoken.Claims;
import org.energygrid.east.authenticationservice.model.dto.UserDto;

public interface IJwtService {
    String create(UserDto user);
    Claims getClaims(String jwt);
}
