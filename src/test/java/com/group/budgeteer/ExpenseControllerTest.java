package com.group.budgeteer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.budgeteer.models.Budget;
import com.group.budgeteer.models.Expense;
import com.group.budgeteer.models.User;
import com.group.budgeteer.services.ExpenseService;
import org.junit.Ignore;
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

    Budget budget1 = new Budget(UUID.randomUUID(), 5000.00, LocalDate.of(2023, 9,1), null);
    Expense EXPENSE_1 = new Expense("exp1", "description1", 48.56, budget1);
    Expense EXPENSE_2 = new Expense("exp2", "description2", 10.86, budget1);
    Expense EXPENSE_3 = new Expense("exp3", "description3", 98.32, budget1);


//    User user2 = new User();
//    Budget budget2 = new Budget();
//    Expense EXPENSE_2 = new Expense("food", "description 2", 21.68, user2, budget2);




    //HELLO WORLD
    @Test
    public void shouldReturnHelloWorld_success() throws Exception {
        mockMvc.perform(get("/api/v1/hello-world"))
                .andExpect(MockMvcResultMatchers.content().string("hello World")) //bc dealing with a string, expects content of hello World, CASE SENSITIVE
                .andExpect(status().isOk()) //expect ok status
                .andDo(print());
    }

    //GET ALL
    @Test
    public void getExpenses_success() throws Exception {
        List<Expense> expenses = new ArrayList<>(Arrays.asList(EXPENSE_1, EXPENSE_2, EXPENSE_3));
        when(expenseService.getExpenses(budget1.getId())).thenReturn(expenses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/budgets/{budgetId}/expenses", budget1.getId()) //TODO enter the correct ID to budget1
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data",hasSize(3))) //$ is a placeholder for jsonpath, so here we want to get the payload data
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }

    //GET ONE
//    @Test
//    @Ignore
//    public void getExpense_success() throws Exception {
//        when(expenseService.getExpense(EXPENSE_1.getBudget().getId() , EXPENSE_1.getId())).thenReturn(EXPENSE_1);
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/budgets/{budgetId}/expenses/{expenseId}", "1") //bc this is a get request
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.id" ).value(EXPENSE_1.getId()))
//                .andExpect(jsonPath("$.data.name" ).value(EXPENSE_1.getName()))
//                .andExpect(jsonPath("$.data.description" ).value(EXPENSE_1.getDescription()))
//                .andExpect(jsonPath("$.data.price" ).value(EXPENSE_1.getPrice()))
//                .andExpect(jsonPath("$.message" ).value("success"))
//                .andDo(print());
//    }
//
//    @Test
//    @Ignore
//    void createExpense_success() throws Exception {
//        when(expenseService.createExpense(Mockito.any(UUID.class), Mockito.any(Expense.class))).thenReturn(EXPENSE_1);
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/budgets/{budgetId}/expenses")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(EXPENSE_1));
//
//        //once send button is clicked
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.data.name" ).value(EXPENSE_1.getName()))
//                .andExpect(jsonPath("$.data.description" ).value(EXPENSE_1.getDescription()))
//                .andExpect(jsonPath("$.data.price" ).value(EXPENSE_1.getPrice()))
//                .andExpect(jsonPath("$.message" ).value("success"))
//                .andDo(print());
//    }
//
//    @Test
//    @Ignore
//    void updateExpense_success() throws Exception {
//        UUID uuid = UUID.randomUUID(); //TODO figure out how to assign uuid
//
//        User user4 = new User();
//        Budget budget4 = new Budget();
//        Expense EXPENSE_4 = new Expense(uuid, "expense 4", "description 4", 31.68, user4, budget4);
//
//        Expense updatedExpense = new Expense(uuid, "expense 4", "updated description", 60.00, user4, budget4);
//        when(expenseService.updateExpense(Mockito.any(UUID.class), Mockito.any(UUID.class), Mockito.any(Expense.class))).thenReturn(updatedExpense);
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/budgets/{budgetId}/expenses/{expenseId}", UUID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.objectMapper.writeValueAsString(EXPENSE_4));
//
//        //  Perform the PUT request using MockMvc and make assertions on the response.
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.data.id").value(updatedExpense.getId()))
//                .andExpect(jsonPath("$.data.name").value(updatedExpense.getName()))
//                .andExpect(jsonPath("$.data.description").value(updatedExpense.getDescription()))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andDo(print());
//    }
//
//    @Test
//    @Ignore
//    void deleteExpense_success() throws Exception {
//        when(expenseService.deleteExpense(EXPENSE_1.getId()).thenReturn(EXPENSE_1));
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/expenses/{expenseId}", UUID)
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.data.id").value(EXPENSE_1.getId()))
//                .andExpect(jsonPath("$.data.name").value(EXPENSE_1.getName()))
//                .andExpect(jsonPath("$.data.description").value(EXPENSE_1.getDescription()))
//                .andExpect(jsonPath("$.message").value("success"))
//                .andDo(print());
//    }
}

//TODO add docstring
