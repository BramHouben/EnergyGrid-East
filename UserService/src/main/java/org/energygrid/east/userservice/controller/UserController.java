package org.energygrid.east.userservice.controller;

import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.model.viewmodel.UserViewModel;
import org.energygrid.east.userservice.service.UserService;
import javax.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    ModelMapper modelMapper;

    public UserController() {
        this.modelMapper = new ModelMapper();
    }

    @PostMapping()
    public ResponseEntity AddUser(@NotNull @RequestBody User user) {
        try {
            userService.AddUser(user);
            return ResponseEntity.status(201).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<UserViewModel> GetUserByUuidOrUsernameOrEmail(@RequestParam(required = false) String uuid, @RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        try {
            UserDTO user = userService.GetUserByUuidOrUsernameOrEmail(uuid, username, email);
            if (user == null) {
                return ResponseEntity.status(404).body(null);
            }

            var userViewmodel = modelMapper.map(user, UserViewModel.class);
            return ResponseEntity.ok(userViewmodel);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity EditUser(@NotNull @RequestBody User user) {
        try {
            var userDto = modelMapper.map(user, UserDTO.class);
            userService.EditUser(userDto);
            return ResponseEntity.ok(null);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity DeleteUser(@NotNull @PathVariable String uuid) {
        try {
            userService.DeleteUser(uuid);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}