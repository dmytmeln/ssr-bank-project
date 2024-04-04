package bank.model.services;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;
import bank.model.repository.TransactionRepository;
import bank.model.services.servicesImpl.BankServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class BankServiceTest {

    private BankServiceImpl bankService;
    private TransactionRepository transactionRepository;

    @Autowired
    public BankServiceTest(BankServiceImpl bankService, TransactionRepository transactionRepository) {
        this.bankService = bankService;
        this.transactionRepository = transactionRepository;
    }

    @Test
    @Transactional
    void checkMakeDepositTest() {
        long id = 1;
        double expectedBalance = 2000;
        int expectedSize = 3;

        String expectedInfo = "Transaction Test Deposit";
        double moneyAmount = 1000.;
        Transaction transaction = Transaction.builder()
                .moneyAmount(moneyAmount)
                .msg(expectedInfo)
                .transactionType(expectedInfo)
                .build();

        bankService.makeDeposit(id, transaction);

        List<Transaction> transactionsByBankAccountId = transactionRepository.findTransactionsByBankAccountId(id);
        Transaction transactionDB = transactionsByBankAccountId.get(expectedSize - 1);
        BankAccount bankAccount = bankService.findById(id);

        assertEquals(expectedSize, transactionsByBankAccountId.size());
        assertEquals(expectedBalance, bankAccount.getBalance());
        assertEquals(expectedInfo, transactionDB.getMsg());
        assertEquals(moneyAmount, transactionDB.getMoneyAmount());
        assertEquals(bankAccount, transactionDB.getBankAccount());
    }

    @Test
    @Transactional
    void checkMakeWithdrawalTest() {
        long id = 1;
        double expectedBalance = 0;
        int expectedSize = 3;

        String expectedInfo = "Transaction Test Withdrawal";
        double moneyAmount = 1000.;
        Transaction transaction = Transaction.builder()
                .moneyAmount(moneyAmount)
                .msg(expectedInfo)
                .transactionType(expectedInfo)
                .build();

        bankService.makeWithdrawal(id, transaction);

        List<Transaction> transactionsByBankAccountId = transactionRepository.findTransactionsByBankAccountId(id);
        Transaction transactionDB = transactionsByBankAccountId.get(expectedSize - 1);
        BankAccount bankAccount = bankService.findById(id);

        assertEquals(expectedSize, transactionsByBankAccountId.size());
        assertEquals(expectedBalance, bankAccount.getBalance());
        assertEquals(expectedInfo, transactionDB.getMsg());
        assertEquals(moneyAmount, transactionDB.getMoneyAmount());
        assertEquals(bankAccount, transactionDB.getBankAccount());
    }

    @Test
    void checkFindByIdTest() {
        long id = 1;
        double expectedBalance = 1000;
        BankAccount bankAccount = bankService.findById(id);

        assertEquals(expectedBalance, bankAccount.getBalance());
        assertEquals(id, bankAccount.getId());
    }

    @Test
    void checkFindByUserIdTest() {
        long userId = 1;
        long expectedId = 1;
        double expectedBalance = 1000;

        BankAccount bankAccount = bankService.findBankAccountByUserId(userId);

        assertEquals(expectedBalance, bankAccount.getBalance());
        assertEquals(expectedId, bankAccount.getId());
    }

}