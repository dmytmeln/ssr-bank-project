package bank.controllers;

import bank.domain.BankAccount;
import bank.domain.Transaction;
import bank.dto.TransactionForm;
import bank.service.BankService;
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
    private BankService bankServiceMock;

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
        when(bankServiceMock.findBankAccountByUserId(accountId)).thenReturn(bankAccount);
        when(bankServiceMock.findById(accountId)).thenReturn(bankAccount);
    }

    @Test
    void testShowBank() throws Exception {
        mockMvc.perform(get("/bank").sessionAttr("userId", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", bankAccount))
                .andExpect(model().attributeExists("transactionForm"))
                .andExpect(model().attribute("transactionForm", new TransactionForm()))
                .andExpect(view().name("html/bank"));

        verify(bankServiceMock, times(1)).findBankAccountByUserId(accountId);
    }

    @Test
    void testInvalidShowBank() throws Exception {
        long id = -1;

        when(bankServiceMock.findBankAccountByUserId(id)).thenThrow(new EntityNotFoundException(
                "BankAccount with  id [%d] not found".formatted(id)
        ));

        mockMvc.perform(get("/bank").sessionAttr("userId", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("html/error"));

        verify(bankServiceMock, times(1)).findBankAccountByUserId(id);
    }

    @Test
    void testMakeDeposit() throws Exception {
        String message = "Message";
        String type = "Type";
        double expectedBalance = bankAccount.getBalance() + 1000D;

        when(bankServiceMock.makeDeposit(eq(accountId), any(Transaction.class))).thenAnswer(invocationOnMock -> {
            Long bankAccountId = invocationOnMock.getArgument(0);
            Transaction transaction = invocationOnMock.getArgument(1);
            BankAccount account = bankServiceMock.findById(bankAccountId);
            account.setBalance(account.getBalance() + transaction.getMoneyAmount());
            transaction.setBankAccount(account);
            transaction.setId(bankAccountId);

            return account;
        });

        mockMvc.perform(post("/bank/deposit/{accountId}", accountId)
                        .param("moneyAmount", "1000")
                        .param("msg", message)
                        .param("type", type)
                        .flashAttr("transactionForm", new TransactionForm()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank"))
                .andExpect(view().name("redirect:/bank"));

        verify(bankServiceMock, times(1)).findById(accountId);
        verify(bankServiceMock, times(1)).makeDeposit(eq(accountId), transactionCaptor.capture());

        Transaction captorValue = transactionCaptor.getValue();

        assertEquals(message, captorValue.getMsg());
        assertEquals(type, captorValue.getType());
        assertEquals(bankAccount, captorValue.getBankAccount());
        assertEquals(accountId, captorValue.getId());
        assertEquals(expectedBalance, bankAccount.getBalance());
    }

    @Test
    void testInvalidMakeDeposit() throws Exception {
        mockMvc.perform(post("/bank/deposit/{accountId}", accountId)
                        .param("moneyAmount", "-1000")
                        .flashAttr("transactionForm", new TransactionForm()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", bankAccount))
                .andExpect(view().name("html/bank"));
    }

    @Test
    void testMakeWithdrawal() throws Exception {

        String message = "Message";
        String type = "Type";
        double expectedBalance = bankAccount.getBalance() - 1000D;

        when(bankServiceMock.makeWithdrawal(eq(accountId), any(Transaction.class))).thenAnswer(invocationOnMock -> {
            Long bankAccountId = invocationOnMock.getArgument(0);
            Transaction transaction = invocationOnMock.getArgument(1);
            BankAccount account = bankServiceMock.findById(bankAccountId);
            account.setBalance(account.getBalance() - transaction.getMoneyAmount());
            transaction.setBankAccount(account);
            transaction.setId(bankAccountId);

            return account;
        });

        mockMvc.perform(post("/bank/withdrawal/{accountId}", accountId)
                        .param("moneyAmount", "1000")
                        .param("msg", message)
                        .param("type", type)
                        .flashAttr("transactionForm", new TransactionForm()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bank"))
                .andExpect(view().name("redirect:/bank"));

        verify(bankServiceMock, times(1)).findById(accountId);
        verify(bankServiceMock, times(1)).makeWithdrawal(eq(accountId), transactionCaptor.capture());

        Transaction captorValue = transactionCaptor.getValue();

        assertEquals(message, captorValue.getMsg());
        assertEquals(type, captorValue.getType());
        assertEquals(bankAccount, captorValue.getBankAccount());
        assertEquals(accountId, captorValue.getId());
        assertEquals(expectedBalance, bankAccount.getBalance());

    }

    @Test
    void testInvalidMakeWithdrawal() throws Exception {
        mockMvc.perform(post("/bank/withdrawal/{accountId}", accountId)
                        .param("moneyAmount", "-1000")
                        .flashAttr("transactionForm", new TransactionForm()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andExpect(model().attribute("account", bankAccount))
                .andExpect(view().name("html/bank"));
    }

}
