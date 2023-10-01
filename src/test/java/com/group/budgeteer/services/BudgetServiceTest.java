package com.group.budgeteer.services;

import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.User;
import com.group.budgeteer.repositories.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BudgetServiceTest {

    @InjectMocks
    BudgetService budgetService;

    @Mock
    BudgetRepository budgetRepository;

    User user = new User("sampleuser@example.com", "John", "Doe", "password123", null, null);

    LocalDate date = LocalDate.of(2023, Month.OCTOBER, 1);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken("username", "password");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    @Test
    public void testAuthenticatedServiceMethod() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/auth"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllBudgets(){
        Mockito.doReturn(getMockBudgets()).when(budgetRepository).findAll();
        List<Budget> budgets = this.budgetService.getBudgets();
        assertEquals(1, budgets.size());
    }

    private Iterable<Budget> getMockBudgets(){
        List<Budget> budgets = new ArrayList<>(1);
        for (int i = 0; i< 1; i++){
            budgets.add(new Budget(5000.00, date, user, null));
        }
        return budgets;
    }
}