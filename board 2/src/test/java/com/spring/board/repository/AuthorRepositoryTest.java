package com.spring.board.repository;

import com.spring.board.author.domain.Author;
import com.spring.board.author.domain.Role;
import com.spring.board.author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//DataJpaTest 어노테이션을 사용하면 매 테스트가 종료되면 자동으로 DB원상복구
//모든 스프링빈을 생성하지는 않고 DB테스트 특화 어노테이션이다.
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//replace = AutoConfigureTestDatabase.Replace.ANY; H2DB(spring 내장 엔메모리)가 기본설정
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest 어노테이션은 자동롤백 기능 지원하지 않고 별도의 롤백 코드 또는 어노테이션 필요
//@SpringBootTest 어노테이션은 실제 스프링 실행과 동일하게 스프링빈 생성 및 주입을 한다(느림)
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test") application-test.yml파일을 찾아 설정값 세팅
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;


    @Test
    public void authorSaveTest(){
        //객체를 만들어서 save -> 재조회해서 -> 만든 객체와 비교
        //준비(prepare, given)
        Author author = Author.builder()
                  .name("홍길동")
                  .email("gildong4@naver.com")
                  .password("1234")
                  .role(Role.ADMIN)
                  .build();
        //실행단계(excute/ when)
        authorRepository.save(author);
        Author authorDb = authorRepository.findByEmail("gildong4@naver.com").orElse(null);


        //검증(then)
        //Assertions 클래스의 기능을 통해 오휴의 원인파악, null처리, 가시적으로 성공/실패 여부 확인
        Assertions.assertEquals(author.getEmail(),authorDb.getEmail());
    }
}
