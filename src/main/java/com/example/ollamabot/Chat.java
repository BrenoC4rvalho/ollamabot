package com.example.ollamabot;

import com.example.ollamabot.service.OllamaChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Chat implements CommandLineRunner {

    @Autowired
    private OllamaChatService chatService;

    @Override
    public void run(String... args) throws Exception {

//        System.out.println(chatService.chat("ola, me chamo Breno, quero sempre que voce comecar uma mensagem, comece com meu nome."));
//        System.out.println(chatService.chat("Qual o maior pais do mundo"));
//        System.out.println(chatService.chat("Esse pais ja ganhou copa do mundo de futebol masculino?"));
//        chatService.resetHistory();
//        System.out.println(chatService.chat("Qual pais mais ganhou copa do mundo"));
    }
}
