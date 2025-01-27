package com.apushkin.web;

import com.apushkin.model.Transaction;
import com.apushkin.service.BankTransactionsService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class BankApiController {
    private final BankTransactionsService bankTransactionsService;

    public BankApiController(BankTransactionsService bankTransactionsService) {
        this.bankTransactionsService = bankTransactionsService;
    }

    @GetMapping(value = "/")
    @ResponseBody
    public String index() {
        return "Hello from Bank API";
    }

    @GetMapping(value = "/transactions")
    @ResponseBody
    public List<Transaction> getTransactions() {
        return bankTransactionsService.findAllTransactions();
    }

    @PostMapping(value = "/transaction")
    @ResponseBody
    public Transaction createTransaction(@RequestBody @Valid Transaction transaction) {
        return bankTransactionsService.makeTransaction(transaction.getAmount(), transaction.getReference());
    }
}
