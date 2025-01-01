package be.pxl.services.services;

import be.pxl.services.domain.Post;
import be.pxl.services.dto.PostRequest;
import be.pxl.services.dto.PostResponse;
import be.pxl.services.exception.NotFoundException;
import be.pxl.services.repository.PostRepository;
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
                .creationDate(LocalDateTime.now())
                .isConcept(false)
                .build();

        this.postRepository.save(post);
        return post.getId();
    }

    @Override
    public void deletePost() {

    }

    @Override
    public PostResponse updatePost(long id, PostRequest postRequest) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());

        postRepository.save(post);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .isConcept(post.isConcept())
                .creationDate(post.getCreationDate())
                .build();
    }

    @Override
    public List<PostResponse> getPublishedPosts() {
        return postRepository.findAll().stream()
                .filter(post -> !post.isConcept())
                .map(post -> PostResponse.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .isConcept(post.isConcept())
                        .creationDate(post.getCreationDate())
                        .build()).toList();
    }

    @Override
    public List<PostResponse> getConceptPosts() {
        return postRepository.findAll().stream()
                .filter(Post::isConcept)
                .map(post -> PostResponse.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .isConcept(post.isConcept())
                        .creationDate(post.getCreationDate())
                        .build()).toList();
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostResponse.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getAuthor())
                        .creationDate(post.getCreationDate())
                        .isConcept(post.isConcept())
                        .build()).toList();
    }

    @Override
    public Long savePostAsConcept(PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .creationDate(LocalDateTime.now())
                .isConcept(true)
                .build();

        this.postRepository.save(post);
        return post.getId();
    }
}
