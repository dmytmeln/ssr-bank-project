package bank.controllers;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;
import bank.model.services.BankService;
import bank.model.services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @MockBean
    private TransactionService transactionServiceMock;

    private static Long id;
    private static BankAccount bankAccount;
    private static Transaction transaction;

    @BeforeAll
    static void init() {
        id = 1L;

        transaction = Transaction.builder()
                .moneyAmount(1000D)
                .msg("Message")
                .transactionType("Type")
                .id(id)
                .build();

        bankAccount = BankAccount.builder()
                .balance(0D)
                .id(id)
                .build();
    }

    @BeforeEach
    void setup() {
        when(bankService.findBankAccountByUserId(id)).thenReturn(bankAccount);

        when(transactionServiceMock.getBankAccountTransactions(id)).thenReturn(List.of(transaction));
    }

    @Test
    void testGetTransactionPage() throws Exception {
        mockMvc.perform(get("/transactions").sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("transactions"))
                .andExpect(model().attribute("transactions", List.of(transaction)))
                .andExpect(view().name("html/transactions"));

        verify(bankService, times(1)).findBankAccountByUserId(id);
        verify(transactionServiceMock, times(1)).getBankAccountTransactions(id);
    }

    @Test
    void testGetTransactionPage_WithNoTransactions() throws Exception {
        when(transactionServiceMock.getBankAccountTransactions(id)).thenReturn(List.of());

        mockMvc.perform(get("/transactions").sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("transactions"))
                .andExpect(model().attribute("transactions", List.of()))
                .andExpect(view().name("html/transactions"));

        verify(bankService, times(1)).findBankAccountByUserId(id);
        verify(transactionServiceMock, times(1)).getBankAccountTransactions(id);
    }

    @Test
    void testInvalidGetTransactionPage_WithNonexistentAccount() throws Exception {
        long id = -1;

        when(bankService.findBankAccountByUserId(id)).thenThrow(new EntityNotFoundException(
                "BankAccount with  id [%d] not found".formatted(id)
        ));;

        mockMvc.perform(get("/transactions").sessionAttr("userId", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("html/error"));

        verify(bankService, times(1)).findBankAccountByUserId(id);
        verify(transactionServiceMock, times(0)).getBankAccountTransactions(id);
    }

}
