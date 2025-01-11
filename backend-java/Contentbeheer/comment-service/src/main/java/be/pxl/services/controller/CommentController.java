package be.pxl.services.controller;

import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.CommentUpdateRequest;
import be.pxl.services.services.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = this.commentService.createComment(commentRequest);
        return ResponseEntity.created(URI.create("/api/comments/" + commentResponse.getId())).build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable long postId) {
        List<CommentResponse> commentResponse = this.commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(commentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable long id, @RequestBody CommentUpdateRequest commentRequest) {
        CommentResponse commentResponse = this.commentService.updateComment(id, commentRequest);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable long id) {
        CommentResponse commentResponse = this.commentService.getCommentById(id);
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        this.commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
