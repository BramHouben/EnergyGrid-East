package org.energygrid.east.authenticationservice.controller;

import org.energygrid.east.authenticationservice.model.fromFrontend.User;
import org.energygrid.east.authenticationservice.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> Login(@NotNull @RequestBody User user) {
        try {
            return ResponseEntity.ok(authenticationService.login(user));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
