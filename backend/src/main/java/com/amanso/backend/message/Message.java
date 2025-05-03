package com.amanso.backend.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

import static jakarta.persistence.GenerationType.SEQUENCE;

import java.util.UUID;

import com.amanso.backend.chat.Chat;
import com.amanso.backend.common.BaseAuditingEntity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
/*
 * Returns all messages for a given chat id, ordered by created date in
 * descending order. This is used to get all messages for a given chat id.
 */
@NamedQuery(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID, query = "SELECT m FROM Message m WHERE m.chat.id = :chatId ORDER BY m.createdDate DESC")
/*
 * Update all messages for a given chat id to a new state, where the old state
 * is the one that is currently set.
 * This is used to set all messages to seen for a given chat id.
 */
@NamedQuery(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT_ID, query = "UPDATE Message m SET m.state = :newState WHERE m.chat.id = :chatId AND m.state = :oldState")
public class Message extends BaseAuditingEntity {

    @Id
    @SequenceGenerator(name = "message_seq", sequenceName = "message_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "message_seq")
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Enumerated(EnumType.STRING)
    private MessageState state;
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(name = "sender_id", nullable = false)
    private UUID senderId;
    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;

    private String mediaFilePath;

}
