package com.amanso.backend.chat;

import com.amanso.backend.common.BaseAuditingEntity;
import com.amanso.backend.message.Message;
import com.amanso.backend.message.MessageState;
import com.amanso.backend.message.MessageType;
import com.amanso.backend.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.UUID;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@Table(name = "chat")
/*
 * Query to find chat by sender ID. It retrieves distinct chats where the
 * sender ID matches the provided sender ID or the receiver ID matches the
 * provided sender ID. The results are ordered by the created date in
 * descending order.
 */
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID, query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.receiver.id = :senderId ORDER BY c.createdDate DESC")
/*
 * Query to find chat by sender ID and receiver ID. It retrieves distinct chats
 * where the sender ID matches the provided sender ID and the receiver ID
 * matches the provided receiver ID, or vice versa. The results are ordered by
 * the created date in descending order.
 *
 */
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER_ID, query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.receiver.id = :receiverId) OR (c.sender.id = :receiverId AND c.receiver.id = :senderId) ORDER BY c.createdDate DESC")
/*
 * The Chat class represents a chat entity in the system. It contains the
 * sender and receiver users, a list of messages, and methods to retrieve chat
 * information such as chat name, unread messages count, last message content,
 * and last message time.
 */
public class Chat extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdDate DESC")
    private List<Message> messages;

    /*
     * Return the chat name based on the sender ID, if the sender ID matches the
     * chat sender ID, return the receiver's name, otherwise return the sender's
     * name.
     */
    @Transient
    public String getChatName(final Long senderId) {
        if (sender.getId().equals(senderId)) {
            return receiver.getFirstName() + " " + receiver.getLastName();
        } else {
            return sender.getFirstName() + " " + sender.getLastName();
        }
    }

    /*
     * Filter the messages list to get the unread messages for the given sender ID.
     * The method filters the messages where the receiver ID matches the sender ID
     * and the message state is SENT. It returns the count of such messages.
     */
    @Transient
    public Long getUnreadMessagesCount(final String senderId) {
        return messages
                .stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> MessageState.SENT == m.getState())
                .count();
    }

    /*
     * Returns the content of the latest message.
     * If there are no messages, returns null.
     * If the last message is not of type TEXT, returns "Attachment".
     */
    @Transient
    public String getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }

        Message lastMessage = messages.get(messages.size() - 1);
        return lastMessage.getType() == MessageType.TEXT
                ? lastMessage.getContent()
                : "Attachment";
    }

    /*
     * Returns the date of the latest message.
     * If there are no messages, returns null.
     */
    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages.isEmpty()) {
            return null;
        }

        return messages.get(messages.size() - 1).getCreatedDate();
    }

}
