package com.amanso.backend.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amanso.backend.common.StringResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    /**
     * Create a new chat between two users.
     * The route recieives the sender and receiver IDs as request parameters.
     * Invokes the chatService to get or create a new chat, and retrieves the chat
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

}
