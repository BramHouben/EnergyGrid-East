package org.energygrid.east.authenticationservice.repository;

import org.energygrid.east.authenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<User, Integer> {
    User findUserByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
}
