package com.dorokhov.telegrambotm1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration;

@SpringBootApplication(exclude = {
        ContextFunctionCatalogAutoConfiguration.class
})
public class TelegramBotM1Application {
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotM1Application.class, args);
    }
}
