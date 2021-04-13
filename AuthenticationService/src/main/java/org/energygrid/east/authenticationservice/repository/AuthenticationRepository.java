package org.energygrid.east.authenticationservice.repository;

import org.energygrid.east.authenticationservice.model.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<UserDto, Integer> {
    UserDto findUserByEmailAndPassword(String email, String password);
    UserDto findUserByEmail(String email);
    UserDto findUserByUuid(String uuid);
}
