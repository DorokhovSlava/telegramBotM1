package com.dorokhov.telegrambotm1;

import com.dorokhov.telegrambotm1.config.BotConfiguration;
import com.dorokhov.telegrambotm1.service.BusInfoService;
import com.dorokhov.telegrambotm1.service.MessageService;
import com.dorokhov.telegrambotm1.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@ComponentScan
@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private final UserService userService;

    private final BusInfoService busInfoService;

    private final MessageService messageService;

    final BotConfiguration configuration;

    static final String HELP_TEXT = "Этот бот позволяет принимать и отправлять сообщения под средством команд в меню и через ввод, " +
            "а так же сохранять и удалять информацию о запросах и пользователях. \n\n"
            + "VERSION MARK1 \n\n"
            + "Выберите /start - чтобы начать работу и получить приветвтвенное сообщение\n\n"
            + "Выберите /help - чтобы получить эту справочную информацию ещё раз \n\n"
            + "Выберите /mydata - получить информацию о своём аккаунте и дату регистрации \n\n"
            + "Выберите /deletedata - удалить информацию о своём аккаунте и дату регистрации \n\n"
            + "Выберите /mymessages - получить информацию о своих сообщениях\n\n"
            + "Выберите /bus24_balmoshnaya - чтобы узнать расписание автобуса 24 «ул.Памирская – Пл.Дружбы(по ул.Крупской)» Остановка: «Балмошная»\n\n"
            + "Выберите /bus24_circus - чтобы узнать расписание автобуса 24 «ул.Памирская – Пл.Дружбы(по ул.Крупской)» Остановка: «Цирк» \n\n";

    @Autowired
    public TelegramBot(UserService userService, BusInfoService busInfoService, MessageService messageService, BotConfiguration configuration) {
        this.userService = userService;
        this.busInfoService = busInfoService;
        this.messageService = messageService;
        this.configuration = configuration;

        // Menu of commands
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "начало работы, приветствие"));
        listOfCommands.add(new BotCommand("/mydata", "информация о пользователе, история запросов"));
        listOfCommands.add(new BotCommand("/deletedata", "удаление истории и информации"));
        listOfCommands.add(new BotCommand("/mymessages", "все сохранённые сообщения"));
        listOfCommands.add(new BotCommand("/help", "информация о боте"));
        listOfCommands.add(new BotCommand("/bus24_balmoshnaya", "24 «Балмошная» «ул.Памирская – Пл.Дружбы»\n" +
                "Остановка: «Балмошная» "));
        listOfCommands.add(new BotCommand("/bus24_circus", "24 «Цирк» «ул.Памирская – Пл.Дружбы»\n " +
                "Остановка: «Цирк» "));

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
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            var msg = update.getMessage();

            messageService.saveMessage(update.getMessage());

            switch (messageText) {

                case "/start":
                    userService.registerUser(msg);
                    startCommandReceived(chatId, msg.getChat().getFirstName());
                    break;

                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;

                case "/mydata":
                    userService.getUserInfo(msg);
                    sendMessage(chatId, userService.getUserInfo(msg));
                    break;

                case "/deletedata":
                    userService.deleteUserInfo(msg);
                    sendMessage(chatId, userService.deleteUserInfo(msg));
                    break;

                case "/mymessages":
                    messageService.getAllByUserName(msg);
                    sendMessage(chatId, messageService.getAllByUserName(msg).toString());

                case "/bus24_balmoshnaya":
                    String urlBalm = "http://www.m.gortransperm.ru/time-table/24/108302";
                    sendMessage(chatId, String.join("\n", busInfoService.getBusInfo(msg, urlBalm)));
                    break;

                case "/bus24_circus":
                    String urlCircus = "http://www.m.gortransperm.ru/time-table/24/8100";
                    sendMessage(chatId, String.join("\n", busInfoService.getBusInfo(msg, urlCircus)));
                    break;

                default:
                    String emojiAnswer = EmojiParser.parseToUnicode("\uD83D\uDE4A");
                    sendMessage(chatId, "Прошу прощения, команда пока не поддерживается " + emojiAnswer
                            + "\n\n Для получения списка команд выберите /help");
            }
        }
    }

    /**
     * Method command start
     */
    private void startCommandReceived(long chatId, String userName) {
        String answer = "Привет " + userName + ", рад тебя видеть!";
        sendMessage(chatId, answer);
        log.info("Start Message send to user: " + userName);
    }

    /**
     * Method send message
     */
    public void sendMessage(long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textMessage);

        System.out.println(message);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
