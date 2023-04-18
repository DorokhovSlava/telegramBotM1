package com.dorokhov.telegrambotm1.command;

import com.dorokhov.telegrambotm1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class GetDataCommand implements Command {

    @Autowired
    private UserService userService;

    /**
     * @param msg
     * @return
     */
    @Override
    public String execute(Message msg) {
        return userService.getUserInfo(msg);
    }

    /**
     * @return 
     */
    @Override
    public String commandId() {
        return "/mydata";
    }

    /**
     * @return 
     */
    @Override
    public String description() {
        return "Получить информацию о пользователе бота ";
    }
}
