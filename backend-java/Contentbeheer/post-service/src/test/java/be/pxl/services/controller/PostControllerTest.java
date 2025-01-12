package be.pxl.services.controller;

import be.pxl.services.domain.Post;
import be.pxl.services.domain.enums.PostStatus;
import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.ReviewRequest;
import be.pxl.services.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:8.3");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void clearRepository() {
        postRepository.deleteAll();
    }

    @Test
    public void testCreatePostAsEditor() throws Exception {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .build();

        String postString = objectMapper.writeValueAsString(post);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts").header("Role", "editor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postString))
                .andExpect(status().isCreated());

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    public void testCreatePostAsNonEditor() throws Exception {
        PostRequest postRequest = PostRequest.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .build();

        String postRequestString = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts").header("Role", "user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestString))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testApprovePostAsEditor() throws Exception {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/" + post.getId() + "/approve").header("Role", "editor"))
                .andExpect(status().isOk());

        Post approvedPost = postRepository.findById(post.getId()).orElseThrow();
        assertEquals(PostStatus.APPROVED, approvedPost.getStatus());
    }

    @Test
    public void testRejectPostAsEditor() throws Exception {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        ReviewRequest reviewRequest = ReviewRequest.builder()
                .author("Test reviewer")
                .comment("Test comment")
                .build();
        String reviewRequestString = objectMapper.writeValueAsString(reviewRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/" + post.getId() + "/reject").header("Role", "editor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewRequestString))
                .andExpect(status().isOk());

        Post rejectedPost = postRepository.findById(post.getId()).orElseThrow();
        assertEquals(PostStatus.REJECTED, rejectedPost.getStatus());
    }

    @Test
    public void testGetPublishedPosts() throws Exception {
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .author("Test author")
                .status(PostStatus.APPROVED)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSavePostAsConceptAsEditor() throws Exception {
        PostRequest postRequest = PostRequest.builder()
                .title("Concept title")
                .content("Concept content")
                .author("Concept author")
                .build();

        String postRequestString = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/concepts/add").header("Role", "editor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestString))
                .andExpect(status().isCreated());

        assertEquals(1, postRepository.findAll().size());
    }

    @Test
    public void testSavePostAsConceptAsNonEditor() throws Exception {
        PostRequest postRequest = PostRequest.builder()
                .title("Concept title")
                .content("Concept content")
                .author("Concept author")
                .build();

        String postRequestString = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/concepts/add").header("Role", "user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestString))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetConceptPosts() throws Exception {
        Post post = Post.builder()
                .title("Concept title")
                .content("Concept content")
                .author("Concept author")
                .status(PostStatus.CONCEPT)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/concepts"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPendingPosts() throws Exception {
        Post post = Post.builder()
                .title("Pending title")
                .content("Pending content")
                .author("Pending author")
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/pending"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRejectedPosts() throws Exception {
        Post post = Post.builder()
                .title("Rejected title")
                .content("Rejected content")
                .author("Rejected author")
                .status(PostStatus.REJECTED)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/rejected"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllPostsAsEditor() throws Exception {
        Post post = Post.builder()
                .title("All title")
                .content("All content")
                .author("All author")
                .status(PostStatus.APPROVED)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/all").header("Role", "editor"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllPostsAsNonEditor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/all").header("Role", "user"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdatePostAsEditor() throws Exception {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostRequest postRequest = PostRequest.builder()
                .title("New title")
                .content("New content")
                .author("New author")
                .build();

        String postRequestString = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/" + post.getId() + "/edit").header("Role", "editor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestString))
                .andExpect(status().isOk());

        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        assertEquals("New title", updatedPost.getTitle());
        assertEquals("New content", updatedPost.getContent());
        assertEquals("New author", updatedPost.getAuthor());
    }

    @Test
    public void testUpdatePostAsNonEditor() throws Exception {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .status(PostStatus.PENDING)
                .build();
        postRepository.save(post);

        PostRequest postRequest = PostRequest.builder()
                .title("New title")
                .content("New content")
                .author("New author")
                .build();
        String postRequestString = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/" + post.getId() + "/edit").header("Role", "user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postRequestString))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdatePostToPendingAsEditor() throws Exception {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .status(PostStatus.CONCEPT)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/" + post.getId() + "/pending").header("Role", "editor"))
                .andExpect(status().isOk());

        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        assertEquals(PostStatus.PENDING, updatedPost.getStatus());
    }

    @Test
    public void testUpdatePostToPendingAsNonEditor() throws Exception {
        Post post = Post.builder()
                .title("Old title")
                .content("Old content")
                .author("Old author")
                .status(PostStatus.CONCEPT)
                .build();
        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/" + post.getId() + "/pending").header("Role", "user"))
                .andExpect(status().isForbidden());
    }
}
