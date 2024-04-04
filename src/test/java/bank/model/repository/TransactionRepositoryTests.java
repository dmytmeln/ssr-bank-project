package bank.model.repository;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class TransactionRepositoryTests {

    private TransactionRepository transactionRepo;

    @Autowired
    public TransactionRepositoryTests(TransactionRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Test
    void testFindTransactionHistoriesByBankAccountId() {
        Long bankAccountId = 1L;

        List<Transaction> transactionHistories = transactionRepo.findTransactionsByBankAccountId(bankAccountId);

        assertEquals(2, transactionHistories.size());
    }

    @Test
    void testSaveAndFindTransactionHistory() {
        Transaction transaction = Transaction.builder()
                .msg("Test message")
                .moneyAmount(500.0)
                .bankAccount(BankAccount.builder().id(1L).build())
                .transactionType("Test type")
                .build();

        Transaction savedTransaction = transactionRepo.save(transaction);
        Optional<Transaction> foundTransactionHistory = transactionRepo.findById(savedTransaction.getId());

        assertTrue(foundTransactionHistory.isPresent());
        assertEquals("Test message", foundTransactionHistory.get().getMsg());
    }

    @Test
    void testDeleteTransactionHistory() {
        long id = 1L;
        transactionRepo.deleteById(id);

        int expectedSize = 1;

        assertEquals(expectedSize, transactionRepo.findAll().size());
        assertNull(transactionRepo.findById(id).orElse(null));
    }


    @Test
    void updateTransactionHistory() {
        double moneyAmount = 10000.0;
        Transaction transaction = transactionRepo.findById(1L).get();
        transaction = transaction.toBuilder()
                .moneyAmount(moneyAmount)
                .build();

        Transaction savedTransaction = transactionRepo.save(transaction);
        Optional<Transaction> foundTransactionHistory = transactionRepo.findById(savedTransaction.getId());

        assertTrue(foundTransactionHistory.isPresent());
        assertEquals(moneyAmount, foundTransactionHistory.get().getMoneyAmount());
    }

}
