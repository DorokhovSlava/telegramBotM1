package com.dorokhov.telegrambotm1;

import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.command.CommandDispatcher;
import com.dorokhov.telegrambotm1.config.BotConfiguration;
import com.dorokhov.telegrambotm1.service.BusInfoService;
import com.dorokhov.telegrambotm1.service.MessageService;
import com.dorokhov.telegrambotm1.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

import javax.annotation.PostConstruct;
import java.util.*;

@ComponentScan
@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandDispatcher commandDispatcher;
    private final BusInfoService busInfoService;
    private final MessageService messageService;
    final BotConfiguration configuration;

    static final String HELP_TEXT = """
            Этот бот позволяет принимать и отправлять сообщения под средством команд в меню и через ввод, а так же сохранять и удалять информацию о запросах и пользователях.\s

            VERSION MARK1\s

            Выберите /start - чтобы начать работу и получить приветвтвенное сообщение

            Выберите /help - чтобы получить эту справочную информацию ещё раз\s

            Выберите /mydata - получить информацию о своём аккаунте и дату регистрации\s

            Выберите /deletemydata - удалить информацию о своём аккаунте и дату регистрации\s

            Выберите /mymsg - получить информацию о своих сообщениях
            
            Выберите /deletemymsg - получить информацию о своих сообщениях

            Выберите /bus24_balmoshnaya - чтобы узнать расписание автобуса 24 «ул.Памирская – Пл.Дружбы(по ул.Крупской)» Остановка: «Балмошная»

            Выберите /bus24_circus - чтобы узнать расписание автобуса 24 «ул.Памирская – Пл.Дружбы(по ул.Крупской)» Остановка: «Цирк»\s

            """;

    @Autowired
    public TelegramBot(CommandDispatcher commandDispatcher, BusInfoService busInfoService, MessageService messageService, BotConfiguration configuration) {
        this.commandDispatcher = commandDispatcher;
        this.busInfoService = busInfoService;
        this.messageService = messageService;
        this.configuration = configuration;
        // Menu of commands
        List<BotCommand> listOfCommands = new ArrayList<>();
        Map<String, String> descriptionByKey = commandDispatcher.allCommandDescriptionById();
        Set<String> keys = descriptionByKey.keySet();
        for (String key : keys) {
            listOfCommands.add(new BotCommand(key, descriptionByKey.get(key)));
        }

//        listOfCommands.add(new BotCommand("/start", "начало работы, приветствие"));
//        listOfCommands.add(new BotCommand("/mydata", "информация о пользователе, история запросов"));
//        listOfCommands.add(new BotCommand("/deletemydata", "удаление истории и информации"));
//        listOfCommands.add(new BotCommand("/mymsg", "все сохранённые сообщения пользователя"));
//        listOfCommands.add(new BotCommand("/deletemymsg", "удалить все сообщения пользователя"));
//        listOfCommands.add(new BotCommand("/help", "информация о боте"));
//        listOfCommands.add(new BotCommand("/bus24_balmoshnaya", "24 «Балмошная» «ул.Памирская – Пл.Дружбы»\n" +
//                "Остановка: «Балмошная» "));
//        listOfCommands.add(new BotCommand("/bus24_circus", "24 «Цирк» «ул.Памирская – Пл.Дружбы»\n " +
//                "Остановка: «Цирк» "));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error command list: " + e.getMessage());
        }
    }

    @PostConstruct
    void postContruct() {

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
            Message message = update.getMessage();

            String messageText = message.getText();
            long chatId = message.getChatId();

            Command command = commandDispatcher.dispatchById(messageText);
            String answer = command.execute(message);

            sendMessage(chatId, answer);
            messageService.saveMessage(message);
        }
    }


//    /**
//     * @param update check message from user and return response
//     */
//    @SneakyThrows
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
//            long chatId = update.getMessage().getChatId();
//            var msg = update.getMessage();
//            messageService.saveMessage(update.getMessage());
//            switch (messageText) {
//                case "/start" -> {
//                    userService.registerUser(msg);
//                    startCommandReceived(chatId, msg.getChat().getFirstName());
//                }
//                case "/help" -> sendMessage(chatId, HELP_TEXT);
//                case "/mydata" -> {
//                    userService.getUserInfo(msg);
//                    sendMessage(chatId, userService.getUserInfo(msg));
//                }
//                case "/deletemydata" -> {
//                    userService.deleteUserInfo(msg);
//                    messageService.deleteAllByName(msg);
//                    sendMessage(chatId, userService.deleteUserInfo(msg));
//                }
//                case "/mymsg" -> {
//                    messageService.getAllByUserName(msg);
//                    sendMessage(chatId, messageService.getAllByUserName(msg).toString());
//                }
//                case "/deletemymsg" -> {
//                    messageService.deleteAllByName(msg);
//                    sendMessage(chatId, messageService.getAllByUserName(msg).toString());
//                }
//                case "/bus24_balmoshnaya" -> {
//                    String urlBalm = "http://www.m.gortransperm.ru/time-table/24/108302";
//                    sendMessage(chatId, String.join("\n", busInfoService.getBusInfo(msg, urlBalm)));
//                }
//                case "/bus24_circus" -> {
//                    String urlCircus = "http://www.m.gortransperm.ru/time-table/24/8100";
//                    sendMessage(chatId, String.join("\n", busInfoService.getBusInfo(msg, urlCircus)));
//                }
//                default -> sendMessage(chatId, DEFAULT_TEXT);
//            }
//        }
//    }

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