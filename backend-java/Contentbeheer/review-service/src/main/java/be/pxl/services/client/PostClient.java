package be.pxl.services.client;

import be.pxl.services.domain.dto.PostResponse;
import be.pxl.services.domain.dto.RejectedPostResponse;
import be.pxl.services.domain.dto.ReviewRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "post-service")
public interface PostClient {
    @GetMapping("/api/posts/{id}/approve")
    PostResponse approvePost(@PathVariable("id") long id, @RequestHeader("Role") String role);
    @PutMapping("/api/posts/{id}/reject")
    RejectedPostResponse rejectPost(@PathVariable("id") long id, @RequestHeader("Role") String role, @RequestBody ReviewRequest reviewRequest);
}
