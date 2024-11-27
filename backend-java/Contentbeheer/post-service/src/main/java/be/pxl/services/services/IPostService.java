package be.pxl.services.services;

import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;

import java.util.List;

public interface IPostService {
    Long createPost(PostRequest postRequest);
    void deletePost();
    void updatePost();
    List<PostResponse> getPosts();
    void savePostAsConcept();
}
