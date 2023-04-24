package com.dorokhov.telegrambotm1.command.dataCommands;

import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DeleteDataCommand implements Command {

    @Autowired
    private UserService userService;

    /**
     * @param msg 
     * @return
     */
    @Override
    public String execute(Message msg) {
        return userService.deleteUserInfo(msg);
    }

    /**
     * @return 
     */
    @Override
    public String commandId() {
        return "/deletemydata";
    }

    /**
     * @return 
     */
    @Override
    public String description() {
        return "Удаление информации о пользователе";
    }
}
