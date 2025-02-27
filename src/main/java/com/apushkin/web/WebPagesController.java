package com.apushkin.web;

import com.apushkin.model.Transaction;
import com.apushkin.service.BankTransactionsService;
import com.apushkin.web.forms.TransactionForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebPagesController {
    private final BankTransactionsService bankTransactionsService;

    public WebPagesController(BankTransactionsService bankTransactionsService) {
        this.bankTransactionsService = bankTransactionsService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index.html";
    }

    @GetMapping("/account/{userId}")
    public String transactions(Model model,  @PathVariable("userId") String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId not to be null");
        }
        List<Transaction> transactions = bankTransactionsService.findAllTransactions();
        model.addAttribute("transactions", transactions);
        model.addAttribute("userId", userId);
        model.addAttribute("newTransaction", new TransactionForm());
        return "transactions.html";
    }

    @PostMapping("/transactions/new")
    public String newTransaction(@ModelAttribute("newTransaction") @Valid TransactionForm transactionForm,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "transactions.html";
        }
        bankTransactionsService.makeTransaction(transactionForm.getAmount(), transactionForm.getReference());
        List<Transaction> transactions = bankTransactionsService.findAllTransactions();
        model.addAttribute("transactions", transactions);
        model.addAttribute("userId", transactionForm.getReceivingUserId());
        return "transactions.html";
    }
}
