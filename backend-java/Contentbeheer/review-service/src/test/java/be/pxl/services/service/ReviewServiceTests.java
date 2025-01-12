package be.pxl.services.service;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTests {
    @Mock
    private PostClient postClient;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApprovePost() {
        PostResponse postResponse = new PostResponse();
        when(postClient.approvePost(any(Long.class), any(String.class))).thenReturn(postResponse);

        PostResponse result = reviewService.approvePost(1L, "editor");

        assertNotNull(result);
        verify(postClient, times(1)).approvePost(1L, "editor");
        verify(rabbitTemplate, times(1)).convertAndSend("myQueue", "Post with id 1 has been approved");
    }

    @Test
    public void testRejectPost() {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .author("Test reviewer")
                .comment("Test comment")
                .build();
        RejectedPostResponse rejectedPostResponse = new RejectedPostResponse();
        when(postClient.rejectPost(any(Long.class), any(String.class), any(ReviewRequest.class))).thenReturn(rejectedPostResponse);

        RejectedPostResponse result = reviewService.rejectPost(1L, "editor", reviewRequest);

        assertNotNull(result);
        verify(postClient, times(1)).rejectPost(1L, "editor", reviewRequest);
        verify(rabbitTemplate, times(1)).convertAndSend("myQueue", "Post with id 1 has been rejected by Test reviewer for the reason: Test comment");
    }
}