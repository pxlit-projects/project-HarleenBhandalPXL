package be.pxl.services.services;

import be.pxl.services.controller.NotificationController;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class QueueService {
    private final NotificationController notificationController;
    private final Logger logger = Logger.getLogger(QueueService.class.getName());

    @RabbitListener(queues = "myQueue")
    public void listen(String in) {
        System.out.println("Message received in post-service from myQueue : " + in);
        notificationController.notify(in);

        logger.info("Message received in post-service from myQueue : " + in);
    }
}

