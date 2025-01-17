package be.pxl.services.controller;

import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;
import be.pxl.services.dto.RejectedPostResponse;
import be.pxl.services.dto.ReviewRequest;
import be.pxl.services.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final IPostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest, @RequestHeader("Role") String role) {
        if (!role.equals("editor")) {
            return ResponseEntity.status(403).body("Only editors can post");
        }

        Long id = this.postService.createPost(postRequest);
        return ResponseEntity.created(URI.create("/api/posts/" + id)).build();
    }

    @PostMapping("/concepts/add")
    public ResponseEntity<String> savePostAsConcept(@RequestBody PostRequest postRequest, @RequestHeader("Role") String role) {
        if (!role.equals("editor")) {
            return ResponseEntity.status(403).body("Only editors can post");
        }

        Long id = this.postService.savePostAsConcept(postRequest);
        return ResponseEntity.created(URI.create("/api/posts/concepts/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPublishedPosts() {
        List<PostResponse> posts = this.postService.getPublishedPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/concepts")
    public ResponseEntity<List<PostResponse>> getConceptPosts() {
        List<PostResponse> conceptPosts = this.postService.getConceptPosts();
        return ResponseEntity.ok(conceptPosts);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PostResponse>> getPendingPosts() {
        List<PostResponse> pendingPosts = this.postService.getPendingPosts();
        return ResponseEntity.ok(pendingPosts);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<PostResponse>> getRejectedPosts() {
        List<PostResponse> pendingPosts = this.postService.getRejectedPosts();
        return ResponseEntity.ok(pendingPosts);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts(@RequestHeader("Role") String role) {
        if (!role.equals("editor")) {
            return ResponseEntity.status(403).body("Only editors can view concept posts");
        }

        List<PostResponse> posts = this.postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> updatePost(@PathVariable long id, @RequestBody PostRequest postRequest, @RequestHeader("Role") String role) {
        if (!role.equals("editor")) {
            return ResponseEntity.status(403).body("Only editors can edit");
        }

        PostResponse postResponse = this.postService.updatePost(id, postRequest);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{id}/pending")
    public ResponseEntity<?> updatePostToPending(@PathVariable long id, @RequestHeader("Role") String role) {
        if (!role.equals("editor")) {
            return ResponseEntity.status(403).body("Only editors can update to pending");
        }

        PostResponse postResponse = this.postService.updatePostToPending(id);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable long id) {
        PostResponse postResponse = this.postService.getPostById(id);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{id}/approve")
    public ResponseEntity<?> approvePost(@PathVariable long id, @RequestHeader("Role") String role) {
        if (!role.equals("editor")) {
            return ResponseEntity.status(403).body("Only editors can approve");
        }

        PostResponse postResponse = this.postService.approvePost(id);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectPost(@PathVariable long id, @RequestHeader("Role") String role, @RequestBody ReviewRequest comment) {
        if (!role.equals("editor")) {
            return ResponseEntity.status(403).body("Only editors can reject");
        }

        RejectedPostResponse rejectedPostResponse = this.postService.rejectPost(id, comment);
        return ResponseEntity.ok(rejectedPostResponse);
    }
}
