package be.pxl.services.services;

import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;
import be.pxl.services.dto.RejectedPostResponse;
import be.pxl.services.dto.ReviewRequest;

import java.util.List;

public interface IPostService {
    Long createPost(PostRequest postRequest);
    PostResponse updatePost(long id, PostRequest postRequest);
    List<PostResponse> getPublishedPosts();
    List<PostResponse> getConceptPosts();
    List<PostResponse> getPendingPosts();
    List<PostResponse> getAllPosts();
    List<PostResponse> getRejectedPosts();
    Long savePostAsConcept(PostRequest postRequest);
    PostResponse updatePostToPending(long id);
    PostResponse getPostById(long id);
    PostResponse approvePost(long id);
    RejectedPostResponse rejectPost(long id, ReviewRequest comment);
}
