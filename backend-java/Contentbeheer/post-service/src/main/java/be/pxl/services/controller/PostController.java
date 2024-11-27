package be.pxl.services.controller;

import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;
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
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        Long id = this.postService.createPost(postRequest);
        return ResponseEntity.created(URI.create("/api/posts/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts() {
        List<PostResponse> posts = this.postService.getPosts();
        return ResponseEntity.ok(posts);
    }
}
