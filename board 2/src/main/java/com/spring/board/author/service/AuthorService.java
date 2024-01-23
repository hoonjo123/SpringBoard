package com.spring.board.author.service;

import com.spring.board.author.domain.Author;
import com.spring.board.author.domain.Role;
import com.spring.board.author.dto.AuthorDetailResDto;
import com.spring.board.author.dto.AuthorListResDto;
import com.spring.board.author.dto.AuthorSaveReqDto;
import com.spring.board.author.dto.AuthorUpdateReqDto;
import com.spring.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired //생성자가 하나일때는 Autowired 생략가능하나.. 그냥 붙인다.
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void save(AuthorSaveReqDto authorSaveReqDto) {
        Role role = null;
        if (authorSaveReqDto.getRole() == null || authorSaveReqDto.getRole().equals("user")) {
            role = Role.USER;
        } else {
            role = Role.ADMIN;
        }
        //일반 생성자 방식
//        Author author = new Author(
//                authorSaveReqDto.getName(),
//                authorSaveReqDto.getEmail(),
//                authorSaveReqDto.getPassword(),
//                role);
        //빌더패턴
        Author author = Author.builder()
                .email(authorSaveReqDto.getEmail())
                .name(authorSaveReqDto.getName())
                .password(authorSaveReqDto.getPassword())
                .build();
        authorRepository.save(author);

    }

    public List<AuthorListResDto> findAll() {
        List<Author> Authors = authorRepository.findAll();
        List<AuthorListResDto> AuthorListResDtos = new ArrayList<>();
        for (Author author : Authors) {
            AuthorListResDto authorListResDtos = new AuthorListResDto();
            authorListResDtos.setId(author.getId());
            authorListResDtos.setName(author.getName());
            authorListResDtos.setEmail(author.getEmail());
            AuthorListResDtos.add(authorListResDtos);
        }
        return AuthorListResDtos;
    }

    public AuthorDetailResDto findById(Long id) throws EntityNotFoundException {
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String role = null;
        if (author.getRole() == null || author.getRole().equals(Role.USER)) {
            role = "일반유저";
        } else {
            role = "관리자";
        }

        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
        authorDetailResDto.setId(author.getId());
        authorDetailResDto.setName(author.getName());
        authorDetailResDto.setEmail(author.getEmail());
        authorDetailResDto.setPassword(author.getPassword());
        authorDetailResDto.setRole(role);
        authorDetailResDto.setCreatedTime(author.getCreatedTime());
        return authorDetailResDto;

    }

    // ... 기존 코드 ...

    public void update(AuthorUpdateReqDto authorUpdateReqDto) {
        Author author = authorRepository.findById(authorUpdateReqDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorUpdateReqDto.getId()));

        author.setName(authorUpdateReqDto.getName());
        author.setPassword(authorUpdateReqDto.getPassword());
        // 기타 필요한 필드 업데이트
        authorRepository.save(author);
    }


    public void delete(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            authorRepository.delete(author);
        }
    }
}
