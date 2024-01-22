package com.spring.board.author.service;

import com.spring.board.author.domain.Author;
import com.spring.board.author.dto.AuthorDetailResDto;
import com.spring.board.author.dto.AuthorListResDto;
import com.spring.board.author.dto.AuthorSaveReqDto;
import com.spring.board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired //생성자가 하나일때는 Autowired 생략가능하나.. 그냥 붙인다.
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    public void save(AuthorSaveReqDto authorSaveReqDto){
        Author author = new Author(authorSaveReqDto.getName(), authorSaveReqDto.getEmail(), authorSaveReqDto.getPassword());
        authorRepository.save(author);

    }

    public List<AuthorListResDto> findAll() {
        List<Author> Authors = authorRepository.findAll();
        List<AuthorListResDto> AuthorListResDtos = new ArrayList<>();
        for (Author author : Authors) {
            AuthorListResDto authorListResDtos= new AuthorListResDto();
            authorListResDtos.setId(author.getId());
            authorListResDtos.setName(author.getName());
            authorListResDtos.setEmail(author.getEmail());
            AuthorListResDtos.add(authorListResDtos);
        }
        return AuthorListResDtos;
    }

    public AuthorDetailResDto findById(Long id) throws EntityNotFoundException {
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        AuthorDetailResDto authorDetailResDto = new AuthorDetailResDto();
        authorDetailResDto.setId(author.getId());
        authorDetailResDto.setName(author.getName());
        authorDetailResDto.setEmail(author.getEmail());
        authorDetailResDto.setPassword(author.getPassword());
        authorDetailResDto.setCreatedTime(author.getCreatedTime());
        return authorDetailResDto;

    }



}
