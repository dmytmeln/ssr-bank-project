package bank.controllers;

import bank.model.domain.BankAccount;
import bank.model.services.BankService;
import bank.model.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final BankService bankService;

    private final String TRANSACTION_PAGE = "html/transactions";

    @GetMapping
    public String getTransactionPage(@SessionAttribute Long userId, Model model) {
        BankAccount bankAccount = bankService.findBankAccountByUserId(userId);
        model.addAttribute("transactions", transactionService.getBankAccountTransactions(bankAccount.getId()));

        return TRANSACTION_PAGE;
    }

}
