package org.energygrid.east.userservice.controller;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired UserService userService;

    @PostMapping()
    public ResponseEntity AddUser(@NotNull @RequestParam(name = "user") UserDTO user) {
        try {
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    //TODO automap models to prevent sensitive data exposure
    @GetMapping()
    public ResponseEntity<UserDTO> GetUser(@NotNull @RequestParam(name = "id") UUID id) {
        try {
            UserDTO user = userService.GetUserById(id);
            return ResponseEntity.status(200).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity EditUser(@NotNull User user) {
        try {
            // TODO map to dto
            userService.EditUser();
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping()
    public ResponseEntity DeleteUser(@NotNull @RequestParam(name = "id") UUID id) {
        try {
            userService.DeleteUser(id);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
