package com.dorokhov.telegrambotm1.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackageClasses = BotConfiguration.class)
@Data
public class BotConfiguration {

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;
}
