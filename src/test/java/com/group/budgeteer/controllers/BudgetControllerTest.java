package com.group.budgeteer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.User;

import com.group.budgeteer.services.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration
@WithMockUser(username="julian@gmail.com")
class BudgetControllerTest {
    @MockBean
    private BudgetService budgetService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    User user  = User.builder()
            .firstName("Julian")
            .lastName("Smith")
            .email("julian@gmail.com")
            .password("password")
            .build();

    Budget bud1;
    Budget bud2;
    Budget bud3;

    @BeforeEach
    void setUp(){
        bud1 = new Budget(UUID.randomUUID(),5000.00, LocalDate.of(2023, 9, 1), user);
        bud2 = new Budget(UUID.randomUUID(),4000.00, LocalDate.of(2023, 10, 1), user);
        bud3 = new Budget(UUID.randomUUID(), 3000.00, LocalDate.of(2023, 11, 1), user);
    }
}