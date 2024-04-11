package bank.model.services.servicesImpl;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;
import bank.model.repository.TransactionRepository;
import bank.model.services.BankService;
import bank.model.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepo;
    private final BankService bankService;

    @Override
    public List<Transaction> getBankAccountTransactions(Long bankAccountId) {
        bankService.findById(bankAccountId);
        return transactionRepo.findTransactionsByBankAccountId(bankAccountId);
    }

    @Override
    public List<Transaction> getBankAccountTransactionsByUserId(Long userId) {
        BankAccount bankAccount = bankService.findBankAccountByUserId(userId);
        return getBankAccountTransactions(bankAccount.getId());
    }

}