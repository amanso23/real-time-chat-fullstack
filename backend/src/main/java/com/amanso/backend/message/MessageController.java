package com.amanso.backend.message;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Tag(name = "Message", description = "A controller for managing messages")
public class MessageController {

    private final MessageService messageService;

    /*
     * * This endpoint is used to send a message to a chat.
     * It takes a MessageRequest object as input and saves the message to the
     * database.
     * 
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@RequestBody MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
    }

    /*
     * This endpoint is used to get all messages for a given chat id.
     * It takes a chat id as input and returns a list of MessageResponse objects.
     * 
     */
    @PostMapping(value = "/upload-media", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMediaMessage(
            @Parameter() @RequestParam("chat-id") UUID chatId, @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        messageService.uploadMediaMessage(chatId, file, authentication);

    }

    /*
     * This endpoint is used to set all messages to seen for a given chat id.
     * 
     */
    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setMessagesToSeenByChatId(@RequestParam("chat-id") UUID chatId, Authentication authentication) {
        messageService.setMessagesToSeenByChatId(chatId, authentication);
    }

    @GetMapping("/chat/{chat-id}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable("chat-id") UUID chatId) {
        return ResponseEntity.ok(messageService.findMessagesByChatId(chatId));
    }

}
