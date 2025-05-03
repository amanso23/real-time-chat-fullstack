package com.amanso.backend.notification;

import java.util.UUID;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(UUID destination, Notification payload) {
        log.info("Sending notification to {}: {}", destination, payload);
        messagingTemplate.convertAndSendToUser(String.valueOf(destination), "/chat", payload);
    }

}
