package com.example.ollamabot.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.ollama.management.PullModelStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class AIConfig {

    @Bean
    public ChatModel chatModel(OllamaChatModel ollamaChatModel) {
        return ollamaChatModel;
    }

    @Bean
    public ChatClient chatClient() {
        OllamaApi ollamaApi = new OllamaApi("http://localhost:11434");
        OllamaOptions options = new OllamaOptions();
        ToolCallingManager toolCallingManager = ToolCallingManager.builder().build();
        ObservationRegistry observationRegistry = ObservationRegistry.NOOP;
        ModelManagementOptions modelManagementOptions = new ModelManagementOptions(
                PullModelStrategy.WHEN_MISSING,
                Collections.emptyList(),
                Duration.ofSeconds(30),
                3
        );

        OllamaChatModel chatModel = new OllamaChatModel(
                ollamaApi,
                options,
                toolCallingManager,
                observationRegistry,
                modelManagementOptions
        );

        return ChatClient.create(chatModel);
    }

}
