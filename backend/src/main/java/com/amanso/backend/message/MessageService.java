package com.amanso.backend.message;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.amanso.backend.chat.Chat;
import com.amanso.backend.chat.ChatRepository;
import com.amanso.backend.file.FileService;
import com.amanso.backend.file.FileUtils;
import com.amanso.backend.notification.Notification;
import com.amanso.backend.notification.NotificationService;
import com.amanso.backend.notification.NotificationType;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final FileService fileService;
    private final NotificationService notificationService;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setSenderId(messageRequest.getSenderId());
        message.setReceiverId(messageRequest.getReceiverId());
        message.setChat(chat);
        message.setType(messageRequest.getType());
        message.setState(MessageState.SENT);

        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .content(messageRequest.getContent())
                .senderId(messageRequest.getSenderId())
                .receiverId(messageRequest.getReceiverId())
                .chatName(chat.getChatName(messageRequest.getSenderId()))
                .messageType(messageRequest.getType())
                .notificationType(NotificationType.MESSAGE)
                .build();

        notificationService.sendNotification(messageRequest.getReceiverId(), notification);

    }

    /*
     * Returns a list of mapped MessageResponse objects for the given chatId.
     * The messages are retrieved from the messageRepository using the chatId.
     * 
     */
    public List<MessageResponse> findMessagesByChatId(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(mapper::toMessageResponse)
                .toList();
    }

    @Transactional
    public void setMessagesToSeenByChatId(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String recieverId = getRecieverId(chat, authentication);

        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.SENT, MessageState.SEEN);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .receiverId(recieverId)
                .senderId(getSenderId(chat, authentication))
                .notificationType(NotificationType.SEEN)
                .build();

        notificationService.sendNotification(recieverId, notification);
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String senderId = getSenderId(chat, authentication);
        final String receiverId = getRecieverId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);

        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setType(MessageType.IMAGE);
        message.setState(MessageState.SENT);
        message.setChat(chat);
        message.setMediaFilePath(filePath);

        messageRepository.save(message);

        Notification notification = Notification.builder()
                .chatId(chat.getId())
                .senderId(senderId)
                .receiverId(receiverId)
                .chatName(chat.getChatName(senderId))
                .messageType(MessageType.IMAGE)
                .notificationType(NotificationType.IMAGE)
                .media(FileUtils.readFileFromLocation(filePath))
                .build();

        notificationService.sendNotification(receiverId, notification);

    }

    /*
     * Verifies if the user is the sender or receiver of the chat and returns the
     * receiver id.
     * If the user is the sender, return the receiver id.
     * If the user is the receiver, return the sender id.
     */

    private String getSenderId(Chat chat, Authentication authentication) {
        if (String.valueOf(chat.getSender().getId()).equals(authentication.getName())) {
            return String.valueOf(chat.getSender().getId());
        }
        return String.valueOf(chat.getReceiver().getId());
    }

    private String getRecieverId(Chat chat, Authentication authentication) {
        if (String.valueOf(chat.getSender().getId()).equals(authentication.getName())) {
            return String.valueOf(chat.getReceiver().getId());
        }
        return String.valueOf(chat.getSender().getId());

    }

}
