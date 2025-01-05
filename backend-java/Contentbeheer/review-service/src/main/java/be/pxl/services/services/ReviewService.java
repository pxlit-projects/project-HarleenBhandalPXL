package be.pxl.services.services;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final PostClient postClient;

    @Override
    public PostResponse approvePost(long id, String role) {
        return postClient.approvePost(id, role);
    }

    @Override
    public RejectedPostResponse rejectPost(long id, String role, ReviewRequest reviewRequest) {
        return postClient.rejectPost(id, role, reviewRequest);
    }
}
