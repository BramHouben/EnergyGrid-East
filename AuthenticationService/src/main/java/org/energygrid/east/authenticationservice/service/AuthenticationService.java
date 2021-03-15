package org.energygrid.east.authenticationservice.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService implements IAuthenticationService {

    //@Autowired
    //public IAuthenticationRepo authenticationRepo;

    private Map<String, String> users = new HashMap<>();

    public AuthenticationService() {
        users.put("1@user.com", "password1");
        users.put("2@user.com", "password2");
        users.put("3@user.com", "password3");
        users.put("4@user.com", "password4");
        users.put("5@user.com", "password5");
    }


    @Override
    public boolean Login(String email, String password) {
        //todo use repo
        String actualPassword = users.get(email);

        if (actualPassword.equals(password)) return true;

        return false;
    }
}
