package org.energygrid.east.userservice.service;

import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.enums.AccountRole;
import org.energygrid.east.userservice.model.fromFrontend.User;

import javax.validation.constraints.NotNull;

import org.energygrid.east.userservice.model.rabbitMq.UserRabbitMq;
import org.energygrid.east.userservice.rabbit.Producer.AddUserProducer;
import org.energygrid.east.userservice.rabbit.RabbitProducer;
import org.energygrid.east.userservice.repo.IUserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private IUserRepo userRepo;
    private final ModelMapper mapper = new ModelMapper();

    public void addUser(@NotNull User user) {
        UserDTO dbUser = userRepo.getUserByUuidOrUsernameOrEmail(null, user.getUsername(), user.getEmail());
        if (dbUser != null) {
            throw new DuplicatedNameException("Username or email already in use");
            // TODO check if email or username is in use
        }

        UserDTO userToStore = mapper.map(user, UserDTO.class);
        userToStore.setUuid(UUID.randomUUID().toString());
        userToStore.setAccountRole(AccountRole.LargeScaleCustomer);

        userRepo.save(userToStore);
        storeUserInAuthenticationService(mapper.map(userToStore, UserRabbitMq.class));
    }

    private void storeUserInAuthenticationService(UserRabbitMq user) {
        RabbitProducer rabbitProducer = new RabbitProducer();
        AddUserProducer userProducer = new AddUserProducer(user);
        rabbitProducer.produce(userProducer);
    }

    public UserDTO getUserByUuidOrUsernameOrEmail(String uuid, String username, String email) {
        if (StringUtils.isEmpty(uuid) && StringUtils.isEmpty(username) && StringUtils.isEmpty(email)) {
            throw new NullPointerException("uuid, username and or email was empty");
        }
        // TODO check local
        return userRepo.getUserByUuidOrUsernameOrEmail(uuid, username, email);
    }

    public void editUser(@NotNull User user) {
        var dbUser = userRepo.getUserByUuid(user.getUuid());
        if (dbUser == null) {
            throw new NullPointerException();
        }

        var userToStore = new UserDTO();
        if (user.getNewPassword() != null) {
            // TODO add rabbitmq message to update password on auth service
        }

        if (!user.getEmail().equals(dbUser.getEmail())) {
            // TODO add rabbitmq message to update email on auth service
        }

        userToStore.setUuid(user.getUuid());
        userToStore.setUsername(user.getUsername());
        userToStore.setAccountRole(user.getAccountRole());
        userToStore.setLanguage(user.getLanguage());

        userRepo.save(userToStore);
    }

    public void deleteUser(@NotNull String uuid) {
        UserDTO userToDelete = userRepo.getUserByUuid(uuid);
        // TODO add rabbitmq message to delete the user data on auth service
        userRepo.delete(userToDelete);
    }
}
