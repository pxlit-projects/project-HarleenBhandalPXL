package be.pxl.services.services;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.dto.PostResponse;
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
    public PostResponse rejectPost(long id, String role) {
        return postClient.rejectPost(id, role);
    }
}
