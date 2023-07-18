package com.aidanloten.centralbanker.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToTopic(Topic topic, Object payload) {
        messagingTemplate.convertAndSend(String.format("/topic/%s", topic.name()), payload);
    }

    public enum Topic {
        personAssets,
    }
}
