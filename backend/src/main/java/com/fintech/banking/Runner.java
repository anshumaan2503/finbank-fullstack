package com.fintech.banking;

import com.fintech.banking.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Runner {

    @Bean
    CommandLineRunner testDb(AccountRepository repo) {
        return args -> {
            System.out.println("Accounts count: " + repo.count());
        };
    }
}
