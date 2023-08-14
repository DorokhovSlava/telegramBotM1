package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.model.User;
import com.dorokhov.telegrambotm1.repository.MessageRepository;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import com.dorokhov.telegrambotm1.service.MessageService;
import com.dorokhov.telegrambotm1.model.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        var msgUser = userRepository.findByChatId(msg.getChatId());

        Messages messages = new Messages();
        messages.setTextMessage(msgText);
        messages.setUserName(userName);
        messages.setMessageDate(new Timestamp(System.currentTimeMillis()));
        messages.setUser(msgUser);

        messageRepository.save(messages);
        log.info("message saved by chatId: " + msg.getChatId());
    }

    /**
     * @param msg
     */
    @Override
    public String deleteAllByName(Message msg) {
        var userName = msg.getChat().getUserName();
        if (userRepository.findByUserName(userName).isPresent()) {
            messageRepository.deleteAllByName(userName);
            log.info(" delete all messages by  " + userName);
            return "Сообщения удалены";
        }
        log.error(" not found messages by " + userName);
        return "Сообщения не найдены";
    }

    /**
     * @param msg
     * @return List
     */
    @Override
    public List<Messages> getAllByUserName(Message msg) {
        var userName = msg.getChat().getUserName();
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            log.info("Found all messages by " + userName);
            return new ArrayList<>(messageRepository.findAllByName(userName));
        } else {
            log.error("Not found messages by " + userName);
            return Collections.emptyList();
        }
    }

    /**
     * @param msg
     * @return List
     */
    @Override
    public List<Messages> getByText(String text, Message msg) {
        var msgText = msg.getText();
        Stream<Messages> messagesStream = messageRepository.findAllByText(msgText).stream().filter(o -> o.getTextMessage().equals(text));
        return messagesStream.collect(Collectors.toList());
    }
}
