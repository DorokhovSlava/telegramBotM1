package com.dorokhov.telegrambotm1.Service;

import com.dorokhov.telegrambotm1.config.BotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfiguration configuration;

    public TelegramBot(BotConfiguration configuration) {
        this.configuration = configuration;
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

    /**
     * @param update check message from user and return response
     */
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {

                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                default:
                    sendMessage(chatId, "Прошу прощения, команда не поддерживается :(");

            }
        }

    }

    /**
     * Method command start
     */
    private void startCommandReceived(long chatId, String userName) {

        String answer = "Привет, " + userName + ", рад тебя видеть!";

        sendMessage(chatId, answer);
        log.info("Message send to user: " + userName);

    }

    private void sendMessage(long chatId, String textMessage) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textMessage);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
