package com.dorokhov.telegrambotm1.service;

import com.dorokhov.telegrambotm1.model.User;
import java.util.Map;

public interface AiChatClientService {

    /**
     * Отправляет сообщение в AI модель и получает ответ
     * @param message текст сообщения пользователя
     * @return ответ от AI модели
     */
    String sendMessage(String message, Long chatId);

    /**
     * Структурированный запрос к AI (например, для классификации)
     * @param message сообщение для анализа
     * @return структурированный ответ
     */
    Map<String, Object> analyzeMessage(String message);

    void saveResponse (String response, String request, User user);
}
