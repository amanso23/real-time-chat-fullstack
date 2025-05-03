package com.amanso.backend.chat;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {

    public ChatResponse toChatResponse(Chat chat, UUID userId) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(userId))
                .unreadMessagesCount(chat.getUnreadMessagesCount(userId))
                .lastMessageContent(chat.getLastMessage())
                .lastMessageTime(chat.getLastMessageTime())
                .isRecieverOnline(chat.getReceiver().isOnline())
                .senderId(String.valueOf(chat.getSender().getId()))
                .recieverId(String.valueOf((chat.getReceiver().getId())))
                .build();

    }

}
