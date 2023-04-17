package com.dorokhov.telegrambotm1.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {

    String execute(Message message);

    String commandId();

    String description();

    @Autowired
    default void registrate(CommandDispatcher commandDispatcher) {
        String commandId = commandId();
        if(StringUtils.hasText(commandId)) {
            commandDispatcher.registrationCommand(commandId, this);
        }
    }

}
