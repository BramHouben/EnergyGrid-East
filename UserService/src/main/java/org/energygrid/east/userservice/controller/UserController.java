package org.energygrid.east.userservice.controller;

import io.jsonwebtoken.Claims;
import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.enums.AccountRole;
import org.energygrid.east.userservice.model.fromFrontend.Operator;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.model.viewmodel.UserViewModel;
import org.energygrid.east.userservice.service.IJwtService;
import org.energygrid.east.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private IJwtService jwtService;

    private final ModelMapper modelMapper;

    @Autowired
    private HttpServletRequest request;
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    public UserController() {
        this.modelMapper = new ModelMapper();
    }

    @PostMapping()
    public ResponseEntity addUser(@NotNull @RequestBody User user) {
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
    public ResponseEntity<UserViewModel> getUserByUuidOrUsernameOrEmail(@RequestParam(required = false) UUID uuid, @RequestParam(required = false) String username, @RequestParam(required = false) String email) {
        try {
            String jwt = request.getHeader("jwt");
            if(jwt == null || jwt.isEmpty()) {
                throw new IllegalAccessException();
            }

            UserDTO user = userService.getUserByUuidOrUsernameOrEmail(uuid, username, email);
            if (user == null) {
                return ResponseEntity.status(404).body(null);
            }

            var userViewmodel = modelMapper.map(user, UserViewModel.class);
            return ResponseEntity.ok(userViewmodel);
        } catch (IllegalAccessException e){
            return ResponseEntity.status(401).body(null);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping()
    public ResponseEntity editUser(@NotNull @RequestBody User user) {
        try {
            String jwt = request.getHeader("jwt");
            if(jwt == null || jwt.isEmpty()) {
                throw new IllegalAccessException();
            }

            userService.editUser(user, jwt);
            return ResponseEntity.ok(null);
        } catch (IllegalAccessException e){
          return ResponseEntity.status(401).body(null);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping()
    public ResponseEntity deleteUser() {
        try {
            String jwt = request.getHeader("jwt");
            if(jwt == null || jwt.isEmpty()) {
                throw new IllegalAccessException();
            }

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
    public ResponseEntity getGridOperators(){
        try {
            //if(checkForNotAdmin(request.getHeader("jwt"))) return ResponseEntity.status(401).body(null);

            var operators = userService.getGridOperators();

            //var operatorViewModel = modelMapper.map(operators, UserViewModel.class);
            return ResponseEntity.ok(operators);
        } catch (NullPointerException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/operator")
    public ResponseEntity addGridOperator(@NotNull @RequestBody User user){
        try {
            //if(checkForNotAdmin(request.getHeader("jwt"))) return ResponseEntity.status(401).body(null);

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
    public ResponseEntity deleteGridOperator(@NotNull @RequestBody Operator operator){
        try{
            //if(checkForNotAdmin(request.getHeader("jwt"))) return ResponseEntity.status(401).body(null);

            userService.deleteOperator(operator);
            return ResponseEntity.status(201).body(null);
        } catch (DuplicatedNameException e) {
            return ResponseEntity.status(409).body(null);
        } catch (Exception e) {
            logger.log(Level.ALL, e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    private boolean checkForNotAdmin(String jwt){
        if(jwt == null || jwt.isEmpty()) return true;

        Claims jwtClaims = jwtService.getClaims(jwt);
        var userRole = jwtClaims.get("role");

        return userRole != "ADMIN";
    }

}
