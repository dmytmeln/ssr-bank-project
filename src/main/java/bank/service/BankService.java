package bank.service;

import bank.domain.BankAccount;
import bank.domain.Transaction;
import bank.dto.TransactionForm;

public interface BankService {

    BankAccount findById(Long accountId);

    BankAccount makeDeposit(Long accountId, TransactionForm transaction);

    BankAccount makeWithdrawal(Long accountId, TransactionForm transaction);

    BankAccount findBankAccountByUserId(Long userId);

}
