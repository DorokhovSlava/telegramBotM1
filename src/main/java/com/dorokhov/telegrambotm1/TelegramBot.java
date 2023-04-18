package com.dorokhov.telegrambotm1;

import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.command.CommandDispatcher;
import com.dorokhov.telegrambotm1.config.BotConfiguration;
import com.dorokhov.telegrambotm1.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ComponentScan
@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandDispatcher commandDispatcher;
    private final MessageService messageService;
    final BotConfiguration configuration;

    @Autowired
    public TelegramBot(CommandDispatcher commandDispatcher, MessageService messageService, BotConfiguration configuration) {
        this.commandDispatcher = commandDispatcher;
        this.messageService = messageService;
        this.configuration = configuration;
        // Menu of commands
        List<BotCommand> listOfCommands = new ArrayList<>();
        Map<String, String> descriptionByKey = commandDispatcher.allCommandDescriptionById();
        Set<String> keys = descriptionByKey.keySet();
        for (String key : keys) {
            listOfCommands.add(new BotCommand(key, descriptionByKey.get(key)));
        }
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error command list: " + e.getMessage());
        }
    }

    /**
     * @return register name
     */
    @Override
    public String getBotUsername() {
        return configuration.getBotName();
    }

    /**
     * @return register token
     */
    @Override
    public String getBotToken() {
        return configuration.getBotToken();
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message msg = update.getMessage();

            String messageText = msg.getText();
            long chatId = msg.getChatId();

            Command command = commandDispatcher.dispatchById(messageText);
            String answer = command.execute(msg);

            sendMessage(chatId, answer);
            messageService.saveMessage(msg);
        }
    }

    /**
     * Method send message
     */
    public void sendMessage(long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textMessage);
        log.info(message.toString());
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}