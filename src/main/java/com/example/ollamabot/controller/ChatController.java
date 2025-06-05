package com.example.ollamabot.controller;

import com.example.ollamabot.service.OllamaChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    OllamaChatService chatService;

    @GetMapping("/teacher")
    public ResponseEntity<?> chatbot(@RequestParam String message) {
        var response = chatService.askAsTeacher(message);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/describe-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> describeImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "question", defaultValue = "Descreva esta imagem em detalhes.") String question
    ) throws IOException {

        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Por favor, selecione uma imagem para enviar.");
        }
        byte[] imageData = image.getBytes();
        Resource imageResource = new ByteArrayResource(imageData);

        MediaType mediaType = MediaType.parseMediaType(image.getContentType());

        String description = chatService.describeImage(imageResource, mediaType, question);
        return ResponseEntity.ok(description);
    }

}
