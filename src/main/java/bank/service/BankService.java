package bank.service;

import bank.domain.BankAccount;
import bank.domain.Transaction;

public interface BankService {

    BankAccount findById(Long accountId);

    BankAccount makeDeposit(Long accountId, Transaction transaction);

    BankAccount makeWithdrawal(Long accountId, Transaction transaction);

    BankAccount findBankAccountByUserId(Long userId);

}
