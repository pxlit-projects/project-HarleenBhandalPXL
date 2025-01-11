package be.pxl.services.services;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final PostClient postClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public PostResponse approvePost(long id, String role) {
        PostResponse postResponse = postClient.approvePost(id, role);
        rabbitTemplate.convertAndSend("myQueue", "Post with id " + id + " has been approved");
        return postResponse;
    }

    @Override
    public RejectedPostResponse rejectPost(long id, String role, ReviewRequest reviewRequest) {
        RejectedPostResponse rejectedPostResponse = postClient.rejectPost(id, role, reviewRequest);
        rabbitTemplate.convertAndSend("myQueue", "Post with id " + id + " has been rejected by " + reviewRequest.getAuthor() + " for the reason: " + reviewRequest.getComment());
        return rejectedPostResponse;
    }
}
