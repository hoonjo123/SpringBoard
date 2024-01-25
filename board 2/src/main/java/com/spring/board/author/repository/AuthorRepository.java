package com.spring.board.author.repository;

import com.spring.board.author.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    //findBy컬럼명A and 컬럼명B 컬럼명의 규칙으로 자동으로 where조건문을 사용한 method생성
    Optional<Author> findByEmail(String email);

}
