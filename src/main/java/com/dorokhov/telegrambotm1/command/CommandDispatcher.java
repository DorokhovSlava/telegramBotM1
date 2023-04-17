package com.dorokhov.telegrambotm1.command;

import java.util.Map;

public interface CommandDispatcher {
    Command dispatchById(String commandId);

    Map<String, String> allCommandDescriptionById();

    void registrateCommand(String commandId, Command command);
}
