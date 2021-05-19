//package org.energygrid.east.userservice;
//
//import org.energygrid.east.userservice.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@ActiveProfiles("test")
//@SpringBootTest
//class userServiceTest {
//    private final UserService userService;
//
//    public userServiceTest() {
//        userService = new UserService();
//    }
//
//    @Test
//    void AddUserNullPointerExceptionTest() {
//        assertThrows(NullPointerException.class, () -> userService.addUser(null));
//    }
//
//    @Test
//    void GetUserByUuidOrUsernameOrEmailNullPointerExceptionTest() {
//        assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(UUID.randomUUID(), null, null));
//        assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, "Test", null));
//        assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, null, "Test"));
//
//        assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, "Test", "Test"));
//        assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(UUID.randomUUID(), null, "Test"));
//        assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(UUID.randomUUID(), "Test", null));
//
//        assertThrows(NullPointerException.class, () -> userService.getUserByUuidOrUsernameOrEmail(null, null, null));
//    }
//
//    @Test
//    void EditUserNullPointerExceptionTest() {
//        assertThrows(NullPointerException.class, () -> userService.editUser(null, null));
//    }
//
//    @Test
//    void DeleteUserNullPointerExceptionTest() {
//        assertThrows(NullPointerException.class, () -> userService.deleteUser(null));
//    }
//}
