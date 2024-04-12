package bank.controllers;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;
import bank.model.services.BankService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankController.class)
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @Captor
    ArgumentCaptor<Transaction> transactionCaptor;

    private static Long accountId;
    private static BankAccount bankAccount;

    @BeforeAll
    static void init() {
        accountId = 1L;

        bankAccount = BankAccount.builder()
                .balance(0D)
                .id(accountId)
                .build();
    }

    @BeforeEach
    void setup() {
        when(bankService.findBankAccountByUserId(accountId)).thenReturn(bankAccount);
        when(bankService.findById(accountId)).thenReturn(bankAccount);
    }

    @Test
    void checkShowBank() throws Exception {
        mockMvc.perform(get("/bank").sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", bankAccount))
                .andExpect(model().attributeExists("transaction"))
                .andExpect(model().attribute("transaction", new Transaction()))
                .andExpect(view().name("html/bank"));

        verify(bankService, times(1)).findBankAccountByUserId(accountId);
    }

    @Test
    void checkInvalidShowBank() throws Exception {
        long id = -1;

        when(bankService.findBankAccountByUserId(id)).thenThrow(new EntityNotFoundException(
                "BankAccount with  id [%d] not found".formatted(id)
        ));

        mockMvc.perform(get("/bank").sessionAttr("userId", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("html/error"));

        verify(bankService, times(1)).findBankAccountByUserId(id);
    }

    @Test
    void checkMakeDeposit() throws Exception {

        String message = "Message";
        String transactionType = "Type";
        double expectedBalance = bankAccount.getBalance() + 1000D;

        when(bankService.makeDeposit(eq(accountId), any(Transaction.class))).thenAnswer(invocationOnMock -> {
            Long bankAccountId = invocationOnMock.getArgument(0);
            Transaction transaction = invocationOnMock.getArgument(1);
            BankAccount account = bankService.findById(bankAccountId);
            account.setBalance(account.getBalance() + transaction.getMoneyAmount());
            transaction.setBankAccount(account);
            transaction.setId(bankAccountId);

            return account;
        });

        mockMvc.perform(post("/bank/deposit/{accountId}", accountId)
                        .param("moneyAmount", "1000")
                        .param("msg", message)
                        .param("transactionType", transactionType))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank"))
                .andExpect(view().name("redirect:/bank"));

        verify(bankService, times(2)).findById(accountId);
        verify(bankService, times(1)).makeDeposit(eq(accountId), transactionCaptor.capture());

        Transaction captorValue = transactionCaptor.getValue();

        assertEquals(message, captorValue.getMsg());
        assertEquals(transactionType, captorValue.getTransactionType());
        assertEquals(bankAccount, captorValue.getBankAccount());
        assertEquals(accountId, captorValue.getId());
        assertEquals(expectedBalance, bankAccount.getBalance());

    }

    @Test
    void checkInvalidMakeDeposit() throws Exception {
        mockMvc.perform(post("/bank/deposit/{accountId}", accountId)
                        .param("moneyAmount", "-1000"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", bankAccount))
                .andExpect(view().name("html/bank"));
    }

    @Test
    void checkMakeWithdrawal() throws Exception {

        String message = "Message";
        String transactionType = "Type";
        double expectedBalance = bankAccount.getBalance() - 1000D;

        when(bankService.makeWithdrawal(eq(accountId), any(Transaction.class))).thenAnswer(invocationOnMock -> {
            Long bankAccountId = invocationOnMock.getArgument(0);
            Transaction transaction = invocationOnMock.getArgument(1);
            BankAccount account = bankService.findById(bankAccountId);
            account.setBalance(account.getBalance() - transaction.getMoneyAmount());
            transaction.setBankAccount(account);
            transaction.setId(bankAccountId);

            return account;
        });

        mockMvc.perform(post("/bank/withdrawal/{accountId}", accountId)
                        .param("moneyAmount", "1000")
                        .param("msg", message)
                        .param("transactionType", transactionType))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank"))
                .andExpect(view().name("redirect:/bank"));

        verify(bankService, times(2)).findById(accountId);
        verify(bankService, times(1)).makeWithdrawal(eq(accountId), transactionCaptor.capture());

        Transaction captorValue = transactionCaptor.getValue();

        assertEquals(message, captorValue.getMsg());
        assertEquals(transactionType, captorValue.getTransactionType());
        assertEquals(bankAccount, captorValue.getBankAccount());
        assertEquals(accountId, captorValue.getId());
        assertEquals(expectedBalance, bankAccount.getBalance());

    }

    @Test
    void checkInvalidMakeWithdrawal() throws Exception {
        mockMvc.perform(post("/bank/withdrawal/{accountId}", accountId)
                        .param("moneyAmount", "-1000"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", bankAccount))
                .andExpect(view().name("html/bank"));
    }

}
