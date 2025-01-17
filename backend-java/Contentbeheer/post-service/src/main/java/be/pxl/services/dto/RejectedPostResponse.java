package be.pxl.services.dto;

import be.pxl.services.domain.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RejectedPostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime creationDate;
    private PostStatus status;
    private String rejectionReason;
    private String rejectedBy;
}
