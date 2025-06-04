package com.example.ollamabot.controller;

import com.example.ollamabot.service.OllamaChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    OllamaChatService chatService;

    @GetMapping("/generate")
    public ResponseEntity<?> chatbot(@RequestParam String message) {
        var response = chatService.chat(message);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/questionImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> chatWithImage(
        @RequestParam("message") String message,
        @RequestParam("image")MultipartFile imageFile
    ) {
//        var response = chatService.chatWithImage(message, imageFile);
        var response = "teste";

        return ResponseEntity.ok(response);

    }

    @GetMapping("/reset")
    public ResponseEntity<?> resetChat() {
        chatService.resetHistory();
        return ResponseEntity.ok().body("Reset Success!");
    }

}
