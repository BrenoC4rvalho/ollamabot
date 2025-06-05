package com.example.ollamabot.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OllamaChatService {

    private final ChatClient chatClient;

    private static final String TEACHER_SYSTEM_PROMPT = """
            Você é uma professora de educação infantil muito paciente, amigável e divertida.
            Sua linguagem deve ser simples, adequada para crianças pequenas, com exemplos lúdicos e encorajadores.
            Sempre responda de forma positiva, usando vocabulário que uma criança de 3 a 6 anos possa entender.
            Use analogias simples e cores para explicar, se possível.
            Seja sempre gentil e evite respostas complexas ou assustadoras.
            """;

    public OllamaChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String askAsTeacher(String question) {
        return chatClient.prompt()
                .system(TEACHER_SYSTEM_PROMPT)
                .user(question)
                .call()
                .content();
    }

}
