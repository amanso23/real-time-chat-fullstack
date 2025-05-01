package com.amanso.backend.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {

    public ChatResponse toChatResponse(Chat chat, String userId) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(userId))
                .unreadMessagesCount(chat.getUnreadMessagesCount(userId))
                .lastMessageContent(chat.getLastMessage())
                .lastMessageTime(chat.getLastMessageTime())
                .isRecieverOnline(chat.getReceiver().isOnline())
                .senderId(chat.getSender().getId())
                .recieverId(chat.getReceiver().getId())
                .build();

    }

}
