package com.amanso.backend.message;

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
public class MessageResponse {

    private Long id;
    private String content;
    private UUID senderId;
    private UUID receiverId;
    private MessageType type;
    private UUID chatId;
    private MessageState state;
    private LocalDateTime createdAt;
    private byte[] media;

}
