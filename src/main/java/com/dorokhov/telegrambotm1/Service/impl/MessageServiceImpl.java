package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.repository.MessageRepository;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import com.dorokhov.telegrambotm1.service.MessageService;
import com.dorokhov.telegrambotm1.model.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * @param msg
     */
    @Override
    public void saveMessage(Message msg) {
        var msgText = msg.getText();
        var userName = msg.getChat().getUserName();

        Messages messages = new Messages();
        messages.setTextMessage(msgText);
        messages.setUserName(userName);
        messages.setMessageDate(new Timestamp(System.currentTimeMillis()));

        messageRepository.save(messages);
        log.info("message saved by chatId: " + msg.getChatId());
    }

    /**
     * @param msg
     */
    @Override
    public void deleteAllByName(Message msg) {
        var userName = msg.getChat().getUserName();
        if (userRepository.findByUserName(userName) != null) {
            messageRepository.deleteAllByName(userName);
            log.info(" delete all messages by  " + userName);
        }
        log.error(" not found messages by " + userName);
    }

    /**
     * @param msg
     * @return List
     */
    @Override
    public List<Messages> getAllByUserName(Message msg) {
        var userName = msg.getChat().getUserName();
        if (userRepository.findByUserName(userName) != null) {
            log.info(" found all messages by  " + userName);
            return messageRepository.findAllByName(userName).stream().collect(Collectors.toList());
        }
        log.error(" not found messages by " + userName);
        return null;
    }

    /**
     * @param msg
     * @return List
     */
    @Override
    public List<Messages> getByText(Message msg) {
        var msgText = msg.getText();
        return messageRepository.findAllByText(msgText);
    }
}
