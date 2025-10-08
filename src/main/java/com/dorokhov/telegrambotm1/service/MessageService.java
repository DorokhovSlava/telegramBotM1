package com.dorokhov.telegrambotm1.service;

import com.dorokhov.telegrambotm1.model.Messages;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface MessageService {

    void saveMessage(Message msg);

    String processMessageWithAI(Message msg, Long chatId);

    String deleteAllByName(Message msg);

    List<Messages> getAllByUserName(Message msg);

    List<Messages> getByText(String text, Message msg);

}
