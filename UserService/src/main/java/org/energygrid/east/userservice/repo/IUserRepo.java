package org.energygrid.east.userservice.repo;

import org.energygrid.east.userservice.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<UserDTO, String> {
    UserDTO getUserByUuid(String uuid);
    UserDTO getUserByUuidOrUsernameOrEmail(String uuid, String username, String email);
}