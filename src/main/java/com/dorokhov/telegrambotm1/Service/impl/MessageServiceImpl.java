package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.repository.MessageRepository;
import com.dorokhov.telegrambotm1.service.MessageService;
import com.dorokhov.telegrambotm1.model.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * @param msg
     */
    @Override
    public void saveMessage(Message msg) {
            var msgText = msg.getText();
            var userName = msg.getChat().getUserName();

            Messages message = new Messages();
            message.setTextMessage(msgText);
            message.setUserName(userName);
            message.setUser(message.getUser());
            message.setMessageDate(new Timestamp(System.currentTimeMillis()));

            messageRepository.save(message);
            log.info("message saved by chatId: " + msg.getChatId());
    }
}
