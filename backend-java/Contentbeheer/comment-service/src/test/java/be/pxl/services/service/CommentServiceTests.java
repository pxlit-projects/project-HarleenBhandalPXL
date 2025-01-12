package be.pxl.services.service;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.CommentUpdateRequest;
import be.pxl.services.exception.NotFoundException;
import be.pxl.services.repository.CommentRepository;
import be.pxl.services.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComment() {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentResponse commentResponse = commentService.createComment(commentRequest);

        assertNotNull(commentResponse);
        assertEquals(1L, comment.getId());
        assertEquals("Test content", commentResponse.getContent());
        assertEquals("Test user", commentResponse.getUserName());
    }

    @Test
    public void testUpdateComment() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        CommentUpdateRequest updateRequest = CommentUpdateRequest.builder()
                .content("Updated content")
                .build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentResponse updatedComment = commentService.updateComment(1L, updateRequest);

        assertNotNull(updatedComment);
        assertEquals("Updated content", updatedComment.getContent());
    }

    @Test
    public void testDeleteComment() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    public void testGetCommentById() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentResponse commentResponse = commentService.getCommentById(1L);

        assertNotNull(commentResponse);
        assertEquals(1L, commentResponse.getId());
        assertEquals("Test content", commentResponse.getContent());
        assertEquals("Test user", commentResponse.getUserName());
    }

    @Test
    public void testGetCommentsByPostId() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        when(commentRepository.findByPostId(1L)).thenReturn(Optional.of(List.of(comment)));

        List<CommentResponse> comments = commentService.getCommentsByPostId(1L);

        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("Test content", comments.get(0).getContent());
    }

    @Test
    public void testCreateCommentWithInvalidData() {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(null)
                .content("")
                .userName("")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(commentRequest);
        });

        assertEquals("PostId, content and userName are required", exception.getMessage());
    }

    @Test
    public void testUpdateCommentWithInvalidData() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentUpdateRequest updateRequest = CommentUpdateRequest.builder()
                .content("")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.updateComment(1L, updateRequest);
        });

        assertEquals("Content is required", exception.getMessage());
    }

    @Test
    public void testGetCommentsByPostIdWithNoComments() {
        when(commentRepository.findByPostId(1L)).thenReturn(Optional.of(List.of()));

        List<CommentResponse> comments = commentService.getCommentsByPostId(1L);

        assertNotNull(comments);
        assertTrue(comments.isEmpty());
    }

    @Test
    public void testGetCommentByInvalidId() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            commentService.getCommentById(1L);
        });

        assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    public void testDeleteCommentByInvalidId() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            commentService.deleteComment(1L);
        });

        assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    public void testCreateCommentWithNullPostId() {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(null)
                .content("Test content")
                .userName("Test user")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(commentRequest);
        });

        assertEquals("PostId, content and userName are required", exception.getMessage());
    }

    @Test
    public void testCreateCommentWithNullContent() {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content(null)
                .userName("Test user")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(commentRequest);
        });

        assertEquals("PostId, content and userName are required", exception.getMessage());
    }

    @Test
    public void testCreateCommentWithNullUserName() {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("Test content")
                .userName(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(commentRequest);
        });

        assertEquals("PostId, content and userName are required", exception.getMessage());
    }

    @Test
    public void testUpdateCommentWithNullContent() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        CommentUpdateRequest updateRequest = CommentUpdateRequest.builder()
                .content(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.updateComment(1L, updateRequest);
        });

        assertEquals("Content is required", exception.getMessage());
    }

    @Test
    public void testGetCommentsByInvalidPostId() {
        when(commentRepository.findByPostId(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            commentService.getCommentsByPostId(1L);
        });

        assertEquals("Comment not found", exception.getMessage());
    }
}