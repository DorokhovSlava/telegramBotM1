package com.dorokhov.telegrambotm1.command.messageCommands;

import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class DeleteMessagesCommand implements Command {

    @Autowired
    private MessageService messageService;

    /**
     * @param msg 
     * @return
     */
    @Override
    public String execute(Message msg) {
        return messageService.deleteAllByName(msg);
    }

    /**
     * @return 
     */
    @Override
    public String commandId() {
        return "/deletemymsg";
    }

    /**
     * @return 
     */
    @Override
    public String description() {
        return "Удалить все сообщения пользователя";
    }
}
