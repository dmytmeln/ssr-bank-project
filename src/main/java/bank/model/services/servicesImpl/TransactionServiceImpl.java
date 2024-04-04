//package bank.model.services;
//
//import bank.exceptions.NotFound;
//import bank.exceptions.TransactionNotFound;
//import bank.model.domain.AccountTransactions;
//import bank.model.domain.BankAccount;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Qualifier("TransactionService")
//public class TransactionService {
//
//    private final BankAccountRepository accountRepo;
//
//    private final TransactionHistoryRepository transactionRepo;
//
//    private final AccountTransactionRepository accountTransactionRepo;
//
//    @Autowired
//    public TransactionService(
//            @Qualifier("BankAccountDao") BankAccountRepository accountRepo,
//            @Qualifier("TransactionHistoryDao") TransactionHistoryRepository transactionRepo,
//            @Qualifier("AccountTransactionRepositoryImpl") AccountTransactionRepository accountTransactionRepo
//    ) {
//        this.accountRepo = accountRepo;
//        this.transactionRepo = transactionRepo;
//        this.accountTransactionRepo = accountTransactionRepo;
//    }
//
//    public List<TransactionHistory> getTransactionsByUserId(Long userId) throws NotFound {
//
//        BankAccount bankAccount = accountRepo.find(BankAccount.builder().userId(userId).build());
//
//        List<AccountTransactions> accountTransactions = accountTransactionRepo.findByAccountId(bankAccount.getId());
//
//        return accountTransactions.stream()
//                .reduce(new ArrayList<>(),
//                        this::addTransactionToList,
//                        (list1, list2) -> {
//                            list1.addAll(list2);
//                            return list1;
//                        });
//
//    }
//
//    private List<TransactionHistory> addTransactionToList(List<TransactionHistory> list, AccountTransactions transaction) {
//        try {
//            list.add(transactionRepo.findById(transaction.getTransactionId()));
//            return list;
//        } catch (TransactionNotFound e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
