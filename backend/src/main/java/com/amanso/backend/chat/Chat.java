package com.amanso.backend.chat;

import com.amanso.backend.common.BaseAuditingEntity;
import com.amanso.backend.message.Message;
import com.amanso.backend.message.MessageState;
import com.amanso.backend.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.UUID;

import java.beans.Transient;
import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    public Long getUnreadMessages(final String senderId) {
        return messages
                .stream()
                .filter(m -> m.getReceiverId().equals(senderId))
                .filter(m -> MessageState.SENT == m.getState())
                .count();
    }

    /*
     * Get the last message from the messages list. If the list is empty, return
     * null. Otherwise, return the content of the first message in the list.
     * 
     */
    @Transient
    public String getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(0).getContent();
    }

}
