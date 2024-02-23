package org.swiggy.walletsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.swiggy.walletsystem.dto.request.UserRequest;
import org.swiggy.walletsystem.execptions.UserAlreadyPresentException;
import org.swiggy.walletsystem.execptions.UserNotFoundException;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.entites.Wallet;
import org.swiggy.walletsystem.models.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
//    @Test
//    void testRegisterUser() throws UserAlreadyPresentException {
//        String username = "testUser";
//        UserModel userModel = new UserModel(1L, username, passwordEncoder.encode("password"), null);
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//
//        UserRequest userRequest = new UserRequest(username, "password");
//        when(userService.registerUser(userRequest)).thenReturn(userModel);
//
//        UserModel result = userService.registerUser(userRequest);
//        assertEquals(userModel, result);
//    }
//
//    @Test
//    void testRegisterUser1() throws UserAlreadyPresentException {
//        UserModel userModel = new UserModel(1L, "testUser", passwordEncoder.encode("password"), null);
//        when(userRepository.save(userModel)).thenReturn(userModel);
//
//        UserRequest userRequest = new UserRequest("testUser", "password");
//        when(userService.registerUser(userRequest)).thenReturn(userModel);
//
//        UserModel result = userService.registerUser(userRequest);
//        assertEquals(userModel, result);
//    }
//
//    @Test
//    void testUserDelete() throws UserNotFoundException {
//        String username = "testUser";
//        UserModel userModel = new UserModel(1L, username, passwordEncoder.encode("password"), null);
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userModel));
//
//        String result = userService.deleteUser(username);
//        assertEquals("User deleted successfully", result);
//        verify(userRepository, times(1)).delete(userModel);
//    }
//
//    @Test
//    void testUserDelete1() {
//        String username = "testUser";
//        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
//
//        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(username));
//        verify(userRepository, times(0)).delete(any());
//    }
}