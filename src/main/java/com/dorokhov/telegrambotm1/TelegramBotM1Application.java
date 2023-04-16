package com.dorokhov.telegrambotm1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBotM1Application {
    public static void main(String[] args) {
        try {
            SpringApplication.run(TelegramBotM1Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
