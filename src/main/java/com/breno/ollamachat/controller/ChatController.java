package com.breno.ollamachat.controller;

import com.breno.ollamachat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> teacherChat(@RequestParam String question) {
        String response = chatService.askTeacher(question);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/describe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> describeImage(
            @RequestParam MultipartFile image,
            @RequestParam String question
    ) throws IOException {

        if(image.isEmpty()) {
            return ResponseEntity.badRequest().body("image not found.");
        }

        byte[] imageData = image.getBytes();
        Resource imageResource = new ByteArrayResource(imageData);
        MediaType mediaType = MediaType.parseMediaType(image.getContentType());


        String response = chatService.describeImage(imageResource, mediaType, question);
        return ResponseEntity.ok(response);
    }
}
