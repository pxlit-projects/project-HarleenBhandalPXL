package be.pxl.services.services;

import be.pxl.services.domain.dto.PostResponse;

public interface IReviewService {
    PostResponse approvePost(long id, String role);
    PostResponse rejectPost(long id, String role);
}
