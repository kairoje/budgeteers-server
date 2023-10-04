package com.group.budgeteer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.budgeteer.classes.APIResponse;
import com.group.budgeteer.controllers.UserController;
import com.group.budgeteer.models.User;
import com.group.budgeteer.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private User requestUser;

    @BeforeEach
    void setUp(){
         requestUser = User.builder()
                .email("julian@gmail.com")
                .password("password")
                .build();
    }

    @Test
    void testSignUp() throws Exception {
        // Mock the UserService behavior when signing up
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn("sampleJWTToken");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(requestUser));

        // Perform a POST request to the signup endpoint
        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("sampleJWTToken"));
    }

    @Test
    void testLogin() throws Exception {
        // Mock the UserService behavior when logging in
        Mockito.when(userService.login(Mockito.any(User.class))).thenReturn(requestUser);

        // Perform a POST request to the login endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName").value(requestUser.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(requestUser.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.lastName").value(requestUser.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(requestUser.getId()));

    }
}