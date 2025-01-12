package be.pxl.services.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;

    public void notify(String message) {
        messagingTemplate.convertAndSend("/topic/notification", message);
    }
}
