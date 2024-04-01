//package bank.model.services;
//
//import bank.exceptions.AccountNotFound;
//import bank.exceptions.NotFound;
//import bank.model.domain.AccountTransactions;
//import bank.model.domain.BankAccount;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//@Service
//@Qualifier("BankService")
//public class BankService {
//
//    private final BankAccountRepository accountRepo;
//
//    private final TransactionHistoryRepository transactionRepo;
//
//    private final AccountTransactionRepository accountTransactionRepo;
//
//    private final Validation validation;
//
//    @Autowired
//    public BankService(@Qualifier("BankAccountDao") BankAccountRepository accountRepo,
//                       @Qualifier("TransactionHistoryDao") TransactionHistoryRepository transactionRepo,
//                       @Qualifier("AccountTransactionRepositoryImpl") AccountTransactionRepository accountTransactionRepo,
//                       Validation validation) {
//
//        this.accountRepo = accountRepo;
//        this.transactionRepo = transactionRepo;
//        this.accountTransactionRepo = accountTransactionRepo;
//        this.validation = validation;
//
//    }
//
//    public BankAccount makeDeposit(Long accountId, Double moneyAmount, String transactionMsg) throws NotFound {
//
//        if (moneyAmount <= 0) {
//            throw new IllegalArgumentException("Amount of money have to be bigger than zero");
//        }
//
//        BankAccount bankAccount = findBankAccount(accountId);
//
//        bankAccount.setBalance(bankAccount.getBalance() + moneyAmount);
//
//        TransactionHistory transaction = TransactionHistory.builder()
//                .transactionType("Deposit")
//                .msg(transactionMsg)
//                .moneyAmount(moneyAmount)
//                .build();
//
//        bankAccount = updateDb(bankAccount, transaction);
//
//        return bankAccount;
//
//    }
//
//    public BankAccount makeWithdrawal(Long accountId, double moneyAmount, String transactionMsg) throws AccountNotFound {
//
//        BankAccount bankAccount = findBankAccount(accountId);
//
//        double balance = bankAccount.getBalance();
//        double result = balance - moneyAmount;
//        if (result < 0) {
//            throw new IllegalArgumentException("You don't have enough money to withdraw " + moneyAmount);
//        }
//
//        bankAccount.setBalance(result);
//
//        TransactionHistory transaction = TransactionHistory.builder()
//                .transactionType("Withdrawal")
//                .msg(transactionMsg)
//                .moneyAmount(moneyAmount)
//                .build();
//
//        bankAccount = updateDb(bankAccount, transaction);
//
//        return bankAccount;
//
//    }
//
//    private BankAccount updateDb(BankAccount bankAccount, TransactionHistory transaction) throws AccountNotFound {
//        Long accountId = bankAccount.getId();
//        bankAccount = accountRepo.update(bankAccount, accountId);
//
//        String transactionMsg = validation.validateTransactionMsg(transaction.getMsg());
//        transaction.setMsg(transactionMsg);
//
//        transaction = transactionRepo.add(transaction);
//
//        accountTransactionRepo.add(
//            AccountTransactions.builder()
//                    .accountId(accountId)
//                    .transactionId(transaction.getId())
//                    .build()
//        );
//
//        return bankAccount;
//    }
//
//    public BankAccount findBankAccount(Long accountId) throws AccountNotFound {
//        return accountRepo.findById(accountId);
//    }
//
//    public BankAccount findBankAccount(BankAccount bankAccount) throws AccountNotFound {
//        return accountRepo.find(bankAccount);
//    }
//
//    public BankAccount findBankAccountByUserId(Long userId) throws AccountNotFound {
//        return accountRepo.find(BankAccount.builder().userId(userId).build());
//    }
//
//}
