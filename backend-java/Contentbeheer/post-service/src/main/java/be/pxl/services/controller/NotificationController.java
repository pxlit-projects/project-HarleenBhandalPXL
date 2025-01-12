package be.pxl.services.controller;

import be.pxl.services.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;
    private final Logger logger = Logger.getLogger(NotificationController.class.getName());

    public void notify(String message) {
        messagingTemplate.convertAndSend("/topic/notification", message);
        logger.info("Notification sent: " + message);
    }
}
