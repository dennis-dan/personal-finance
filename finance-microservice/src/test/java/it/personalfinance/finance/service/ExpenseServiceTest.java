
package it.personalfinance.finance.service;

import it.personalfinance.finance.entity.Expense;
import it.personalfinance.finance.repository.ExpenseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
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
    void findAllShouldReturnExpenses() {
        when(expenseRepository.findAll())
                .thenReturn(List.of(expense));

        List<Expense> result = expenseService.findAll();

        assertEquals(1, result.size());
        assertEquals("Supermercato",
                result.get(0).getDescription());

        verify(expenseRepository).findAll();
    }

    @Test
    void findByIdShouldReturnExpenseWhenFound() {
        when(expenseRepository.findById("123"))
                .thenReturn(Optional.of(expense));

        Optional<Expense> result =
                expenseService.findById("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
        assertEquals("Supermercato",
                result.get().getDescription());
    }

    @Test
    void findByIdShouldThrow404WhenNotFound() {
        when(expenseRepository.findById("999"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> expenseService.findById("999")
                );

        assertEquals(
                HttpStatus.NOT_FOUND,
                exception.getStatusCode()
        );
    }

    @Test
    void saveShouldReturnSavedExpense() {
        when(expenseRepository.save(expense))
                .thenReturn(expense);

        Expense result = expenseService.save(expense);

        assertNotNull(result);
        assertEquals("Supermercato",
                result.getDescription());

        verify(expenseRepository).save(expense);
    }

    @Test
    void updateShouldSaveExpenseWhenIdExists() {
        when(expenseRepository.findById("123"))
                .thenReturn(Optional.of(expense));

        when(expenseRepository.save(any(Expense.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Expense updated = new Expense();
        updated.setDescription("Spesa aggiornata");
        updated.setAmount(new BigDecimal("30.00"));
        updated.setCategory("Food");
        updated.setDate(LocalDate.of(2026, 10, 10));

        Expense result = expenseService.update("123", updated);

        assertEquals("123", result.getId());
        assertEquals("Spesa aggiornata",
                result.getDescription());
        assertEquals(new BigDecimal("30.00"),
                result.getAmount());

        verify(expenseRepository).save(updated);
    }

    @Test
    void updateShouldThrow404WhenIdDoesNotExist() {
        when(expenseRepository.findById("999"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> expenseService.update("999", expense)
        );

        verify(expenseRepository, never())
                .save(any(Expense.class));
    }

    @Test
    void deleteShouldDeleteExpenseWhenIdExists() {
        when(expenseRepository.findById("123"))
                .thenReturn(Optional.of(expense));

        String result = expenseService.delete("123");

        assertEquals("Expense_deleted!", result);

        verify(expenseRepository).deleteById("123");
    }

    @Test
    void deleteShouldThrow404WhenIdDoesNotExist() {
        when(expenseRepository.findById("999"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> expenseService.delete("999")
        );

        verify(expenseRepository, never())
                .deleteById("999");
    }
}
