package be.pxl.services.services;

import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPostResponse;
import be.pxl.services.domain.dto.ReviewRequest;

public interface IReviewService {
    PostResponse approvePost(long id, String role);
    RejectedPostResponse rejectPost(long id, String role, ReviewRequest reviewRequest);
}
