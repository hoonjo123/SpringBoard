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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void save(PostCreateReqDto postCreateReqDto) throws IllegalArgumentException{
        Author author = authorRepository.findByEmail(postCreateReqDto.getEmail()).orElse(null);
        LocalDateTime localDateTime = null;
        String appointment = null;
        if (postCreateReqDto.getAppointment().equals("Y") &&  //YES인 경우에만 DB에 Y, NO이면 null 세팅
                !postCreateReqDto.getAppointmentTime().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(postCreateReqDto.getAppointmentTime() , dateTimeFormatter);
            LocalDateTime now = LocalDateTime.now();
            if(localDateTime.isBefore(now)){
                throw new IllegalArgumentException("시간정보 잘못입력");
            }
            appointment = "Y";
        }
        Post post = Post.builder()
                .title(postCreateReqDto.getTitle())
                .contents(postCreateReqDto.getContents())
                .author(author)
                .appointment(appointment)
                .appointmentTime(localDateTime)
                .build();
//        더티체킹 테스트
//        author.updateAuthor("dirty checking test", "1234");
//        authorRepository.save(author);

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

    public Page<PostListResDto> findByAppointment(Pageable pageable) {
        Page<Post> posts = postRepository.findByAppointment(null, pageable);
        //Page는 스프링에서 제공해주는 일종의 wrapping객체인데 이걸 어떻게 list로 변환하는게 가능할까요?
        Page<PostListResDto> postListResDtos
                = posts.map(p ->
                new PostListResDto(
                        p.getId(),
                        p.getTitle(),
                        p.getAuthor() == null ? "이메일없음" : p.getAuthor().getEmail()
                ));
        return postListResDtos;
    }



    public Page<PostListResDto> findAllPaging(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        //Page는 스프링에서 제공해주는 일종의 wrapping객체인데 이걸 어떻게 list로 변환하는게 가능할까요?
        Page<PostListResDto> postListResDtos
                = posts.map(p ->
                new PostListResDto(
                        p.getId(),
                        p.getTitle(),
                        p.getAuthor() == null ? "이메일없음" : p.getAuthor().getEmail()
                ));
        return postListResDtos;
    }


    public Post FindById(Long id) {
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

    public void update(Long id, PostUpdateReqDto postUpdateReqDto) {
        Post post = this.FindById(id);
        post.updatePost(postUpdateReqDto.getTitle(), postUpdateReqDto.getContents());
        postRepository.save(post);
    }

    public void delete(Long id) {
        Post post = this.FindById(id);
        postRepository.delete(post);
    }

    public void createPostWithAuthor(String authorEmail, PostCreateReqDto postCreateReqDto) {
        // Author 객체를 이메일을 통해 찾기
        Author author = authorRepository.findByEmail(postCreateReqDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // Post 객체 빌드
        Post post = Post.builder()
                .title(postCreateReqDto.getTitle())
                .contents(postCreateReqDto.getContents())
                .author(author) // Author 객체 설정
                .build();

        // Post 객체 저장
        postRepository.save(post);
    }

}
