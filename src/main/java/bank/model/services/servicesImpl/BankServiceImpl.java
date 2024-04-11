package bank.model.services.servicesImpl;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;
import bank.model.repository.AccountRepository;
import bank.model.repository.TransactionRepository;
import bank.model.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    @Override
    public BankAccount findById(Long accountId) {
        return accountRepo.findById(accountId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Account with id: %d not found!".formatted(accountId)
                )
        );
    }

    @Override
    public BankAccount makeDeposit(Long accountId, Transaction transaction) {
        BankAccount bankAccount = findById(accountId);
        bankAccount.setBalance(bankAccount.getBalance() + transaction.getMoneyAmount());
        if (transaction.getTransactionType().isBlank()) {
            transaction.setTransactionType("Deposit");
        }
        update(bankAccount, transaction);

        return bankAccount;
    }

    @Override
    public BankAccount makeWithdrawal(Long accountId, Transaction transaction) {
        BankAccount bankAccount = findById(accountId);

        double balance = bankAccount.getBalance();
        Double moneyAmount = transaction.getMoneyAmount();
        double result = balance - moneyAmount;
        if (result < 0) {
            throw new IllegalArgumentException("You don't have enough money to withdraw " + moneyAmount);
        }

        bankAccount.setBalance(result);
        if (transaction.getTransactionType().isBlank()) {
            transaction.setTransactionType("Withdrawal");
        }
        update(bankAccount, transaction);

        return bankAccount;
    }

    private void update(BankAccount bankAccount, Transaction transaction) {
        transaction.setBankAccount(bankAccount);
        if (transaction.getMsg().isBlank()) {
            transaction.setMsg("Standard Transaction Message");
        }

        accountRepo.save(bankAccount);
        transactionRepo.save(transaction);
    }

    @Override
    public BankAccount findBankAccountByUserId(Long userId) {
        return accountRepo.findBankAccountByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Bank Account with user id: %d not found!".formatted(userId)
                )
        );
    }

}