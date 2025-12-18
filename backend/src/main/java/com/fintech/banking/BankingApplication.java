package com.fintech.banking;

import java.math.BigDecimal;

import com.fintech.banking.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }
}
