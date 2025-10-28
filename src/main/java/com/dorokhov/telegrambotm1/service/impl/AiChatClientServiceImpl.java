package com.dorokhov.telegrambotm1.service.impl;

import com.dorokhov.telegrambotm1.config.promt.BasePrompt;
import com.dorokhov.telegrambotm1.model.AIResponse;
import com.dorokhov.telegrambotm1.model.Messages;
import com.dorokhov.telegrambotm1.model.User;
import com.dorokhov.telegrambotm1.repository.AIResponseRepository;
import com.dorokhov.telegrambotm1.repository.MessageRepository;
import com.dorokhov.telegrambotm1.repository.UserRepository;
import com.dorokhov.telegrambotm1.service.AiChatClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiChatClientServiceImpl implements AiChatClientService {

    private final OllamaChatModel chatModel;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AIResponseRepository aiResponseRepository;
    private final BasePrompt basePrompt;
    //private final VectorStore vectorStore;

    @Override
    public String sendMessage(String message, Long chatId) {
        log.info("Sending message to AI: {}", message);

        Optional<User> user = userRepository.findByChatId(chatId);

        String systemPrompt = basePrompt.getBasePrompt();

        try {
            List<Messages> messageContext = messageRepository.findAllByChatId(chatId);

            String context = messageContext.stream()
                    .sorted(Comparator.comparing(Messages::getMessageDate).reversed())
                    .limit(10)
                    .sorted(Comparator.comparing(Messages::getMessageDate))
                    .map(msg -> {
                        String sender = msg.getUserName() != null ? "Пользователь" : "AI";
                        return sender + ": " + msg.getTextMessage();
                    })
                    .collect(Collectors.joining("\n"));

            String enhancedPrompt = String.format("""
                            SYSTEM PROMPT:
                            %s
                            
                            CONVERSATION CONTEXT:
                            %s
                            
                            CURRENT USER MESSAGE: %s
                            """,
                    systemPrompt,
                    context.isEmpty() ? "This is the beginning of the dialogue." : context,
                    message
            );

            SystemMessage systemMessage = new SystemMessage(enhancedPrompt);
            UserMessage userMessage = new UserMessage(message);
            Prompt prompt = new Prompt(Arrays.asList(systemMessage, userMessage));

            var response = chatModel.call(prompt)
                    .getResult()
                    .getOutput().getContent();

            saveResponse(response, message, user.orElse(null));
            log.info("Received AI response: {}", response);
            return response;

        } catch (Exception e) {
            log.error("Error communicating with AI: {}", e.getMessage());
            return "⚠️ Произошла ошибка при обработке запроса. Попробуйте еще раз.";
        }
    }


    @Override
    public Map<String, Object> analyzeMessage(String message) {
        log.info("Analyzing message: {}", message);

        String analysisPrompt = """
                Проанализируй сообщение и верни ответ в формате JSON:
                - sentiment: тональность (POSITIVE, NEGATIVE, NEUTRAL)
                - category: категория (QUESTION, COMPLAINT, FEEDBACK, OTHER)
                - urgency: срочность (HIGH, MEDIUM, LOW)
                - keyTopics: массив ключевых тем
                
                Сообщение: %s
                """.formatted(message);

        var promt = new Prompt("Ты полезный AI ассистент в Telegram боте. Отвечай кратко и по делу.");

        try {
            var response = chatModel.call(promt)
                    .getResult()
                    .getOutput().getContent();

            // Парсим ответ (в реальном приложении лучше использовать ObjectMapper)
            Map<String, Object> result = new HashMap<>();
            result.put("sentiment", extractValue(response, "sentiment"));
            result.put("category", extractValue(response, "category"));
            result.put("urgency", extractValue(response, "urgency"));
            result.put("rawResponse", response);

            return result;

        } catch (Exception e) {
            log.error("Error analyzing message: {}", e.getMessage());
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("error", "Analysis failed");
            return errorResult;
        }
    }

    @Override
    public void saveResponse(String response, String request, @NonNull User user) {

        AIResponse aIResponse = new AIResponse();
        aIResponse.setTextResponse(response);
        aIResponse.setTextRequest(request);
        aIResponse.setUserName(user.getUserName());
        aIResponse.setResponseDate(new Timestamp(System.currentTimeMillis()));
        aIResponse.setUser(user);

        aiResponseRepository.save(aIResponse);
        log.info("Saving response: {}", response);
    }

    private String extractValue(String response, String key) {
        // Простая реализация парсинга - в реальном приложении используйте JSON парсер
        try {
            String searchKey = "\"" + key + "\":";
            int startIndex = response.indexOf(searchKey);
            if (startIndex != -1) {
                startIndex += searchKey.length();
                int endIndex = response.indexOf(",", startIndex);
                if (endIndex == -1) endIndex = response.indexOf("}", startIndex);
                if (endIndex != -1) {
                    return response.substring(startIndex, endIndex)
                            .replace("\"", "")
                            .trim();
                }
            }
        } catch (Exception e) {
            log.warn("Failed to extract key {} from response", key);
        }
        return "UNKNOWN";
    }
}
