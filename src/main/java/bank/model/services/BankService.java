package bank.model.services;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;

public interface BankService {

    BankAccount findById(Long accountId);

    BankAccount makeDeposit(Long accountId, Transaction transaction);

    BankAccount makeWithdrawal(Long accountId, Transaction transaction);

    BankAccount findBankAccountByUserId(Long userId);

}
