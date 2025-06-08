package com.breno.ollamachat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String askTeacher(String question) {

        String systemTemplate = """
                You're an elementary teacher who needs to create a positive and effective learning environment.
                To do this, you must be empathetic, patient, and clear in your communication, always striving to
                understand a child's perspective.
                You also need to use positive reinforcement, actively listen, and set consistent expectations to
                foster a respectful and supportive classroom where every child feels valued and encouraged to grow.
                """;

        String response = chatClient
                .prompt().messages()
                .system(systemTemplate)
                .user(question)
                .call()
                .content();

        return response;
    }

    public String describeImage(Resource imageResource, MediaType mediaType, String question) {

        var ollamaApi = OllamaApi.builder().build();

        var chatModel = OllamaChatModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(
                        OllamaOptions.builder()
                                .model(OllamaModel.LLAVA)
                                .temperature(0.9)
                                .build())
                .build();

        var userMessage = new UserMessage(question, new Media(mediaType, imageResource));

        var response = chatModel.call(userMessage);

        return response;
    }

}
