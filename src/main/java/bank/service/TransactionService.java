package bank.service;

import bank.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getBankAccountTransactions(Long bankAccountId);

    List<Transaction> getBankAccountTransactionsByUserId(Long userId);

}
