package com.dorokhov.telegrambotm1.command.messageCommands;

import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.service.MessageService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class GetMessagesCommand implements Command {

    private final MessageService messageService;

    public GetMessagesCommand(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * @param msg 
     * @return
     */
    @Override
    public String execute(Message msg) {
        return messageService.getAllByUserName(msg).toString();
    }

    /**
     * @return 
     */
    @Override
    public String commandId() {
        return "/mymessages";
    }

    /**
     * @return 
     */
    @Override
    public String description() {
        return "Получить все сообщения пользователя";
    }
}
