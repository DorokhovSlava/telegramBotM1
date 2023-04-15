package com.dorokhov.telegrambotm1.service;

import com.dorokhov.telegrambotm1.model.Messages;
import com.dorokhov.telegrambotm1.model.User;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import com.dorokhov.telegrambotm1.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User userEntity;

    private Messages messages;

    private Message message;

    @BeforeEach
    public void setup(){
        userEntity = User.builder()
                .id(1L)
                .userName("testUser")
                .firstName("firstName")
                .lastName("lastName")
                .registeredAt(new Timestamp(System.currentTimeMillis()))
                .chatId(11111111L).build();

        messages = Messages.builder()
                .userName("testUser")
                .id(11111111L)
                .textMessage("testMsg")
                .messageDate(new Timestamp(System.currentTimeMillis()))
                .build();

        message = new Message();
        message.setMessageId(1);
        message.setChat(new Chat(1L, "test"));

    }

    @Test
    void registerUser() {

        //Mockito.when(userService.registerUser()).thenReturn()

    }

    @Test
    void getUserInfo() {
    }

    @Test
    void deleteUserInfo() {
    }
}