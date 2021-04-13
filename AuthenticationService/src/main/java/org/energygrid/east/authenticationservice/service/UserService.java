package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.dto.UserDto;
import org.energygrid.east.authenticationservice.model.rabbitmq.UserRabbitMq;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private final AuthenticationRepository authenticationRepository;

    @Override
    public void addUser(String email, String password) {
        if (authenticationRepository.findUserByEmail(email) == null) {
            var hashedPassword = passwordEncoder.encode(password);
            var user = new UserDto();
            user.setEmail(email);
            user.setPassword(hashedPassword);

            authenticationRepository.save(user);
        }
    }

    @Override
    public void updateUser(UserRabbitMq user) {
       authenticationRepository.save(mapper.map(user, UserDto.class));
    }

    @Override
    public void deleteUser(String uuid) {
        UserDto userToDelete = authenticationRepository.findUserByUuid(uuid);
        authenticationRepository.delete(userToDelete);
    }
}
