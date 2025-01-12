package be.pxl.services.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationController notificationController;

    @Test
    public void testNotify() {
        String message = "Test notification message";

        notificationController.notify(message);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/notification", message);
    }
}