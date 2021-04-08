//package org.energygrid.east.authenticationservice;
//
//import org.energygrid.east.authenticationservice.model.User;
//import org.energygrid.east.authenticationservice.repository.AuthenticationRepository;
//import org.energygrid.east.authenticationservice.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
////@ActiveProfiles("test")
////@RunWith(MockitoJUnitRunner.class)
////@SpringBootTest
////class AuthenticationTests {
////
////    @InjectMocks
////    private UserService userService;
////
////    @Mock
////    private AuthenticationRepository authenticationRepository;
////
////    @Test
////    void userAlreadyExists() {
////        //Arrange
////        userService = new UserService();
////        User user = new User("email@test.com", "password");
////        when(authenticationRepository.save(user)).thenReturn(user);
////        when(authenticationRepository.findUserByEmail("email@test.com")).thenReturn(user);
////
////        //Act
////        userService.addUser("email@test.com", "password");
////
////        //Assert
////        assertEquals(1, authenticationRepository.findAll().stream().count());
////    }
////}
