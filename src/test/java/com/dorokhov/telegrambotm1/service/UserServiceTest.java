package com.dorokhov.telegrambotm1.service;

import com.dorokhov.telegrambotm1.model.User;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    private UserRepository userRepository;

    @Mock
    private User testUser = mock(User.class);
    @Mock
    private Message message;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        testUser = User.builder()
                .userName("testUser")
                .firstName("firstName")
                .lastName("lastName")
                .registeredAt(new Timestamp(System.currentTimeMillis() / 1000))
                .chatId(1L).build();

        message = new Message();
        message.setChat(new Chat(1L, "test"
        ));
    }

    @Test
    void registerUser() {
        userService.registerUser(message);
        when(userRepository.findByChatId(message.getChatId())).thenReturn(Optional.ofNullable(testUser));
    }

    @Test
    void getUserInfo() {
        when(userRepository.findByChatId(message.getChatId())).thenReturn(Optional.ofNullable(testUser));
    }

    @Test
    void deleteUserInfo() {
        userService.deleteUserInfo(message);
        when(userRepository.findByChatId(message.getChatId())).thenReturn(null);
    }
}