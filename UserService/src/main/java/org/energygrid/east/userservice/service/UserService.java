package org.energygrid.east.userservice.service;

import io.jsonwebtoken.Claims;
import javassist.NotFoundException;
import org.energygrid.east.userservice.errormessages.DuplicatedNameException;
import org.energygrid.east.userservice.model.dto.UserDTO;
import org.energygrid.east.userservice.model.enums.AccountRole;
import org.energygrid.east.userservice.model.fromFrontend.User;
import org.energygrid.east.userservice.model.rabbitMq.UserRabbitMq;
import org.energygrid.east.userservice.rabbit.Producer.AddUserProducer;
import org.energygrid.east.userservice.rabbit.Producer.DeleteUserProducer;
import org.energygrid.east.userservice.rabbit.Producer.UpdateUserProducer;
import org.energygrid.east.userservice.rabbit.RabbitProducer;
import org.energygrid.east.userservice.repo.IUserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Service
public class UserService {
    private final ModelMapper mapper = new ModelMapper();
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IJwtService jwtService;

    public void addUser(@NotNull User user) {
        UserDTO dbUser = userRepo.getUserByUuidOrUsernameOrEmail(null, user.getUsername(), user.getEmail());
        if (dbUser != null) {
            throw new DuplicatedNameException("Username or email already in use");
        }

        UserDTO userToStore = mapper.map(user, UserDTO.class);
        userToStore.setUuid(UUID.randomUUID());

        userRepo.save(userToStore);
        var rabbitMqUser = mapper.map(user, UserRabbitMq.class);
        rabbitMqUser.setUuid(userToStore.getUuid());
        rabbitMqUser.setAccountRole(AccountRole.LARGE_SCALE_CUSTOMER);
        storeUserInAuthenticationService(rabbitMqUser);
    }

    private void storeUserInAuthenticationService(UserRabbitMq user) {
        var rabbitProducer = new RabbitProducer();
        var userProducer = new AddUserProducer(user);
        rabbitProducer.produce(userProducer);
    }

    public UserDTO getUserByUuidOrUsernameOrEmail(UUID uuid, String username, String email) {
        if (uuid == null && username.isEmpty() && email.isEmpty()) {
            throw new NullPointerException("uuid, username and or email was empty");
        }

        return userRepo.getUserByUuidOrUsernameOrEmail(uuid, username, email);
    }

    public void editUser(@NotNull User user, @NotNull String jwt) throws IllegalAccessException {
        Claims jwtClaims = jwtService.getClaims(jwt);
        var userUuid = UUID.fromString(jwtClaims.get("uuid").toString());
        var dbUser = userRepo.getUserByUuidOrUsernameOrEmail(userUuid, null, null);

        if (!userUuid.equals(dbUser.getUuid())) {
            throw new IllegalAccessException();
        }

        if (jwt.isEmpty()) {
            throw new NullPointerException();
        }

        if (user.getNewPassword() != null && !user.getNewPassword().isEmpty()) {
            var userRabbitMq = mapper.map(dbUser, UserRabbitMq.class);
            userRabbitMq.setPassword(user.getNewPassword());
            updateUserInAuthenticationService(userRabbitMq);
        }

        if (!user.getEmail().equals(dbUser.getEmail())) {
            dbUser.setEmail(user.getEmail());
            updateUserInAuthenticationService(mapper.map(dbUser, UserRabbitMq.class));
        }

        dbUser.setUsername(user.getUsername());
        dbUser.setLanguage(user.getLanguage());

        userRepo.save(dbUser);
    }

    private void updateUserInAuthenticationService(UserRabbitMq user) {
        var rabbitProducer = new RabbitProducer();
        var userProducer = new UpdateUserProducer(user);
        rabbitProducer.produce(userProducer);
    }

    public void deleteUser(@NotNull String jwt) throws NotFoundException, IllegalAccessException {
        Claims claims = jwtService.getClaims(jwt);
        var uuid = UUID.fromString(claims.get("uuid").toString());
        UserDTO userToDelete = userRepo.getByUuid(uuid);
        if (userToDelete == null) {
            throw new NotFoundException("Not found");
        }

        if (!uuid.equals(userToDelete.getUuid())) {
            throw new IllegalAccessException();
        }

        deleteUserInAuthenticationService(mapper.map(userToDelete, UserRabbitMq.class));
        userRepo.delete(userToDelete);
    }

    private void deleteUserInAuthenticationService(UserRabbitMq user) {
        var rabbitProducer = new RabbitProducer();
        var userProducer = new DeleteUserProducer(user);
        rabbitProducer.produce(userProducer);
    }
}
