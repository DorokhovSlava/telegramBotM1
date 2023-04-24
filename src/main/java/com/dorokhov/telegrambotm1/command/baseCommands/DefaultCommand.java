package com.dorokhov.telegrambotm1.command.baseCommands;

import com.dorokhov.telegrambotm1.command.Command;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.Message;

public class DefaultCommand implements Command {

    static final String emojiAnswer = EmojiParser.parseToUnicode("\uD83D\uDE4A");
    static final String DEFAULT_TEXT = "Прошу прощения, команда пока не поддерживается " + emojiAnswer + "\n\n Для получения списка команд выберите /help";
    @Override
    public String execute(Message msg) {
        return DEFAULT_TEXT;
    }

    @Override
    public String commandId() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }
}
