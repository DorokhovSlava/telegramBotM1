package com.dorokhov.telegrambotm1.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public interface Command {

    String execute(Message msg);

    String commandId();

    String description();

    @Autowired
    default void registration(CommandDispatcher commandDispatcher) {
        String commandId = commandId();
        if(StringUtils.hasText(commandId)) {
            commandDispatcher.registrationCommand(commandId, this);
        }
    }

}
