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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;

    @Override
    public Long createPost(PostRequest postRequest) {
        if  (postRequest.getTitle().isEmpty() || postRequest.getContent().isEmpty() || postRequest.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Title, content and author are required");
        }

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .creationDate(LocalDateTime.now())
                .isConcept(false)
                .status(PostStatus.PENDING)
                .build();

        this.postRepository.save(post);
        return post.getId();
    }

    @Override
    public void deletePost() {

    }

    @Override
    public PostResponse updatePost(long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        if  (postRequest.getTitle().isEmpty() || postRequest.getContent().isEmpty() || postRequest.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Title, content and author are required");
        }

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());

        postRepository.save(post);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .isConcept(post.isConcept())
                .creationDate(post.getCreationDate())
                .status(post.getStatus())
                .build();
    }

    @Override
    public List<PostResponse> getPublishedPosts() {
        return postRepository.findAll().stream()
                .filter(post -> !post.isConcept())
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .isConcept(post.isConcept())
                        .creationDate(post.getCreationDate())
                        .status(post.getStatus())
                        .build()).toList();
    }

    @Override
    public List<PostResponse> getConceptPosts() {
        return postRepository.findAll().stream()
                .filter(Post::isConcept)
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .isConcept(post.isConcept())
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
                        .isConcept(post.isConcept())
                        .status(post.getStatus())
                        .build()).toList();
    }

    @Override
    public Long savePostAsConcept(PostRequest postRequest) {
        if  (postRequest.getTitle().isEmpty() || postRequest.getContent().isEmpty() || postRequest.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Title, content and author are required");
        }

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .creationDate(LocalDateTime.now())
                .isConcept(true)
                .status(PostStatus.PENDING)
                .build();

        this.postRepository.save(post);
        return post.getId();
    }

    @Override
    public PostResponse getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .isConcept(post.isConcept())
                .creationDate(post.getCreationDate())
                .build();
    }

    @Override
    public PostResponse approvePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        post.setStatus(PostStatus.APPROVED);
        postRepository.save(post);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .isConcept(post.isConcept())
                .creationDate(post.getCreationDate())
                .status(post.getStatus())
                .build();
    }

    @Override
    public RejectedPostResponse rejectPost(long id, ReviewRequest comment) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        post.setStatus(PostStatus.REJECTED);
        postRepository.save(post);

        String rejectionReason = comment.getComment();

        if (comment.getAuthor() == null || comment.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("Author is required");
        }

        if (comment.getComment() == null || comment.getComment().isEmpty()) {
            rejectionReason = "No reason provided";
        }

        return RejectedPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .isConcept(post.isConcept())
                .creationDate(post.getCreationDate())
                .status(post.getStatus())
                .rejectionReason(rejectionReason)
                .rejectedBy(comment.getAuthor())
                .build();
    }
}
