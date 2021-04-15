package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.User;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public void addUser(String email, String password) {

        if (authenticationRepository.findUserByEmail(email) == null) {
            var hashedPassword = securityService.hashPassword(password);

            authenticationRepository.save(new User(email, hashedPassword));
        }
    }
}
