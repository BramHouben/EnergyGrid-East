package org.energygrid.east.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.springframework.stereotype.Service;

@Service
public class JwtService implements IJwtService {
    @Override
    public Claims getClaims(String token) {
        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

        var parser = new DefaultJwtParser();
        Jwt<?, ?> jwt = parser.parse(unsignedToken);
        return (Claims) jwt.getBody();
    }
}