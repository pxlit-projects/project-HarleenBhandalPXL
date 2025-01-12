package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.enums.PostStatus;
import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;
import be.pxl.services.dto.RejectedPostResponse;
import be.pxl.services.dto.ReviewRequest;
import be.pxl.services.exception.NotFoundException;
import be.pxl.services.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final Logger logger = Logger.getLogger(PostService.class.getName());

    @Override
    public Long createPost(PostRequest postRequest) {
        if  (postRequest.getTitle() == null || postRequest.getTitle().isEmpty() || postRequest.getContent() == null || postRequest.getContent().isEmpty() || postRequest.getAuthor() == null|| postRequest.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Title, content and author are required");
        }

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();

        this.postRepository.save(post);

        logger.info("Post created with id: " + post.getId());

        return post.getId();
    }

    @Override
    public PostResponse updatePost(long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        if  (postRequest.getTitle() == null || postRequest.getTitle().isEmpty() || postRequest.getContent() == null || postRequest.getContent().isEmpty() || postRequest.getAuthor() == null|| postRequest.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Title, content and author are required");
        }

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());

        postRepository.save(post);

        logger.info("Post updated with id: " + post.getId());

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .creationDate(post.getCreationDate())
                .status(post.getStatus())
                .build();
    }

    @Override
    public List<PostResponse> getPublishedPosts() {
        return postRepository.findAll().stream()
                .filter(post -> post.getStatus() == PostStatus.APPROVED)
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .creationDate(post.getCreationDate())
                        .status(post.getStatus())
                        .build()).toList();
    }

    @Override
    public List<PostResponse> getConceptPosts() {
        return postRepository.findAll().stream()
                .filter(p -> p.getStatus() == PostStatus.CONCEPT)
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .creationDate(post.getCreationDate())
                        .status(post.getStatus())
                        .build()).toList();
    }

    @Override
    public List<PostResponse> getPendingPosts() {
        return postRepository.findAll().stream()
                .filter(p -> p.getStatus() == PostStatus.PENDING)
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .creationDate(post.getCreationDate())
                        .status(post.getStatus())
                        .build()).toList();
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .creationDate(post.getCreationDate())
                        .status(post.getStatus())
                        .build()).toList();
    }

    @Override
    public List<PostResponse> getRejectedPosts() {
        return postRepository.findAll().stream()
                .filter(p -> p.getStatus() == PostStatus.REJECTED)
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .creationDate(post.getCreationDate())
                        .status(post.getStatus())
                        .build()).toList();
    }

    @Override
    public Long savePostAsConcept(PostRequest postRequest) {
        if  (postRequest.getTitle() == null || postRequest.getTitle().isEmpty() || postRequest.getContent() == null || postRequest.getContent().isEmpty() || postRequest.getAuthor() == null|| postRequest.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Title, content and author are required");
        }

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .creationDate(LocalDateTime.now())
                .status(PostStatus.CONCEPT)
                .build();

        logger.info("Post saved as concept with id: " + post.getId() + " by " + post.getAuthor());

        this.postRepository.save(post);
        return post.getId();
    }

    @Override
    public PostResponse updatePostToPending(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        post.setStatus(PostStatus.PENDING);
        postRepository.save(post);

        logger.info("Post updated to pending with id: " + post.getId());

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .creationDate(post.getCreationDate())
                .status(post.getStatus())
                .build();
    }

    @Override
    public PostResponse getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .status(post.getStatus())
                .creationDate(post.getCreationDate())
                .build();
    }

    @Override
    public PostResponse approvePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        post.setStatus(PostStatus.APPROVED);
        postRepository.save(post);

        logger.info("Post approved with id: " + post.getId());

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .creationDate(post.getCreationDate())
                .status(post.getStatus())
                .build();
    }

    @Override
    public RejectedPostResponse rejectPost(long id, ReviewRequest comment) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        if (comment.getAuthor() == null || comment.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author is required");
        }

        post.setStatus(PostStatus.REJECTED);
        postRepository.save(post);

        String rejectionReason = comment.getComment();

        if (comment.getComment() == null || comment.getComment().isEmpty()) {
            rejectionReason = "No reason provided";
        }

        logger.info("Post rejected with id: " + post.getId() + " by " + comment.getAuthor());

        return RejectedPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .creationDate(post.getCreationDate())
                .status(post.getStatus())
                .rejectionReason(rejectionReason)
                .rejectedBy(comment.getAuthor())
                .build();
    }
}
