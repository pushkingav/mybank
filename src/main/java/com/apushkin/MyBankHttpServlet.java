package com.apushkin;

import com.apushkin.model.Transaction;
import com.apushkin.service.BankTransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MyBankHttpServlet extends HttpServlet {
    BankTransactionsService transactionsService = new BankTransactionsService();
    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().equals("/")) {
                response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("""
                    <html>
                        <h1>It's running</h1>
                        <p>The actual my bank app is serving API requests</p>
                    </html>
                    """);
        }
        if (request.getRequestURI().equals("/transactions")) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print("[]");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equals("/transaction")) {
            Transaction test = transactionsService.makeTransaction(1, "test");
            resp.setContentType("application/json;charset=utf-8");
            objectMapper.writeValue(resp.getWriter(), test);
        }
    }
}
