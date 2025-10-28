package com.dorokhov.telegrambotm1.config;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class QdrantConfig {

    @Value("${spring.ai.vectorstore.qdrant.host:localhost}")
    private String host;

    @Value("${spring.ai.vectorstore.qdrant.port:6334}")
    private int port;

    @Value("${spring.ai.vectorstore.qdrant.collection-name:dungeon-knowledge}")
    private String collectionName;

    @Bean
    public QdrantClient qdrantClient() {
        try {
            QdrantClient client = new QdrantClient(
                    QdrantGrpcClient.newBuilder(host, port, false).build()
            );

            // Проверяем и создаем коллекцию если нужно
            createCollectionIfNeeded(client);

            return client;
        } catch (Exception e) {
            log.error("Failed to create Qdrant client: {}", e.getMessage());
            throw new RuntimeException("Qdrant client initialization failed", e);
        }
    }

    private void createCollectionIfNeeded(QdrantClient client) {
        try {
            // Проверяем существует ли коллекция
            var collectionInfo = client.getCollectionInfoAsync(collectionName).get();
            if (collectionInfo.isInitialized()) {
                log.info("Collection {} already exists", collectionName);
            }
        } catch (Exception e) {
            log.info("Collection {} does not exist, creating it...", collectionName);

            try {
                // Создаем коллекцию с размерностью 768 (для nomic-embed-text)
                client.createCollectionAsync(collectionName,
                        Collections.VectorParams.newBuilder()
                                .setSize(768)
                                .setDistance(Collections.Distance.Cosine)
                                .build()
                ).get();

                log.info("Collection {} created successfully", collectionName);
            } catch (Exception createException) {
                log.error("Failed to create collection: {}", createException.getMessage());
                throw new RuntimeException("Collection creation failed", createException);
            }
        }
    }
}
