package com.amanso.backend.chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amanso.backend.user.User;
import com.amanso.backend.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatByRecieverId(Authentication currentUser) {
        // Retrieve the current user's ID from the authentication object
        final UUID userId = UUID.fromString(currentUser.getName());
        return chatRepository.findChatBySenderId(userId)
                .stream()
                .map(chat -> chatMapper.toChatResponse(chat, userId))
                .toList();

    }

    public UUID createdChat(UUID senderId, UUID recieverId) {
        // Check if the chat already exists between the sender and receiver
        // If it does, return the existing chat ID
        Optional<Chat> existingChat = chatRepository.findChatBySenderIdAndReceiverId(senderId, recieverId);
        if (existingChat.isPresent()) {
            return existingChat.get().getId();
        }

        // If the chat does not exist, create a new one
        // and save it to the database
        User sender = userRepository.findByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        User receiver = userRepository.findByPublicId(recieverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        // Create a new chat object and set the sender and receiver
        // Save the new chat to the database
        // and return the chat ID
        Chat newChat = new Chat();
        newChat.setSender(sender);
        newChat.setReceiver(receiver);
        chatRepository.save(newChat);
        return newChat.getId();

    }

}
