package com.dorokhov.telegrambotm1;

import com.dorokhov.telegrambotm1.config.BotConfiguration;
import com.dorokhov.telegrambotm1.service.MessageService;
import com.dorokhov.telegrambotm1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfiguration configuration;
    private final UserService userService;
    private final MessageService messageService;

    static final String HELP_TEXT = "Этот бот позволяет принимать и отправлять сообщения под средством команд в меню и через ввод, " +
            "а так же сохранять и удалять информацию о запросах и пользователях. \n \n"
            + "VERSION MARK1 \n\n"
            + "Выберите /start - чтобы начать работу и получить приветвтвенное сообщение\n\n"
            + "Выберите /help - чтобы получить эту справочную информацию ещё раз \n\n"
            + "Выберите /mydata - получить информацию о своём аккаунте и дату регистрации \n\n"
            + "Выберите /deletecontext - удаление истории и контекста \n\n"
            + "Просто введите сообщение - чтобы отправить сообщение нейросети и получить ответ \n\n";

    @Autowired
    public TelegramBot(BotConfiguration configuration,
                       UserService userService,
                       MessageService messageService) {
        this.configuration = configuration;
        this.userService = userService;
        this.messageService = messageService;

        initializeMenuCommands();
    }

    private void initializeMenuCommands() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "начало работы, приветствие"));
        listOfCommands.add(new BotCommand("/mydata", "информация о пользователе, история запросов"));
        listOfCommands.add(new BotCommand("/deletecontext", "удаление истории и контекста"));
        listOfCommands.add(new BotCommand("/help", "информация о боте"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error command list: {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return configuration.getBotName();
    }

    @Override
    public String getBotToken() {
        return configuration.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChat().getId();

            switch (messageText) {
                case "/start":
                    userService.registerUser(update.getMessage());
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;

                case "/mydata":
                    String userInfo = userService.getUserInfo(update.getMessage());
                    sendMessage(chatId, userInfo);
                    break;

                case "/deletecontext":
                    String deleteResult = userService.deleteUserInfo(update.getMessage());
                    sendMessage(chatId, deleteResult);
                    break;

                default:
                    try {
                        String aiResponse = messageService.processMessageWithAI(update.getMessage(), chatId);
                        sendMessage(chatId, aiResponse);
                    } catch (Exception e) {
                        log.error("AI processing error: {}", e.getMessage());
                        sendMessage(chatId, "Извините, произошла ошибка при обработке запроса. Попробуйте позже.");
                    }
                    break;
            }
        }
    }

    private void startCommandReceived(long chatId, String userName) {
        String answer = "Привет, " + userName + ", рад тебя видеть!";
        sendMessage(chatId, answer);
        log.info("Message send to user: {}", userName);
    }

    private void sendMessage(long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textMessage);

        try {
            execute(message);
            log.info("Message sent to chatId: {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }
}
