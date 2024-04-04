package bank.model.repository;

import bank.model.domain.BankAccount;
import bank.model.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class AccountRepositoryTests {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public AccountRepositoryTests(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Test
    void checkSaveAndFindAccountTest() {
        User user = User.builder()
                .email("dimon281@gmail.com")
                .firstName("Peter")
                .lastName("Stinger")
                .password("asAS!@12")
                .phoneNumber("380981258958")
                .build();

        User savedUser = userRepository.save(user);

        double balance = 1500.0;
        long expectedId = 2L;
        BankAccount bankAccount = BankAccount.builder()
                .balance(balance)
                .user(savedUser)
                .build();

        BankAccount savedBankAccount = accountRepository.save(bankAccount);
        Optional<BankAccount> foundBankAccount = accountRepository.findById(savedBankAccount.getId());

        assertTrue(foundBankAccount.isPresent());
        BankAccount bankAccount1 = foundBankAccount.get();
        assertEquals(expectedId, bankAccount1.getId());
        assertEquals(balance, bankAccount1.getBalance());
    }

    @Test
    void checkUpdateAccountTest() {
        long id = 1L;
        double balance = 20000.0;
        BankAccount bankAccount = BankAccount.builder()
                .balance(balance)
                .id(id)
                .user(User.builder().id(id).build())
                .build();

        accountRepository.save(bankAccount);
        Optional<BankAccount> found = accountRepository.findById(id);

        assertTrue(found.isPresent());
        BankAccount foundBankAccount = found.get();
        assertEquals(id, foundBankAccount.getId());
        assertEquals(balance, foundBankAccount.getBalance());
    }

    @Test
    void checkDeleteAccountTest() {
        long id = 1L;

        accountRepository.deleteById(id);
        Optional<BankAccount> found = accountRepository.findById(id);

        assertFalse(found.isPresent());
        assertNull(found.orElse(null));
    }

}
