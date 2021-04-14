package org.energygrid.east.userservice.service;

import javassist.NotFoundException;
import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.enums.AccountRole;
import org.energygrid.east.userservice.model.fromFrontend.User;
import javax.validation.constraints.NotNull;
import org.energygrid.east.userservice.model.rabbitMq.UserRabbitMq;
import org.energygrid.east.userservice.rabbit.Producer.AddUserProducer;
import org.energygrid.east.userservice.rabbit.Producer.DeleteUserProducer;
import org.energygrid.east.userservice.rabbit.Producer.UpdateUserProducer;
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
        }

        UserDTO userToStore = mapper.map(user, UserDTO.class);
        userToStore.setUuid(UUID.randomUUID());
        userToStore.setAccountRole(AccountRole.LargeScaleCustomer);

        userRepo.save(userToStore);
        var rabbitMqUser = mapper.map(user, UserRabbitMq.class);
        rabbitMqUser.setUuid(userToStore.getUuid());
        storeUserInAuthenticationService(rabbitMqUser);
    }

    private void storeUserInAuthenticationService(UserRabbitMq user) {
        var rabbitProducer = new RabbitProducer();
        var userProducer = new AddUserProducer(user);
        rabbitProducer.produce(userProducer);
    }

    public UserDTO getUserByUuidOrUsernameOrEmail(UUID uuid, String username, String email) {
        if (StringUtils.isEmpty(uuid) && StringUtils.isEmpty(username) && StringUtils.isEmpty(email)) {
            throw new NullPointerException("uuid, username and or email was empty");
        }

        return userRepo.getUserByUuidOrUsernameOrEmail(uuid, username, email);
    }

    public void editUser(@NotNull User user) {
        var dbUser = userRepo.getUserByUuidOrUsernameOrEmail(user.getUuid(), null, null);
        if (dbUser == null) {
            throw new NullPointerException();
        }

        var userToStore = new UserDTO();
        if (user.getNewPassword() != null || !user.getEmail().equals(dbUser.getEmail())) {
            UpdateUserInAuthenticationService(mapper.map(userToStore, UserRabbitMq.class));
        }

        userToStore.setUuid(user.getUuid());
        userToStore.setUsername(user.getUsername());
        userToStore.setAccountRole(user.getAccountRole());
        userToStore.setLanguage(user.getLanguage());

        userRepo.save(userToStore);
    }

    private void UpdateUserInAuthenticationService(UserRabbitMq user) {
        var rabbitProducer = new RabbitProducer();
        var userProducer = new UpdateUserProducer(user);
        rabbitProducer.produce(userProducer);
    }

    public void deleteUser(@NotNull UUID uuid) throws NotFoundException {
        UserDTO userToDelete = userRepo.getByUuid(uuid);
        if (userToDelete == null) {
            throw new NotFoundException("Not found");
        }

        DeleteUserInAuthenticationService(mapper.map(userToDelete, UserRabbitMq.class));
        userRepo.delete(userToDelete);
    }

    private void DeleteUserInAuthenticationService(UserRabbitMq user) {
        var rabbitProducer = new RabbitProducer();
        var userProducer = new DeleteUserProducer(user);
        rabbitProducer.produce(userProducer);
    }
}
