package bank.service;

import bank.model.BankAccount;
import bank.dto.TransactionForm;

public interface BankService {

    BankAccount findById(Long accountId);

    BankAccount makeDeposit(Long accountId, TransactionForm transaction);

    BankAccount makeWithdrawal(Long accountId, TransactionForm transaction);

    BankAccount findBankAccountByUserId(Long userId);

}
