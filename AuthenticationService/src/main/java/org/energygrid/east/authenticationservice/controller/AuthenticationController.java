package org.energygrid.east.authenticationservice.controller;

import org.energygrid.east.authenticationservice.model.User;
import org.energygrid.east.authenticationservice.service.IAuthenticationService;
import org.energygrid.east.authenticationservice.service.jwt.CreateJWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    public IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> LoginUser(@NotNull @RequestParam(name = "email") String email, @NotNull @RequestParam(name = "password") String password) {
        if (authenticationService.Login(email, password)) {
            Map<String, String> claims = new HashMap<>();
            claims.put("email", email);

            String jwtToken = CreateJWTToken.getInstance().create(claims);

            return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
        };

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
