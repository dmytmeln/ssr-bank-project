package bank.model.repository;

import bank.model.domain.BankAccount;
import bank.model.domain.TransactionHistory;
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
public class TransactionHistoryRepositoryTests {

    private TransactionHistoryRepository transactionRepo;

    @Autowired
    public TransactionHistoryRepositoryTests(TransactionHistoryRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Test
    void testFindTransactionHistoriesByBankAccountId() {
        Long bankAccountId = 1L;

        List<TransactionHistory> transactionHistories = transactionRepo.findTransactionHistoriesByBankAccountId(bankAccountId);

        assertEquals(2, transactionHistories.size());
    }

    @Test
    void testSaveAndFindTransactionHistory() {
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .msg("Test message")
                .moneyAmount(500.0)
                .bankAccount(BankAccount.builder().id(1L).build())
                .transactionType("Test type")
                .build();

        TransactionHistory savedTransactionHistory = transactionRepo.save(transactionHistory);
        Optional<TransactionHistory> foundTransactionHistory = transactionRepo.findById(savedTransactionHistory.getId());

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
        TransactionHistory transactionHistory = transactionRepo.findById(1L).get();
        transactionHistory = transactionHistory.toBuilder()
                .moneyAmount(moneyAmount)
                .build();

        TransactionHistory savedTransactionHistory = transactionRepo.save(transactionHistory);
        Optional<TransactionHistory> foundTransactionHistory = transactionRepo.findById(savedTransactionHistory.getId());

        assertTrue(foundTransactionHistory.isPresent());
        assertEquals(moneyAmount, foundTransactionHistory.get().getMoneyAmount());
    }

}
