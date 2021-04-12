package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.User;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public boolean login(String email, String password) {
        User user = authenticationRepository.findUserByEmailAndPassword(email, password);
        return user != null;
    }

    public void AddUser(String email, String password) {
        var user = new User();
        user.setEmail(email);
        user.setPassword(password);
        authenticationRepository.save(user);
    }
}
