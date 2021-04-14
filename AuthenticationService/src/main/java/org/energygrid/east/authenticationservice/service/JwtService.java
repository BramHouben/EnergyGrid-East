package org.energygrid.east.authenticationservice.service;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.energygrid.east.authenticationservice.model.dto.UserDto;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements IJwtService {
    private Key key;

    public JwtService() {
        key = getKey();
    }

    @Override
    public String create(UserDto user){
        Map<String, String> claims = new HashMap<>();
        claims.put("uuid", user.getUuid().toString());
        claims.put("email", user.getEmail());

        Header<?> header = Jwts.header();
        header.setType("JWT");

        return Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setClaims(claims)
                .setIssuer("auth")
                .setIssuedAt(getIssuedAt())
                .setExpiration(getExpiration())
                .signWith(key)
                .compact();
    }

    private Date getIssuedAt(){
        Calendar now = Calendar.getInstance();
        return now.getTime();
    }

    private Date getExpiration(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 60);
        return now.getTime();
    }

    @Override
    public boolean validate(String jwtToken){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        }
        catch (JwtException e) {
            return false;
        }
    }

    private Key getKey() {
        if(key == null){
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            return key;
        }
        return key;
    }
}