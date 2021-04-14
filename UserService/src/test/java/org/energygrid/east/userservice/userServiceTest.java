package org.energygrid.east.userservice;

import org.energygrid.east.userservice.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
public class userServiceTest {
    private final UserService userService;

    public userServiceTest() {
        userService = new UserService();
    }

    @Test
    public void AddUserNullPointerExceptionTest() {
        Assert.assertThrows(NullPointerException.class, () -> userService.addUser(null));
    }

    @Test
    public void GetUserByUuidOrUsernameOrEmailNullPointerExceptionTest() {
        Assert.assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(UUID.randomUUID(), null, null));
        Assert.assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, "Test", null));
        Assert.assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, null, "Test"));

        Assert.assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, "Test", "Test"));
        Assert.assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(UUID.randomUUID(), null, "Test"));
        Assert.assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(UUID.randomUUID(), "Test", null));

        Assert.assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, null, null));
    }

    @Test
    public void EditUserNullPointerExceptionTest() {
        Assert.assertThrows(NullPointerException.class, () -> userService.editUser(null));
    }

    @Test
    public void DeleteUserNullPointerExceptionTest() {
        Assert.assertThrows(NullPointerException.class, () -> userService.deleteUser(null));
    }
}
