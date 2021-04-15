package org.energygrid.east.userservice.service;

import io.jsonwebtoken.Claims;

public interface IJwtService {
    Claims getClaims(String jwt);
}
