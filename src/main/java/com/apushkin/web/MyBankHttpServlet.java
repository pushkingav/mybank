package com.apushkin.web;

import com.apushkin.context.Application;
import com.apushkin.model.Transaction;
import com.apushkin.service.BankTransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MyBankHttpServlet extends HttpServlet {
    private final BankTransactionsService transactionsService = Application.transactionsService;
    private final ObjectMapper objectMapper = Application.objectMapper;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().equals("/transactions")) {
            response.setContentType("application/json;charset=utf-8");
            List<Transaction> transactions = transactionsService.findAllTransactions();
            objectMapper.writeValue(response.getWriter(), transactions);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equals("/transaction")) {
            int amount = Integer.parseInt(req.getParameter("amount"));
            String reference = req.getParameter("reference");
            Transaction test = transactionsService.makeTransaction(amount, reference);
            resp.setContentType("application/json;charset=utf-8");
            objectMapper.writeValue(resp.getWriter(), test);
        }
    }
}
