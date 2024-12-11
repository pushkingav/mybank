package com.apushkin.context;

import com.apushkin.service.BankTransactionsService;
import com.apushkin.web.MyBankHttpServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankAppConfiguration {

    @Bean
    public BankTransactionsService bankTransactionsService() {
        return new BankTransactionsService();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Bean
    public MyBankHttpServlet myBankHttpServlet(BankTransactionsService bankTransactionsService, ObjectMapper objectMapper) {
        return new MyBankHttpServlet(bankTransactionsService, objectMapper);
    }
}
