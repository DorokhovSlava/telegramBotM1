package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.model.User;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import com.dorokhov.telegrambotm1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.Optional;

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
        Long chatId = msg.getChat().getId();
        var chat = msg.getChat();

        if (userRepository.findByChatId(chatId).isEmpty()) {

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved {}", user);
        } else {
            log.error("user already exists by chatId: {}", msg.getChatId());
        }
    }

    /**
     * @param msg
     * @return Sting
     */
    @Override
    public String getUserInfo(Message msg) {
        Optional<User> findUser = userRepository.findByChatId(msg.getChatId());
        String answer;
        if (findUser.isPresent()) {
            log.info("info user by chatId: {}", msg.getChatId());
            return findUser.toString();
        } else {
            answer = "Данные не найдены";
            log.error("not found user info by chatId: {}", msg.getChatId());
            return answer;
        }
    }

    /**
     * @param msg
     * @return Sting
     */
    @Override
    public String deleteUserInfo(Message msg) {
        Optional<User> findUser = userRepository.findByChatId(msg.getChatId());
        String answer;
        if (findUser.isPresent()) {
            userRepository.deleteByChatId(msg.getChatId());
            answer = "Данные были удалены";
            log.info("info user by chatId: {}is deleted", msg.getChatId());
        } else {
            answer = "Данные не найдены";
            log.error("user info by chatId: {} not found", msg.getChatId());
        }
        return answer;
    }

    @Override
    public String getUsersInfo() {
        if (userRepository.findAll().isEmpty()) {
            String answer = userRepository.findAll().toString();
            return answer;
        }
        log.error("not found users info ");
        return null;
    }
}