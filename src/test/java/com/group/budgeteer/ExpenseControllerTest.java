package com.group.budgeteer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.budgeteer.controllers.ExpenseController;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.models.User;
import com.group.budgeteer.services.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    ObjectMapper objectMapper;

    User user1 = new User();
    Budget budget1 = new Budget();
    Expense EXPENSE_1 = new Expense("gas", "description 1", 48.56, user1, budget1);

    User user2 = new User();
    Budget budget2 = new Budget();
    Expense EXPENSE_2 = new Expense("food", "description 2", 21.68, user2, budget2);

    //HELLO WORLD
    @Test
    public void shouldReturnHelloWorld_success() throws Exception {
        mockMvc.perform(get("/api/hello-world/"))
                .andExpect(MockMvcResultMatchers.content().string("hello World")) //bc dealing with a string, expects content of hello World, CASE SENSITIVE
                .andExpect(status().isOk()) //expect ok status
                .andDo(print());
    }

    //GET ALL
    @Test
    public void getExpenses_success() throws Exception {
        List<Expense> expenses = new ArrayList<>(Arrays.asList(EXPENSE_1, EXPENSE_2));
        when(expenseService.getExpenses(EXPENSE_1.getBudget().getId())).thenReturn(expenses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/budgets/{budgetId}/expenses") //this line is just another way of  performing mockMvc.perform(get("/api/hello-world/"))
                        .contentType(MediaType.APPLICATION_JSON)) //bc we are dealing with an object, not a string
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data",hasSize(2))) //$ is a placeholder for jsonpath, so here we want to get the payload data
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    //GET ONE
    @Test
    public void getExpense_success() throws Exception {
        when(expenseService.getExpense(EXPENSE_1.getBudget().getId() , EXPENSE_1.getId())).thenReturn(EXPENSE_1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/budgets/{budgetId}/expenses/{expenseId}", "1") //bc this is a get request
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id" ).value(EXPENSE_1.getId()))
                .andExpect(jsonPath("$.data.name" ).value(EXPENSE_1.getName()))
                .andExpect(jsonPath("$.data.description" ).value(EXPENSE_1.getDescription()))
                .andExpect(jsonPath("$.data.price" ).value(EXPENSE_1.getPrice()))
                .andExpect(jsonPath("$.message" ).value("success"))
                .andDo(print());
    }
}


//TODO PUT
//TODO POST
//TODO DELETE
