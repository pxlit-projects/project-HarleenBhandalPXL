package be.pxl.services.service;

import be.pxl.services.controller.NotificationController;
import be.pxl.services.services.QueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class QueueServiceTests {
    @Mock
    private NotificationController notificationController;

    @InjectMocks
    private QueueService queueService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListen() {
        String message = "Test message";

        queueService.listen(message);

        verify(notificationController, times(1)).notify(message);
    }
}
