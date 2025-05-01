package com.amanso.backend.chat;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amanso.backend.common.StringResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "A controller for managing chats")
public class ChatController {

    private final ChatService chatService;

    /**
     * Create a new chat between two users.
     * The route recieives the sender and receiver IDs as request parameters.
     * Invokes the chatService to get or create a chat, and return the chat
     * ID.
     * Build a StringResponse object with the chat ID and returns it in the
     * response.
     * 
     */
    @PostMapping
    public ResponseEntity<StringResponse> createChat(
            @RequestParam(value = "sender-id") String senderId,
            @RequestParam(value = "receiver-id") String receiverId) {
        final String chatId = chatService.createdChat(senderId, receiverId);
        StringResponse response = StringResponse.builder()
                .response(chatId)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Get all chats for the current user.
     * The route uses the authentication object to get the current user's ID.
     * Invokes the chatService to get the chats for the current user and return
     * them in the response.
     * 
     */
    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChatsByReciever(Authentication currentUser) {
        return ResponseEntity.ok(chatService.getChatByRecieverId(currentUser));
    }

}
