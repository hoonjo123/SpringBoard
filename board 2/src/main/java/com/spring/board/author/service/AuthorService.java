package com.spring.board.author.service;

import com.spring.board.author.domain.Author;
import com.spring.board.author.domain.Role;
import com.spring.board.author.dto.AuthorDetailResDto;
import com.spring.board.author.dto.AuthorListResDto;
import com.spring.board.author.dto.AuthorSaveReqDto;
import com.spring.board.author.dto.AuthorUpdateReqDto;
import com.spring.board.author.repository.AuthorRepository;
import com.spring.board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) throws IllegalArgumentException, IllegalAccessException {
        // 이메일로 기존 저자 조회
        Optional<Author> existingAuthor = authorRepository.findByEmail(authorSaveReqDto.getEmail());
        if(existingAuthor.isPresent()) {
            throw new IllegalAccessException("중복이메일입니다.");
        }

        // Role 설정
        Role role;
        if(authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("user")){
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }

        // 빌더 패턴을 사용한 Author 객체 생성
        Author newAuthor = Author.builder()
                .email(authorSaveReqDto.getEmail())
                .name(authorSaveReqDto.getName())
                .password(authorSaveReqDto.getPassword())
                .role(role) // role을 설정
                .build();

        // Author 객체 저장
        authorRepository.save(newAuthor);
    }


    public List<AuthorListResDto> findAll() {
        List<Author> Authors = authorRepository.findAll();
        List<AuthorListResDto> AuthorListResDtos = new ArrayList<>();
        for(Author author : Authors){
            AuthorListResDto authorListResDto = new AuthorListResDto();
            authorListResDto.setId(author.getId());
            authorListResDto.setName(author.getName());
            authorListResDto.setEmail(author.getEmail());
            AuthorListResDtos.add(authorListResDto);
        }
        return AuthorListResDtos;
//        return authorRepository.findAll().stream().map(author -> new AuthorListResDto(author.getId(), author.getName(), author.getEmail())).toList();
    }

    public Author findById(Long id) throws EntityNotFoundException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));
        return author;
    }

    public AuthorDetailResDto findAuthorDetail(Long id) throws EntityNotFoundException {
        Author author = this.findById(id);
        String role = null;
        if(author.getRole() == null || author.getRole().equals(Role.USER)){
            role = "일반유저";
        }else{
            role = "관리자";
        }
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
        authorDetailResDto.setId(author.getId());
        authorDetailResDto.setName(author.getName());
        authorDetailResDto.setEmail(author.getEmail());
        authorDetailResDto.setPassword(author.getPassword());
        authorDetailResDto.setRole(role);
        authorDetailResDto.setCounts(author.getPosts().size());
        authorDetailResDto.setCreatedTime(author.getCreatedTime());
        return authorDetailResDto;
    }

    public void update(Long id, AuthorUpdateReqDto authorUpdateReqDto) throws EntityNotFoundException {
        Author author = this.findById(id);
        author.updateAuthor(authorUpdateReqDto.getName(), authorUpdateReqDto.getPassword());
        //명시적으로 save를 하지 않더라도, JPA의 영속성 컨텍스트를 통해
        //객체에 변경이 감지(dirty checking)되면, 트랜잭션이 완료되는 시점에 save 동작
        //authorRepository.save(author);
    }

    public void delete(Long id) throws EntityNotFoundException {
        Author author = this.findById(id);
        authorRepository.delete(author);
//        authorRepository.deleteById(id);
    }

}