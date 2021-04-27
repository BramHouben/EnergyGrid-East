package org.energygrid.east.authenticationservice.service.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CreateJWTToken {

    private static CreateJWTToken createJWTToken = new CreateJWTToken();
    private Key key;

    private CreateJWTToken() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static CreateJWTToken getInstance() {
        return createJWTToken;
    }

    public String create(Map<String, String> claims) {
        Header header = Jwts.header();
        header.setType("JWT");

        return Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setClaims(claims)
                .setIssuer("EnergyGrid")
                .setIssuedAt(getIssuedAt())
                .setExpiration(getExpiration())
                .signWith(key)
                .compact();
    }

    private Date getIssuedAt() {
        Calendar now = Calendar.getInstance();
        return now.getTime();
    }

    private Date getExpiration() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 24);
        return now.getTime();
    }

    public Key getKey() {
        return key;
    }
}
