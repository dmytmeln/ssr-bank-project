package bank.model.repository;

import bank.model.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findBankAccountByUserId(Long userId);

}
