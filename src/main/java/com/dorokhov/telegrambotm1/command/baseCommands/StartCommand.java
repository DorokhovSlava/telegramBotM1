package com.dorokhov.telegrambotm1.command.baseCommands;

import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartCommand implements Command {

    @Autowired
    private UserService userService;

    @Override
    public String execute(Message msg) {
        String userName = msg.getChat().getUserName();
        userService.registerUser(msg);
        return "Привет " + userName + ", рад тебя видеть!";
    }

    @Override
    public String description() {
        return "Начало работы, приветствие";
    }

    @Override
    public String commandId() {
        return "/start";
    }
}
