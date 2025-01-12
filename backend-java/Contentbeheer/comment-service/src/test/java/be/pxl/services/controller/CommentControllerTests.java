package be.pxl.services.controller;

import be.pxl.services.domain.Comment;
import be.pxl.services.domain.dto.CommentRequest;
import be.pxl.services.domain.dto.CommentResponse;
import be.pxl.services.domain.dto.CommentUpdateRequest;
import be.pxl.services.repository.CommentRepository;
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
public class CommentControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentRepository commentRepository;

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
        commentRepository.deleteAll();
    }

    @Test
    public void testCreateComment() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder()
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        String commentRequestString = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentRequestString))
                .andExpect(status().isCreated());

        assertEquals(1, commentRepository.findAll().size());
    }

    @Test
    public void testGetCommentsByPostId() throws Exception {
        Comment comment = Comment.builder()
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        commentRepository.save(comment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/post/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateComment() throws Exception {
        Comment comment = Comment.builder()
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        commentRepository.save(comment);

        CommentUpdateRequest updateRequest = CommentUpdateRequest.builder()
                .content("Updated content")
                .build();

        String updateRequestString = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/comments/" + comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestString))
                .andExpect(status().isOk());

        Comment updatedComment = commentRepository.findById(comment.getId()).orElseThrow();
        assertEquals("Updated content", updatedComment.getContent());
    }

    @Test
    public void testGetCommentById() throws Exception {
        Comment comment = Comment.builder()
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        commentRepository.save(comment);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/comments/" + comment.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteComment() throws Exception {
        Comment comment = Comment.builder()
                .postId(1L)
                .content("Test content")
                .userName("Test user")
                .build();

        commentRepository.save(comment);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comments/" + comment.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, commentRepository.findAll().size());
    }
}