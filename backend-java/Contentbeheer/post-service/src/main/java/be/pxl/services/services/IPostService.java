package be.pxl.services.services;

import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;

import java.util.List;

public interface IPostService {
    Long createPost(PostRequest postRequest);
    void deletePost();
    PostResponse updatePost(long id, PostRequest postRequest);
    List<PostResponse> getPublishedPosts();
    List<PostResponse> getConceptPosts();
    List<PostResponse> getAllPosts();
    Long savePostAsConcept(PostRequest postRequest);
    PostResponse getPostById(long id);
}
