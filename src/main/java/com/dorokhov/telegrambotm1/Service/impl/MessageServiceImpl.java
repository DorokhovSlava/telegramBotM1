package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.repository.MessageRepository;
import com.dorokhov.telegrambotm1.service.MessageService;
import com.dorokhov.telegrambotm1.model.Messages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    final MessageRepository messageRepository;

    /**
     * @param msg
     */
    @Override
    public void saveMessage(Message msg) {

        var msgText= msg.getText();
        var user = msg.getChat().getUserName();

        Messages message = new Messages();
        message.setTextMessage(msgText);
        message.setUserName(user);
        message.setMessageDate(new Timestamp(System.currentTimeMillis()));

        messageRepository.save(message);
        log.info("message saved by chatId: " + msg.getChatId());

    }

    /**
     * @param msg
     */
    @Override
    public void deleteAllMessagesByName(Message msg) {

    }

}
