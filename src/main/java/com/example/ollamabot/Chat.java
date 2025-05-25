package com.example.ollamabot;

import com.example.ollamabot.service.OllamaChatService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Chat implements CommandLineRunner {

    @Autowired
    private OllamaChatService chatService;

    @Override
    public void run(String... args) throws Exception {
            chatService.chat("ola, me chamo Breno, quero sempre que voce comecar uma mensagem, comece com meu nome.");
            chatService.chat("Qual o maior pais do mundo");
            chatService.chat("Esse pais ja ganhou copa do mundo de futebol masculino?");
            chatService.resetHistory();
            chatService.chat("Qual pais mais ganhou copa do mundo");
    }
}
