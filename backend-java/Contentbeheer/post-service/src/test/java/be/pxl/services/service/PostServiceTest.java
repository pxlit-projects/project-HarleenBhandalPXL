package be.pxl.services.service;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.enums.PostStatus;
import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;
import be.pxl.services.dto.RejectedPostResponse;
import be.pxl.services.dto.ReviewRequest;
import be.pxl.services.exception.NotFoundException;
import be.pxl.services.repository.PostRepository;
import be.pxl.services.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
public class PostServiceTest {

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:8.3");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
    }

    @Test
    public void testCreatePost() {
        PostRequest postRequest = PostRequest.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .build();
        Long postId = postService.createPost(postRequest);

        assertNotNull(postId);
        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    public void testUpdatePost() {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostRequest postRequest = PostRequest.builder()
                .title("New title")
                .content("New content")
                .author("New author")
                .build();

        PostResponse postResponse = postService.updatePost(post.getId(), postRequest);

        assertNotNull(postResponse);
        assertEquals("New title", postResponse.getTitle());
        assertEquals("New content", postResponse.getContent());
        assertEquals("New author", postResponse.getAuthor());
    }

    @Test
    public void testApprovePost() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostResponse postResponse = postService.approvePost(post.getId());

        assertNotNull(postResponse);
        assertEquals(PostStatus.APPROVED, postResponse.getStatus());
    }

    @Test
    public void testRejectPost() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        ReviewRequest reviewRequest = ReviewRequest.builder()
                .author("Test reviewer")
                .comment("Test comment")
                .build();

        RejectedPostResponse rejectedPostResponse = postService.rejectPost(post.getId(), reviewRequest);

        assertNotNull(rejectedPostResponse);
        assertEquals(PostStatus.REJECTED, rejectedPostResponse.getStatus());
        assertEquals("Test comment", rejectedPostResponse.getRejectionReason());
    }

    @Test
    public void testGetPostById() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.APPROVED)
                .build();
        postRepository.save(post);

        PostResponse postResponse = postService.getPostById(post.getId());

        assertNotNull(postResponse);
        assertEquals("Test title", postResponse.getTitle());
        assertEquals("Test content", postResponse.getContent());
        assertEquals("Test author", postResponse.getAuthor());
    }

    @Test
    public void testUpdatePostWithNonNullTitleAndContent() {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostRequest postRequest = PostRequest.builder()
                .title("New title")
                .content("New content")
                .author("New author")
                .build();

        PostResponse postResponse = postService.updatePost(post.getId(), postRequest);

        assertNotNull(postResponse);
        assertEquals("New title", postResponse.getTitle());
        assertEquals("New content", postResponse.getContent());
        assertEquals("New author", postResponse.getAuthor());
    }

    @Test
    public void testUpdatePostWithNullTitle() {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostRequest postRequest = PostRequest.builder()
                .title(null)
                .content("New content")
                .author("New author")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            postService.updatePost(post.getId(), postRequest);
        });
    }

    @Test
    public void testUpdatePostWithNullContent() {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostRequest postRequest = PostRequest.builder()
                .title("New title")
                .content(null)
                .author("New author")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            postService.updatePost(post.getId(), postRequest);
        });
    }

    @Test
    public void testSavePostAsConcept() {
        PostRequest postRequest = PostRequest.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .build();
        Long postId = postService.savePostAsConcept(postRequest);

        assertNotNull(postId);
        assertEquals(PostStatus.CONCEPT, postRepository.findById(postId).orElseThrow().getStatus());
    }

    @Test
    public void testUpdatePostToPending() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.CONCEPT)
                .build();
        postRepository.save(post);

        PostResponse postResponse = postService.updatePostToPending(post.getId());

        assertNotNull(postResponse);
        assertEquals(PostStatus.PENDING, postResponse.getStatus());
    }

    @Test
    public void testGetPublishedPosts() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.APPROVED)
                .build();
        postRepository.save(post);

        List<PostResponse> publishedPosts = postService.getPublishedPosts();

        assertNotNull(publishedPosts);
        assertFalse(publishedPosts.isEmpty());
        assertEquals(PostStatus.APPROVED, publishedPosts.getFirst().getStatus());
    }

    @Test
    public void testGetConceptPosts() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.CONCEPT)
                .build();
        postRepository.save(post);

        List<PostResponse> conceptPosts = postService.getConceptPosts();

        assertNotNull(conceptPosts);
        assertFalse(conceptPosts.isEmpty());
        assertEquals(PostStatus.CONCEPT, conceptPosts.getFirst().getStatus());
    }

    @Test
    public void testGetPendingPosts() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        List<PostResponse> pendingPosts = postService.getPendingPosts();

        assertNotNull(pendingPosts);
        assertFalse(pendingPosts.isEmpty());
        assertEquals(PostStatus.PENDING, pendingPosts.getFirst().getStatus());
    }

    @Test
    public void testGetRejectedPosts() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.REJECTED)
                .build();
        postRepository.save(post);

        List<PostResponse> rejectedPosts = postService.getRejectedPosts();

        assertNotNull(rejectedPosts);
        assertFalse(rejectedPosts.isEmpty());
        assertEquals(PostStatus.REJECTED, rejectedPosts.getFirst().getStatus());
    }

    @Test
    public void testCreatePostWithInvalidData() {
        PostRequest postRequest = PostRequest.builder()
                .title("")
                .content("")
                .author("")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.createPost(postRequest);
        });

        assertEquals("Title, content and author are required", exception.getMessage());
    }

    @Test
    public void testUpdatePostWithInvalidData() {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostRequest postRequest = PostRequest.builder()
                .title("")
                .content("")
                .author("")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.updatePost(post.getId(), postRequest);
        });

        assertEquals("Title, content and author are required", exception.getMessage());
    }

    @Test
    public void testApprovePostWithInvalidId() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            postService.approvePost(999L);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    public void testRejectPostWithInvalidId() {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .author("Test reviewer")
                .comment("Test comment")
                .build();

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            postService.rejectPost(999L, reviewRequest);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    public void testGetPostByInvalidId() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            postService.getPostById(999L);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    public void testGetAllPosts() {
        Post post1 = Post.builder()
                .title("Test title 1")
                .content("Test content 1")
                .author("Test author 1")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.APPROVED)
                .build();
        Post post2 = Post.builder()
                .title("Test title 2")
                .content("Test content 2")
                .author("Test author 2")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post1);
        postRepository.save(post2);

        List<PostResponse> allPosts = postService.getAllPosts();

        assertNotNull(allPosts);
        assertEquals(2, allPosts.size());
    }

    @Test
    public void testCreatePostWithNullTitle() {
        PostRequest postRequest = PostRequest.builder()
                .title(null)
                .content("Test content")
                .author("Test author")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.createPost(postRequest);
        });

        assertEquals("Title, content and author are required", exception.getMessage());
    }

    @Test
    public void testCreatePostWithNullContent() {
        PostRequest postRequest = PostRequest.builder()
                .title("Test title")
                .content(null)
                .author("Test author")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.createPost(postRequest);
        });

        assertEquals("Title, content and author are required", exception.getMessage());
    }

    @Test
    public void testCreatePostWithNullAuthor() {
        PostRequest postRequest = PostRequest.builder()
                .title("Test title")
                .content("Test content")
                .author(null)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.createPost(postRequest);
        });

        assertEquals("Title, content and author are required", exception.getMessage());
    }

    @Test
    public void testSavePostAsConceptWithInvalidData() {
        PostRequest postRequest = PostRequest.builder()
                .title("")
                .content("")
                .author("")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            postService.savePostAsConcept(postRequest);
        });

        assertEquals("Title, content and author are required", exception.getMessage());
    }

    @Test
    public void testUpdatePostToApproved() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostResponse postResponse = postService.approvePost(post.getId());

        assertNotNull(postResponse);
        assertEquals(PostStatus.APPROVED, postResponse.getStatus());
    }

    @Test
    public void testRejectPostWithNullComment() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        ReviewRequest reviewRequest = ReviewRequest.builder()
                .author("Test reviewer")
                .comment(null)
                .build();

        RejectedPostResponse rejectedPostResponse = postService.rejectPost(post.getId(), reviewRequest);

        assertNotNull(rejectedPostResponse);
        assertEquals(PostStatus.REJECTED, rejectedPostResponse.getStatus());
        assertEquals("No reason provided", rejectedPostResponse.getRejectionReason());
    }

    @Test
    public void testRejectPostWithEmptyComment() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        ReviewRequest reviewRequest = ReviewRequest.builder()
                .author("Test reviewer")
                .comment("")
                .build();

        RejectedPostResponse rejectedPostResponse = postService.rejectPost(post.getId(), reviewRequest);

        assertNotNull(rejectedPostResponse);
        assertEquals(PostStatus.REJECTED, rejectedPostResponse.getStatus());
        assertEquals("No reason provided", rejectedPostResponse.getRejectionReason());
    }

    @Test
    public void testGetPostByValidId() {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .creationDate(LocalDateTime.now())
                .status(PostStatus.APPROVED)
                .build();
        postRepository.save(post);

        PostResponse postResponse = postService.getPostById(post.getId());

        assertNotNull(postResponse);
        assertEquals("Test title", postResponse.getTitle());
        assertEquals("Test content", postResponse.getContent());
        assertEquals("Test author", postResponse.getAuthor());
    }

    @Test
    public void testUpdatePostToPendingWithInvalidId() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            postService.updatePostToPending(999L);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    public void testGetPublishedPostsWhenNoneExist() {
        List<PostResponse> publishedPosts = postService.getPublishedPosts();

        assertNotNull(publishedPosts);
        assertTrue(publishedPosts.isEmpty());
    }

    @Test
    public void testGetConceptPostsWhenNoneExist() {
        List<PostResponse> conceptPosts = postService.getConceptPosts();

        assertNotNull(conceptPosts);
        assertTrue(conceptPosts.isEmpty());
    }

    @Test
    public void testGetPendingPostsWhenNoneExist() {
        List<PostResponse> pendingPosts = postService.getPendingPosts();

        assertNotNull(pendingPosts);
        assertTrue(pendingPosts.isEmpty());
    }

    @Test
    public void testGetRejectedPostsWhenNoneExist() {
        List<PostResponse> rejectedPosts = postService.getRejectedPosts();

        assertNotNull(rejectedPosts);
        assertTrue(rejectedPosts.isEmpty());
    }
}