package com.dorokhov.telegrambotm1.command.commandImpl;


import com.dorokhov.telegrambotm1.command.Command;
import com.dorokhov.telegrambotm1.command.CommandDispatcher;
import com.dorokhov.telegrambotm1.command.baseCommands.DefaultCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CommandDispatcherImpl implements CommandDispatcher {

    private final Map<String, Command> commandById = new HashMap<>();

    @Override
    public Command dispatchById(String commandId) {
        return commandById.getOrDefault(commandId, new DefaultCommand());
    }

    @Override
    public Map<String, String> allCommandDescriptionById() {
        return commandById.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().description()));
    }

    @Override
    public void registrationCommand(String commandId, Command command) {
        commandById.put(commandId, command);
    }
}
