package com.dorokhov.telegrambotm1.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpCommand implements Command {

    static final String HELP_TEXT = """
            Этот бот позволяет принимать и отправлять сообщения под средством команд в меню и через ввод, а так же сохранять и удалять информацию о запросах и пользователях.\s

            VERSION MARK1\s

            Выберите /start - чтобы начать работу и получить приветвтвенное сообщение

            Выберите /help - чтобы получить эту справочную информацию ещё раз\s

            Выберите /mydata - получить информацию о своём аккаунте и дату регистрации\s

            Выберите /deletedata - удалить информацию о своём аккаунте и дату регистрации\s

            Выберите /mymsg - получить информацию о своих сообщениях
            
            Выберите /deletemymsg - получить информацию о своих сообщениях

            Выберите /bus24_balmoshnaya - чтобы узнать расписание автобуса 24 «ул.Памирская – Пл.Дружбы(по ул.Крупской)» Остановка: «Балмошная»

            Выберите /bus24_circus - чтобы узнать расписание автобуса 24 «ул.Памирская – Пл.Дружбы(по ул.Крупской)» Остановка: «Цирк»\s

            """;

    /**
     * @param message 
     * @return HELP_TEXT
     */
    @Override
    public String execute(Message message) { return HELP_TEXT; }

    /**
     * @return 
     */
    @Override
    public String commandId() { return "/help"; }

    /**
     * @return 
     */
    @Override
    public String description() {
        return "Получить справочную информацию по командам ";
    }
}
