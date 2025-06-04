package com.example.ollamabot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class OllamaChatService {


    @Autowired
    private ChatModel chatModel;

    @Autowired
    private ChatClient chatClient;

    private final List<Message> conversationHistory = new ArrayList<>();

    public String chat(String message) {
        System.out.println("Fez a pergunta: " + message);
        conversationHistory.add(new UserMessage(message));

        Prompt prompt = new Prompt(conversationHistory);

        String response = chatClient.prompt(prompt)
                .system("Seu nome e Breno")
                .call()
                .content();

        assert response != null;
        conversationHistory.add(new AssistantMessage(response));

        return response;
    }

//    public String chatWithImage(String message, MultipartFile imageFile) {

//        Resource imageResource = imageFile.getResource();
//
//        UserMessage userMessage = UserMessage.builder()
//                .text(message)
//                .media(new Media(MimeTypeUtils.IMAGE_PNG, imageResource))
//                .build();
//
//        conversationHistory.add(userMessage);
//
//        Prompt prompt = new Prompt(conversationHistory);
//        String response = chatModel.call(prompt).toString();
//
//        assert response != null;
//        conversationHistory.add(new AssistantMessage(response));
//
//        return response;

//    }

    public void resetHistory() {
        conversationHistory.clear();
    }


}
