package be.pxl.services.services;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.CommentUpdateRequest;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(CommentRequest commentRequest);
    CommentResponse updateComment(long id, CommentUpdateRequest commentRequest);
    void deleteComment(long id);
    CommentResponse getCommentById(long id);
    List<CommentResponse> getCommentsByPostId(long postId);
}
