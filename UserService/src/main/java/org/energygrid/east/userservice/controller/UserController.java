package org.energygrid.east.userservice.controller;

import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.enums.AccountRole;
import org.energygrid.east.userservice.model.fromfrontend.Operator;
import org.energygrid.east.userservice.model.fromfrontend.User;
import org.energygrid.east.userservice.model.viewmodel.GridOperatorViewModel;
import org.energygrid.east.userservice.model.viewmodel.UserViewModel;
import org.energygrid.east.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;
    @Value("${HEADER}")
    private String authorization;

    @PostMapping()
    public ResponseEntity<UserViewModel> addUser(@NotNull @RequestBody User user) {
        try {
            userService.addUser(user, AccountRole.LARGE_SCALE_CUSTOMER);
            return ResponseEntity.status(201).body(null);
        } catch (DuplicatedNameException e) {
            return ResponseEntity.status(409).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<UserViewModel> getUserByUuidOrUsernameOrEmail(@RequestParam(required = false) UUID uuid, @RequestParam(required = false) String username, @RequestParam(required = false) String email, HttpServletRequest request) {
        try {
            String jwt = request.getHeader(authorization);
            if (jwt == null || jwt.isEmpty()) {
                throw new IllegalAccessException();
            }

            UserDTO user = userService.getUserByUuidOrUsernameOrEmail(uuid, username, email);
            if (user == null) {
                return ResponseEntity.status(404).body(null);
            }
            var modelMapper = new ModelMapper();
            var userViewModel = modelMapper.map(user, UserViewModel.class);
            return ResponseEntity.ok(userViewModel);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(null);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity<UserViewModel> editUser(@NotNull @RequestBody User user, HttpServletRequest request) {
        try {
            String bearer = request.getHeader(authorization);
            if (bearer == null || bearer.isEmpty()) {
                throw new IllegalAccessException();
            }

            String jwt = bearer.replace("Bearer ", bearer);
            userService.editUser(user, jwt);
            return ResponseEntity.ok(null);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(401).body(null);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping()
    public ResponseEntity<UserViewModel> deleteUser(HttpServletRequest request) {
        try {
            String bearer = request.getHeader(authorization);
            if (bearer == null || bearer.isEmpty()) {
                throw new IllegalAccessException();
            }

            String jwt = bearer.replace("Bearer ", bearer);
            userService.deleteUser(jwt);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/operator")
    public ResponseEntity<List<UserDTO>> getGridOperators(HttpServletRequest request) {
        try {
            String bearer = request.getHeader(authorization);
            if (bearer == null || bearer.isEmpty()) {
                throw new IllegalAccessException();
            }

            var operators = userService.getGridOperators();
            return ResponseEntity.ok(operators);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/operator")
    public ResponseEntity<GridOperatorViewModel> addGridOperator(@NotNull @RequestBody User user, HttpServletRequest request) {
        try {
            String bearer = request.getHeader(authorization);
            if (bearer == null || bearer.isEmpty()) {
                throw new IllegalAccessException();
            }

            userService.addUser(user, AccountRole.GRID_OPERATOR);
            return ResponseEntity.status(201).body(null);
        } catch (DuplicatedNameException e) {
            return ResponseEntity.status(409).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/operator")
    public ResponseEntity<UserViewModel> deleteGridOperator(@NotNull @RequestBody Operator operator, HttpServletRequest request) {
        try {
            String bearer = request.getHeader(authorization);
            if (bearer == null || bearer.isEmpty()) {
                throw new IllegalAccessException();
            }

            userService.deleteOperator(operator);
            return ResponseEntity.status(201).body(null);
        } catch (DuplicatedNameException e) {
            return ResponseEntity.status(409).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

}
