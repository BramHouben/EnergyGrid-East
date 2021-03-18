package org.energygrid.east.userservice.repo;

import org.energygrid.east.userservice.model.dto.UserDTO;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepo {
    public void CreateUser(UserDTO user);
    public UserDTO GetUser(UUID id);
    public UserDTO GetUser(String username, @Nullable String email);
    public void EditUser(UserDTO user);
    public void DeleteUserById(UUID id);
}
