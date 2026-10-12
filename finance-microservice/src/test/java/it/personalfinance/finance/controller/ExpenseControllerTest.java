
package it.personalfinance.finance.controller;

import tools.jackson.databind.ObjectMapper;
import it.personalfinance.finance.entity.Expense;
import it.personalfinance.finance.service.ExpenseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ExpenseService expenseService;

    private Expense expense;

    @BeforeEach
    void setUp() {
        expense = new Expense();
        expense.setId("123");
        expense.setDescription("Supermercato");
        expense.setAmount(new BigDecimal("25.50"));
        expense.setCategory("Food");
        expense.setDate(LocalDate.of(2026, 10, 10));
    }

    @Test
    void findAllShouldReturn200() throws Exception {
        when(expenseService.findAll())
                .thenReturn(List.of(expense));

        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].description")
                        .value("Supermercato"));
    }

    @Test
    void findByIdShouldReturnExpense() throws Exception {
        when(expenseService.findById("123"))
                .thenReturn(Optional.of(expense));

        mockMvc.perform(get("/api/expenses/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.description")
                        .value("Supermercato"));
    }

    @Test
    void saveShouldReturnSavedExpense() throws Exception {
        when(expenseService.save(any(Expense.class)))
                .thenReturn(expense);

        mockMvc.perform(post("/api/expenses/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description")
                        .value("Supermercato"));
    }

    @Test
    void updateShouldReturnUpdatedExpense() throws Exception {
        Expense updated = new Expense();
        updated.setDescription("Spesa aggiornata");
        updated.setAmount(new BigDecimal("30.00"));
        updated.setCategory("Food");
        updated.setDate(LocalDate.of(2026, 10, 10));

        when(expenseService.update(
                org.mockito.ArgumentMatchers.eq("123"),
                any(Expense.class)))
                .thenReturn(updated);

        updated.setId("123");

        mockMvc.perform(put("/api/expenses/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description")
                        .value("Spesa aggiornata"));
    }

    @Test
    void deleteShouldReturnSuccessMessage() throws Exception {
        when(expenseService.delete("123"))
                .thenReturn("Expense_deleted!");

        mockMvc.perform(delete("/api/expenses/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Expense_deleted!"));

        verify(expenseService).delete("123");
    }
}
