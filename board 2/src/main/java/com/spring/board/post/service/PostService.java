package com.spring.board.post.service;

import com.spring.board.author.domain.Author;
import com.spring.board.author.repository.AuthorRepository;
import com.spring.board.post.domain.Post;
import com.spring.board.post.dto.PostCreateReqDto;
import com.spring.board.post.dto.PostDetailResDto;
import com.spring.board.post.dto.PostListResDto;
import com.spring.board.post.dto.PostUpdateReqDto;
import com.spring.board.post.repository.PostRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Builder
public class PostService {
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;
    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostCreateReqDto postCreateReqDto) {
        Author author = authorRepository.findByEmail(postCreateReqDto.getEmail()).orElse(null);
        Post post = Post.builder()
                .title(postCreateReqDto.getTitle())
                .content(postCreateReqDto.getContents())
                .author(author) // author가 null일 수 있음
                .build();

        postRepository.save(post);
    }
    public List<PostListResDto> findAll() {
        List<Post> posts = postRepository.findAllFetchJoin();
        List<PostListResDto> postListResDtos = new ArrayList<>();
        for (Post post : posts) {
            PostListResDto postListResDto = new PostListResDto();
            postListResDto.setId(post.getId());
            postListResDto.setTitle(post.getTitle());
            if (post.getAuthor() != null) {
                postListResDto.setAuthorEmail(post.getAuthor().getEmail());
            } else {
                postListResDto.setAuthorEmail("이메일 없음"); // 이 부분이 중요합니다.
            }
            postListResDtos.add(postListResDto);
        }
        return postListResDtos;
    }


    public Post FindById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Post가 없습니다."));
        return post;
    }

    public PostDetailResDto findPostDetail(Long id) throws EntityNotFoundException {
        Post post = this.FindById(id);
        PostDetailResDto postDetailResDto = new PostDetailResDto();
        postDetailResDto.setId(post.getId());
        postDetailResDto.setTitle(post.getTitle());
        postDetailResDto.setContents(post.getContents());
        postDetailResDto.setCreatedTime(post.getCreatedTime());
        if (post.getAuthor() != null) {
            postDetailResDto.setAuthorEmail(post.getAuthor().getEmail());
        } else {
            postDetailResDto.setAuthorEmail("이메일 없음");
        }
        return postDetailResDto;
    }

    public void update(Long id, PostUpdateReqDto postUpdateReqDto){
        Post post = this.FindById(id);
        post.updatePost(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
    }

    public void delete(Long id){
        Post post = this.FindById(id);
        postRepository.delete(post);
    }

    public void createPostWithAuthor(String email, PostCreateReqDto postCreateReqDto) {
        // Author 객체를 이메일을 통해 찾기
        Author author = authorRepository.findByEmail(postCreateReqDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // Post 객체 빌드
        Post post = Post.builder()
                .title(postCreateReqDto.getTitle())
                .content(postCreateReqDto.getContents())
                .author(author) // Author 객체 설정
                .build();

        // Post 객체 저장
        postRepository.save(post);
    }

}