package com.group.budgeteer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    ObjectMapper objectMapper;

    Budget budget1;
    Expense EXPENSE_1;
    Expense EXPENSE_2;
    Expense EXPENSE_3;

    @BeforeEach
    void setUp(){
        budget1 = new Budget(UUID.randomUUID(), 5000.00, LocalDate.of(2023, 9,1), null);
        EXPENSE_1 = new Expense( UUID.randomUUID(), "exp1", "description1", 48.56, budget1);
        EXPENSE_2 = new Expense(UUID.randomUUID(),"exp2", "description2", 10.86, budget1);
        EXPENSE_3 = new Expense(UUID.randomUUID(), "exp3", "description3", 98.32, budget1);
    }

    //HELLO WORLD
    @Test
    public void shouldReturnHelloWorld_success() throws Exception {
        mockMvc.perform(get("/api/v1/budgets/hello-world"))
                .andExpect(MockMvcResultMatchers.content().string("hello World"))
                .andExpect(status().isOk()) //expect ok status
                .andDo(print());
    }

    //GET ALL
    @Test
    public void getExpenses_success() throws Exception {
        when(expenseService.getExpenses(budget1.getId())).thenReturn(List.of(EXPENSE_1, EXPENSE_2, EXPENSE_3));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/budgets/{budgetId}/expenses", budget1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data",hasSize(3)))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    //POST/CREATE
    @Test
    public void createExpense_success() throws Exception {
        when(expenseService.createExpense(Mockito.any(UUID.class), Mockito.any(Expense.class))).thenReturn(EXPENSE_1);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/budgets/{budgetId}/expenses", budget1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(EXPENSE_1));

        //once send button is clicked
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.name" ).value(EXPENSE_1.getName()))
                .andExpect(jsonPath("$.data.description" ).value(EXPENSE_1.getDescription()))
                .andExpect(jsonPath("$.data.price" ).value(EXPENSE_1.getPrice()))
                .andExpect(jsonPath("$.message" ).value("success"))
                .andDo(print());
    }

    @Test
    public void updateExpense_success() throws Exception {
        UUID expenseID = EXPENSE_1.getId();

        Expense updatedExpense = new Expense(expenseID, "exp1", "description3", 98.32, budget1);

        when(expenseService.updateExpense(Mockito.any(Expense.class))).thenReturn(updatedExpense);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/budgets/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatedExpense));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.id").value(expenseID.toString()))
                .andExpect(jsonPath("$.data.name").value(updatedExpense.getName()))
                .andExpect(jsonPath("$.data.price").value(updatedExpense.getPrice()))
                .andExpect(jsonPath("$.data.description").value(updatedExpense.getDescription()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    @Test
    void deleteExpense_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/budgets/expenses/{expenseId}", EXPENSE_1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        Mockito.verify(expenseService).deleteExpense(EXPENSE_1.getId());
    }
}
