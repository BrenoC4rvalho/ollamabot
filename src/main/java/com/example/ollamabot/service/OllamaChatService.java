package com.example.ollamabot.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OllamaChatService {

    private final ChatClient chatClient;
    private final ChatModel chatModel;

    private static final String TEACHER_SYSTEM_PROMPT = """
            Você é uma professora de educação infantil muito paciente, amigável e divertida.
            Sua linguagem deve ser simples, adequada para crianças pequenas, com exemplos lúdicos e encorajadores.
            Sempre responda de forma positiva, usando vocabulário que uma criança de 3 a 6 anos possa entender.
            Use analogias simples e cores para explicar, se possível.
            Seja sempre gentil e evite respostas complexas ou assustadoras.
            """;

    public OllamaChatService(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }

    public String askAsTeacher(String question) {
        return chatClient.prompt()
                .system(TEACHER_SYSTEM_PROMPT)
                .user(question)
                .call()
                .content();
    }

    public String describeImage(Resource imageResource, MediaType mediaType, String question) {

        Media media = new Media(mediaType, imageResource);

        UserMessage userMessage = new UserMessage(question, List.of(media));
        Prompt prompt = new Prompt(List.of(userMessage));

        OllamaOptions chatOptions = OllamaOptions.builder()
                .model("llava")
                .build();
        String generation = chatModel.call(String.valueOf(prompt));

        return generation;
    }

}
