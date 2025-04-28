package com.amanso.backend.message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    List<Message> findMessagesByChatId(String chatId);

    /*
     * Update all messages for a given chat id to a new state, where the old state
     * is the one that is currently set.
     * This is used to set all messages to seen for a given chat id.
     */
    @Query(name = MessageConstants.SET_MESSAGES_TO_SEEN_BY_CHAT_ID)
    @Modifying
    void setMessagesToSeenByChatId(String chatId, MessageState oldState, MessageState newState);

}
