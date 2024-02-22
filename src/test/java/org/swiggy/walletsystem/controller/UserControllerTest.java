package org.swiggy.walletsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.swiggy.walletsystem.dto.request.UserRequest;
import org.swiggy.walletsystem.execptions.UserAlreadyPresentException;
import org.swiggy.walletsystem.models.entites.UserModel;
import org.swiggy.walletsystem.models.repository.UserRepository;
import org.swiggy.walletsystem.service.UserService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        reset(userService);
    }
    @Test
    void testUserRegistration() throws Exception {
        UserRequest userRequest = new UserRequest("testUser", "password");
        UserModel userModel = new UserModel("testUser", passwordEncoder.encode("password"), null);
        userRepository.save(userModel);

        when(userService.registerUser(userRequest)).thenReturn(userModel);
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.password").exists() );

        verify(userService, times(1)).registerUser(userRequest);
    }
    @Test
    void testUserAlreadyPresent() throws Exception {
        UserRequest userRequest = new UserRequest("testUser", "password");
        when(userService.registerUser(userRequest)).thenThrow(UserAlreadyPresentException.class);
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
        verify(userService, times(1)).registerUser(userRequest);
    }
    @Test
    @WithMockUser(username = "testUser")
    void testUserDeletion() throws Exception {
        UserModel userModel = new UserModel("testUser", passwordEncoder.encode("password"), null);
        userRepository.save(userModel);
        mockMvc.perform(delete("/user/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser("testUser");
    }
    @Test
    void testUserDeletionWithoutAuthentication() throws Exception {
        mockMvc.perform(delete("/user/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
        verify(userService, times(0)).deleteUser("testUser");
    }



}