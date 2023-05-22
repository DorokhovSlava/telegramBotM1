package com.dorokhov.telegrambotm1.command.messageCommands;

import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class GetMessagesByText implements Command {

    @Autowired
    private MessageService messageService;

    /**
     * @param msg
     * @return
     */
    @Override
    public String execute(Message msg) {
        return messageService.getByText(msg.getText(), msg).toString();
    }

    /**
     * @return
     */
    @Override
    public String commandId() {
        return "/msgbytext";
    }

    /**
     * @return
     */
    @Override
    public String description() {
        return "Получить сообщения пользователя по совпадению текста ";
    }
}
