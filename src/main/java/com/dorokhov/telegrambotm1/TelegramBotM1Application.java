package com.dorokhov.telegrambotm1;

import com.dorokhov.telegrambotm1.Service.TelegramBot;
import com.dorokhov.telegrambotm1.config.BotConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.dorokhov.telegrambotm1.repositories.UserRepository")
@EnableJpaRepositories(basePackages = "com.dorokhov.telegrambotm1.repositories")
public class TelegramBotM1Application {

    public static void main(String[] args) {
        SpringApplication.run(TelegramBotM1Application.class, args);
    }

}
