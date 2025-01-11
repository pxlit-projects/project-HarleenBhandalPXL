package be.pxl.services.services;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.CommentUpdateRequest;
import be.pxl.services.exception.NotFoundException;
import be.pxl.services.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final Logger logger = Logger.getLogger(CommentService.class.getName());

    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        if  (commentRequest.getPostId() == null || commentRequest.getContent() == null || commentRequest.getContent().isEmpty() || commentRequest.getUserName() == null || commentRequest.getUserName().isEmpty()) {
            throw new IllegalArgumentException("PostId, content and userName are required");
        }

        Comment comment = Comment.builder()
                .postId(commentRequest.getPostId())
                .content(commentRequest.getContent())
                .userName(commentRequest.getUserName())
                .build();

        this.commentRepository.save(comment);

        logger.info("Comment created with id: " + comment.getId());

        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .content(comment.getContent())
                .userName(comment.getUserName())
                .build();
    }

    @Override
    public CommentResponse updateComment(long id, CommentUpdateRequest commentRequest) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));

        if  (commentRequest.getContent() == null || commentRequest.getContent().isEmpty()) {
            throw new IllegalArgumentException("Content is required");
        }

        comment.setContent(commentRequest.getContent());

        commentRepository.save(comment);

        logger.info("Comment updated with id: " + comment.getId());

        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .content(comment.getContent())
                .userName(comment.getUserName())
                .build();
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));

        logger.info("Comment deleted with id: " + comment.getId());

        commentRepository.delete(comment);
    }

    @Override
    public CommentResponse getCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));

        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPostId())
                .content(comment.getContent())
                .userName(comment.getUserName())
                .build();
    }

    @Override
    public List<CommentResponse> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId).orElseThrow(() -> new NotFoundException("Comment not found"));

        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .id(comment.getId())
                        .postId(comment.getPostId())
                        .content(comment.getContent())
                        .userName(comment.getUserName())
                        .build()).toList();
    }
}
