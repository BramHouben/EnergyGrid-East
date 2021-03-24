package org.energygrid.east.userservice;

import org.energygrid.east.userservice.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    private final UserService userService;
    public UserServiceTest() {
        userService = new UserService();
    }

    @Test
    public void AddUserIllegalArgumentExceptionTest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> userService.AddUser(null));
    }

    @Test
    public void GetUserByUuidOrUsernameOrEmailNullPointerExceptionTest() {
        Assert.assertThrows(NullPointerException.class, () -> userService.GetUserByUuidOrUsernameOrEmail("Test", null, null));
        Assert.assertThrows(NullPointerException.class, () -> userService.GetUserByUuidOrUsernameOrEmail(null, "Test", null));
        Assert.assertThrows(NullPointerException.class, () -> userService.GetUserByUuidOrUsernameOrEmail(null, null, "Test"));

        Assert.assertThrows(NullPointerException.class, () -> userService.GetUserByUuidOrUsernameOrEmail(null, "Test", "Test"));
        Assert.assertThrows(NullPointerException.class, () -> userService.GetUserByUuidOrUsernameOrEmail("Test", null, "Test"));
        Assert.assertThrows(NullPointerException.class, () -> userService.GetUserByUuidOrUsernameOrEmail("Test", "Test", null));

        Assert.assertThrows(NullPointerException.class, () -> userService.GetUserByUuidOrUsernameOrEmail(null, null, null));
    }

    @Test
    public void EditUserIllegalArgumentExceptionTest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> userService.EditUser(null));
    }

    @Test
    public void DeleteUserIllegalArgumentExceptionTest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> userService.DeleteUser(null));
    }
}
