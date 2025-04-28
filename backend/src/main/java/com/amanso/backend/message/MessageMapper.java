package com.amanso.backend.message;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class MessageMapper {

    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .chatId(message.getChat().getId())
                .state(message.getState())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
