package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;
import be.pxl.services.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;

    @Override
    public Long createPost(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .date(LocalDateTime.now())
                .build();

        this.postRepository.save(post);
        return post.getId();
    }

    @Override
    public void deletePost() {

    }

    @Override
    public void updatePost() {

    }

    @Override
    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostResponse.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .creationDate(post.getDate())
                        .build()).toList();
    }

    @Override
    public void savePostAsConcept() {

    }
}
