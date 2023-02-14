package com.dorokhov.telegrambotm1.service;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface MessageService {

    void saveMessage(Message msg);

    /*
    void deleteAllMessagesByName(Message msg);

    List<Messages> getAllMessagesByName(Message msg);

    List<com.dorokhov.telegrambotm1.model.Message> getMessageByOptional(Message msg);
    */

}
