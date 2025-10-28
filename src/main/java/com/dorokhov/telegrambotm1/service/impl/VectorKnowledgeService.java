package com.dorokhov.telegrambotm1.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class VectorKnowledgeService {

    private final VectorStore vectorStore;
    @Qualifier("ollamaEmbeddingModel")
    private final EmbeddingModel embeddingModel;
    private final ObjectMapper objectMapper;

    public void addKnowledge(String title, String content, KnowledgeType type,
                             Map<String, Object> metadata, Long chatId) {
        try {
            // Создаем метаданные с правильными типами
            Map<String, Object> documentMetadata = new HashMap<>();
            documentMetadata.put("title", title);
            documentMetadata.put("type", type.name());
            documentMetadata.put("chatId", chatId != null ? chatId.toString() : "global");
            documentMetadata.put("timestamp", Instant.now().toString());

            if (metadata != null) {
                try {
                    documentMetadata.put("metadata", objectMapper.writeValueAsString(metadata));
                } catch (Exception e) {
                    log.warn("Error serializing metadata: {}", e.getMessage());
                    documentMetadata.put("metadata", "{}");
                }
            } else {
                documentMetadata.put("metadata", "{}");
            }

            Document document = new Document(content, documentMetadata);
            vectorStore.add(List.of(document));
            log.info("Knowledge added to vector store: {} - {}", type, title);

        } catch (Exception e) {
            log.error("Error adding knowledge to vector store: {}", e.getMessage(), e);
        }
    }



    public List<KnowledgeItem> searchRelevantKnowledge(String query, int limit, KnowledgeType... types) {
        return searchRelevantKnowledge(query, limit);
    }

    public List<KnowledgeItem> searchRelevantKnowledge(String query, int limit) {
        try {
            List<Document> results = vectorStore.similaritySearch(query);

            return results.stream()
                    .map(this::mapToKnowledgeItem)
                    .sorted((a, b) -> Double.compare(b.getRelevanceScore(), a.getRelevanceScore()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error searching vector store: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private KnowledgeItem mapToKnowledgeItem(Document document) {
        Map<String, Object> originalMetadata = document.getMetadata();

        // Конвертируем Map<String, Object> в Map<String, String>
        Map<String, String> stringMetadata = new HashMap<>();
        for (Map.Entry<String, Object> entry : originalMetadata.entrySet()) {
            stringMetadata.put(entry.getKey(),
                    entry.getValue() != null ? entry.getValue().toString() : "");
        }

        // В Spring AI score не возвращается напрямую, используем заглушку
        // В реальном приложении можно рассчитать косинусное расстояние
        double relevanceScore = 0.8; // Заглушка

        return new KnowledgeItem(
                stringMetadata.get("title") + ": " + document.getContent(),
                stringMetadata,
                relevanceScore
        );
    }


    public enum KnowledgeType {

    }

    @Data
    @AllArgsConstructor
    public static class KnowledgeItem {
        private String content;
        private Map<String, String> metadata;
        private double relevanceScore;
    }
}
