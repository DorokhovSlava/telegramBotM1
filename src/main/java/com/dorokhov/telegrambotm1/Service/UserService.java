package com.dorokhov.telegrambotm1.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface UserService {

    void registerUser(Message msg);

    String getUserInfo(Message msg);

    String getAllUserInfo();

    String deleteUserInfo(Message msg);

}
