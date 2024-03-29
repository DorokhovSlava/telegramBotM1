package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.model.User;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import com.dorokhov.telegrambotm1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param msg
     */
    @Override
    public void registerUser(Message msg) {

        if (userRepository.findByChatId(msg.getChatId()) == null) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()/1000));

            userRepository.save(user);
            log.info("user saved " + user);
        } else {
            log.error("user already exists by chatId: " + msg.getChatId());
        }
    }

    /**
     * @param msg
     * @return Sting
     */
    @Override
    public String getUserInfo(Message msg) {
        User findUser = userRepository.findByChatId(msg.getChatId());
        String answer;
        if (findUser != null) {
            log.info("info user by chatId: " + msg.getChatId());
            return findUser.toString();
        } else {
            answer = "Данные не найдены";
            log.error("not found user info by chatId: " + msg.getChatId());
            return answer;
        }
    }

    /**
     * @param msg
     * @return Sting
     */
    @Override
    public String deleteUserInfo(Message msg) {
        User findUser = userRepository.findByChatId(msg.getChatId());
        String answer;
        if (findUser != null) {
            userRepository.deleteByChatId(msg.getChatId());
            answer = "Данные были удалены";
            log.info("info user by chatId: " + msg.getChatId() + "is deleted");
        } else {
            answer = "Данные не найдены";
            log.error("user info by chatId: " + msg.getChatId() + " not found");
        }
        return answer;
    }
}