package org.energygrid.east.userservice.service;

import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.repo.IUserRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private SecurityService securityService;

    public void AddUser(@NotNull User user) {
        UserDTO dbUser = userRepo.getUserByUuidOrUsernameOrEmail(null, user.getUsername(), user.getEmail());
        if (dbUser != null) {
            throw new DuplicatedNameException("Username or email already in use");
        }

        String passwordHash = securityService.HashPassword(user.getPassword());
        UserDTO userToStore = new UserDTO();
        userToStore.setUuid(UUID.randomUUID().toString());
        userToStore.setUsername(user.getUsername());
        userToStore.setEmail(user.getEmail());
        userToStore.setPassword(passwordHash);
        userToStore.setAccountRole(user.getAccountRole());

        userRepo.save(userToStore);
    }

    public UserDTO GetUserByUuidOrUsernameOrEmail(String uuid, String username, String email) {
        if (StringUtils.isEmpty(uuid) && StringUtils.isEmpty(username) && StringUtils.isEmpty(email)) {
            throw new RuntimeException("uuid, username and or email was empty");
        }
        return userRepo.getUserByUuidOrUsernameOrEmail(uuid, username, email);
    }

    public void EditUser(@NotNull UserDTO user) {
        var dbUser = userRepo.getUserByUuid(user.getUuid());
        if (dbUser == null) {
            throw new NullPointerException();
        }

        var userToStore = new UserDTO();
        if (user.getPassword() != null) {
            userToStore.setPassword(securityService.HashPassword(user.getPassword()));
        } else {
            userToStore.setPassword(dbUser.getPassword());
        }

        userToStore.setUuid(user.getUuid());
        userToStore.setUsername(user.getUsername());
        userToStore.setAccountRole(user.getAccountRole());
        userToStore.setEmail(user.getEmail());

        userRepo.save(userToStore);
    }

    public void DeleteUser(@NotNull String uuid) {
        UserDTO userToDelete = userRepo.getUserByUuid(uuid);
        userRepo.delete(userToDelete);
    }
}
