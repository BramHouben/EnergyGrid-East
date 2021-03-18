package org.energygrid.east.userservice.service;

import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.repo.IUserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private SecurityService securityService; // TODO fix autowire issue

    public void AddUser(@NotNull User user) {
        UserDTO dbUser = userRepo.GetUser(user.getUsername(), user.getEmail());
        if (dbUser != null) {
            throw new DuplicatedNameException("Username or email already in use");
        }

        String passwordHash = securityService.HashPassword(user.getPassword());
        UserDTO userToStore = new UserDTO();
        userToStore.setId(UUID.randomUUID());
        userToStore.setUsername(user.getUsername());
        userToStore.setEmail(user.getEmail());
        userToStore.setPassword(passwordHash);
        userToStore.setAccountrole(user.getAccountRole());

        userRepo.CreateUser(userToStore);
    }

    public UserDTO GetUserById(@NotNull UUID id) {
        return userRepo.GetUser(id);
    }

    public void EditUser(@NotNull UserDTO user) {
        userRepo.EditUser(user);
    }

    public void DeleteUser(@NotNull UUID id) {
        userRepo.DeleteUserById(id);
    }
}
