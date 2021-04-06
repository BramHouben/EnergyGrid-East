package org.energygrid.east.authenticationservice.service;

import org.energygrid.east.authenticationservice.model.User;
import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService implements IAuthenticationService {

    private AuthenticationRepository authenticationRepository;

    private final Map<String, String> users = new HashMap<>();

    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
        users.put("1@user.com", "password1");
        users.put("2@user.com", "password2");
        users.put("3@user.com", "password3");
        users.put("4@user.com", "password4");
        users.put("5@user.com", "password5");
    }


    @Override
    public boolean login(String email, String password) {
        User user = authenticationRepository.findUserByEmailAndPassword(email, password);
        return user != null;
    }

    @Override
    public void addUser(String email, String password) {
        var user = new User(email, password);
        authenticationRepository.save(user);
    }
}
