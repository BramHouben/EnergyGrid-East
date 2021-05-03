package org.energygrid.east.userservice.controller;

import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.model.viewmodel.UserViewModel;
import org.energygrid.east.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("userservice/user")
public class UserController {
    @Autowired
    private UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    private HttpServletRequest request;

    public UserController() {
        this.modelMapper = new ModelMapper();
    }

    @PostMapping()
    public ResponseEntity AddUser(@NotNull @ModelAttribute User user) {
            userService.addUser(user);
            return ResponseEntity.status(201).body(null);
    }

    @GetMapping()
    public ResponseEntity<UserViewModel> GetUserByUuidOrUsernameOrEmail(@RequestParam(required = false) UUID uuid, @RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        try {
            UserDTO user = userService.getUserByUuidOrUsernameOrEmail(uuid, username, email);
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
    public ResponseEntity EditUser(@NotNull @ModelAttribute User user) {
        try {
            String jwt = request.getHeader("bearer");
            userService.editUser(user, jwt);
            return ResponseEntity.ok(null);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<?> deleteUser(@NotNull @PathVariable UUID uuid) {
        try {
            String jwt = request.getHeader("bearer");
            userService.deleteUser(jwt, uuid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
