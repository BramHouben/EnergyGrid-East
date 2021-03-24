package org.energygrid.east.userservice.service;

import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.repo.IUserRepo;
import javax.validation.constraints.NotNull;
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
        UserDTO dbUser = userRepo.getUserByUuidOrUsernameOrEmail(null, user.GetUsername(), user.GetEmail());
        if (dbUser != null) {
            throw new DuplicatedNameException("Username or email already in use");
        }

        String passwordHash = securityService.HashPassword(user.GetPassword());
        UserDTO userToStore = new UserDTO();
        userToStore.SetUuid(UUID.randomUUID().toString());
        userToStore.SetUsername(user.GetUsername());
        userToStore.SetEmail(user.GetEmail());
        userToStore.SetPassword(passwordHash);
        userToStore.SetAccountRole(user.GetAccountRole());

        userRepo.save(userToStore);
    }

    public UserDTO GetUserByUuidOrUsernameOrEmail(String uuid, String username, String email) {
        if (StringUtils.isEmpty(uuid) && StringUtils.isEmpty(username) && StringUtils.isEmpty(email)) {
            throw new NullPointerException("uuid, username and or email was empty");
        }
        return userRepo.getUserByUuidOrUsernameOrEmail(uuid, username, email);
    }

    public void EditUser(@NotNull UserDTO user) {
        var dbUser = userRepo.getUserByUuid(user.GetUuid());
        if (dbUser == null) {
            throw new NullPointerException();
        }

        var userToStore = new UserDTO();
        if (user.GetPassword() != null) {
            userToStore.SetPassword(securityService.HashPassword(user.GetPassword()));
        } else {
            userToStore.SetPassword(dbUser.GetPassword());
        }

        userToStore.SetUuid(user.GetUuid());
        userToStore.SetUsername(user.GetUsername());
        userToStore.SetAccountRole(user.GetAccountRole());
        userToStore.SetEmail(user.GetEmail());

        userRepo.save(userToStore);
    }

    public void DeleteUser(@NotNull String uuid) {
        UserDTO userToDelete = userRepo.getUserByUuid(uuid);
        userRepo.delete(userToDelete);
    }
}
