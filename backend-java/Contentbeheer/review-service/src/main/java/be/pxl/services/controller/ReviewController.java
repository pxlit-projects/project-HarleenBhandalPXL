package be.pxl.services.controller;

import be.pxl.services.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("/post/{postId}/approve")
    public ResponseEntity<?> approvePost(@PathVariable long postId, @RequestHeader("Role") String role) {
        return ResponseEntity.ok(reviewService.approvePost(postId, role));
    }

    @GetMapping("/post/{postId}/reject")
    public ResponseEntity<?> rejectPost(@PathVariable long postId, @RequestHeader("Role") String role) {
        return ResponseEntity.ok(reviewService.rejectPost(postId, role));
    }
}
