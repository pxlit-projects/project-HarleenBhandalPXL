package be.pxl.services.client;

import be.pxl.services.domain.dto.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "post-service")
public interface PostClient {
    @GetMapping("/api/posts/{id}/approve")
    PostResponse approvePost(@PathVariable("id") long id, @RequestHeader("Role") String role);
    @GetMapping("/api/posts/{id}/reject")
    PostResponse rejectPost(@PathVariable("id") long id, @RequestHeader("Role") String role);
}
