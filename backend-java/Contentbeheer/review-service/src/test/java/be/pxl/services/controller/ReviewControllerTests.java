package be.pxl.services.controller;

import be.pxl.services.client.PostClient;
import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import be.pxl.services.services.IReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.Charset;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ReviewControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostClient postClient;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:8.3");

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApprovePostAsEditor() throws Exception {
        mockMvc.perform(get("/api/reviews/post/1/approve").header("Role", "editor"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRejectPostAsEditor() throws Exception {
        ReviewRequest reviewRequest = ReviewRequest.builder()
                .author("Test reviewer")
                .comment("Test comment")
                .build();
        String reviewRequestString = objectMapper.writeValueAsString(reviewRequest);

        mockMvc.perform(put("/api/reviews/post/1/reject").header("Role", "editor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewRequestString))
                .andExpect(status().isOk());
    }
}