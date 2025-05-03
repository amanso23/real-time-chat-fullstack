package com.amanso.backend.chat;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {

    private UUID id;
    private String name;
    private Long unreadMessagesCount;
    private String lastMessageContent;
    private LocalDateTime lastMessageTime;
    private Boolean isRecieverOnline;
    private String senderId;
    private String recieverId;

}
