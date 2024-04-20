package bank.controllers;

import bank.domain.BankAccount;
import bank.domain.Transaction;
import bank.dto.TransactionForm;
import bank.dto.TransactionTransformer;
import bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller("BankController")
@RequestMapping("/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankService bankService;

    private final String BANK_PAGE = "html/bank";

    @GetMapping
    public String showBank(@SessionAttribute Long userId, Model model) {
        BankAccount bankAccount = bankService.findBankAccountByUserId(userId);
        model.addAttribute("account", bankAccount);
        model.addAttribute("transactionForm", new TransactionForm());
        return BANK_PAGE;
    }

    @PostMapping("deposit/{accountId}")
    public String makeDeposit(
            Model model,
            @PathVariable Long accountId,
            @ModelAttribute("transaction") @Validated TransactionForm transactionForm, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", bankService.findById(accountId));
            return BANK_PAGE;
        }

        Transaction transaction = TransactionTransformer.convertToEntity(transactionForm);
        bankService.makeDeposit(accountId, transaction);

        return "redirect:/bank";
    }

    @PostMapping("withdrawal/{accountId}")
    public String makeWithdrawal(
            Model model,
            @PathVariable Long accountId,
            @ModelAttribute("transaction") @Validated TransactionForm transactionForm, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account", bankService.findById(accountId));
            return BANK_PAGE;
        }

        Transaction transaction = TransactionTransformer.convertToEntity(transactionForm);
        bankService.makeWithdrawal(accountId, transaction);

        return "redirect:/bank";
    }

}
