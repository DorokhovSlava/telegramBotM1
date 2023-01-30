package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.model.User;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import com.dorokhov.telegrambotm1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.stream.Stream;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param msg
     */
    @Override
    public void registerUser(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);
            log.info("user saved" + user);
        }
        log.error("user not saved by chatId: " + msg.getChatId());
    }

    /**
     * @param msg
     * @return Sting
     */
    @Override
    public String getUserInfo(Message msg) {
        if (userRepository.findById(msg.getChatId()).isEmpty()) {
            Stream<User> stringStream = Stream.of(userRepository.getUserById(msg.getChatId()));
            //String answer = userRepository.findById(msg.getChatId()).get().toString();
            return stringStream.toString();
        }
        String answer = "Данные не найдены";
    log.error("not found user info by chatId: " + msg.getChatId());
        return answer;
    }

    /**
     * @return
     */
    @Override
    public String getAllUserInfo() {
        String userStream = Stream.of(userRepository.findAllUser()).toString();
        if (userStream.isEmpty()){
            log.error("not found user info");
            return null;
        }
        log.info("request get all info");
        return userStream;
    }

    /**
     * @param msg
     * @return Sting
     */
    @Override
    public String deleteUserInfo(Message msg) {
        if (userRepository.findById(msg.getChatId()).isPresent()) {
            userRepository.deleteById(msg.getChatId());
            String answer = "Данные были удалены";
            log.info("user chatId: " + msg.getChatId() + "is deleted");
            return answer;
        } else {
            String answer = "Данные не найдены";
            log.info("user info not found");
            return answer;
        }
    }
}
