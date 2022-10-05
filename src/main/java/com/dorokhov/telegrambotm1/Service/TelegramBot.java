package com.dorokhov.telegrambotm1.Service;

import com.dorokhov.telegrambotm1.config.BotConfiguration;
import com.dorokhov.telegrambotm1.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import com.dorokhov.telegrambotm1.model.User;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ComponentScan
@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRepository userRepository;

    final BotConfiguration configuration;

    static final String HELP_TEXT = "Этот бот позволяет принимать и отправлять сообщения под средством команд в меню и через ввод, " +
            "а так же сохранять и удалять информацию о запросах и пользователях. \n \n"
            + "VERSION MARK2 \n\n"
            + "Выберите /start - чтобы начать работу и получить приветвтвенное сообщение\n\n"
            + "Выберите /help - чтобы получить эту справочную информацию ещё раз \n\n";

    public TelegramBot(BotConfiguration configuration) {
        this.configuration = configuration;

        // Menu of commands
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "начало работы, приветствие"));
        listOfCommands.add(new BotCommand("/mydata", "информация о пользователе, история запросов"));
        listOfCommands.add(new BotCommand("/deletedata", "удаление истории и информации"));
        listOfCommands.add(new BotCommand("/help", "информация о боте"));
        listOfCommands.add(new BotCommand("/settings", "настройки"));

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
                    registerUser(update.getMessage());
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;

                case "/mydata":
                    getUserInfo(update.getMessage());
                    break;

                case "/deletedata":
                    deleteUserInfo(update.getMessage());
                    break;

                default:
                    sendMessage(chatId, "Прошу прощения, команда пока не поддерживается");
            }
        }

    }

    /**
     * Method command register user before start
     */
    private void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved" + user);
        }
    }

    /**
     * Method command myData. Get user info by user chatId
     */
    private void getUserInfo(Message msg) {
        if (userRepository.findById(msg.getChatId()).isPresent()) {
            String answer = userRepository.findById(msg.getChatId()).get().toString();
            sendMessage(msg.getChatId(), answer);
        }
    }

    /**
     * Method command myData. Delete user info by user chatId
     */
    private void deleteUserInfo(Message msg) {
        if (userRepository.findById(msg.getChatId()).isPresent()) {
            userRepository.deleteById(msg.getChatId());
            String answer = "Данные быди удалены";
            sendMessage(msg.getChatId(), answer);
            log.info("user chatId: " + msg.getChatId() + "is deleted");
        }else {
            String answer = "Данные не найдены";
            sendMessage(msg.getChatId(), answer);
            log.info("user info not found");
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
