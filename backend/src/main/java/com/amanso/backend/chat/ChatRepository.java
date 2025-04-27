package com.amanso.backend.chat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, String> {

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID)
    public List<Chat> findChatBySenderId(@Param("senderId") String userId);

    @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID)
    public Optional<Chat> findChatBySenderIdAndReceiverId(String senderId, String receiverId);

}
