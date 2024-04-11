package bank.model.services;

import bank.model.domain.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getBankAccountTransactions(Long bankAccountId);

    List<Transaction> getBankAccountTransactionsByUserId(Long userId);

}
