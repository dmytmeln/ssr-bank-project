package bank.controllers;

import bank.model.domain.BankAccount;
import bank.model.domain.Transaction;
import bank.model.services.servicesImpl.BankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller("BankController")
@RequestMapping("/bank")
public class BankController {

    private BankServiceImpl bankService;
    @Autowired
    public BankController(BankServiceImpl bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public String showBank(@SessionAttribute Long userId, Model model, Transaction transaction) {
        BankAccount bankAccount = bankService.findBankAccountByUserId(userId);
        model.addAttribute("account", bankAccount);
        return "html/bank";
    }

    @PostMapping("deposit/{accountId}")
    public String makeDeposit(
            Model model,
            @PathVariable Long accountId,
            @ModelAttribute("transaction") @Valid Transaction transaction,
            BindingResult bindingResult
    ) {

        BankAccount bankAccount = bankService.findById(accountId);
        model.addAttribute("account", bankAccount);
        if (bindingResult.hasErrors()) {
            return "html/bank";
        }

        bankService.makeDeposit(accountId, transaction);

        return "redirect:/bank";
    }

    @PostMapping("withdrawal/{accountId}")
    public String makeWithdrawal(
            Model model,
            @PathVariable Long accountId,
            @ModelAttribute("transaction") @Valid Transaction transaction,
            BindingResult bindingResult
    ) {

        BankAccount bankAccount = bankService.findById(accountId);
        model.addAttribute("account", bankAccount);
        if (bindingResult.hasErrors()) {
            return "html/bank";
        }

        bankService.makeWithdrawal(accountId, transaction);

        return "redirect:/bank";
    }

}
