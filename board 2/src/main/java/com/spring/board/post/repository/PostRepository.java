package com.spring.board.post.repository;

import com.spring.board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedTimeDesc();

    // SELECT P.* from post p left join author a on p.author_id = a.id (SQL쿼리)
    //jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    @Query("select p from Post p left join p.author") //jpql문
    List<Post> findAlljoin();
    // SELECT P.*, a.* from post p left join author a on p.author_id = a.id (SQL쿼리)

    @Query("select p from Post p left join fetch p.author") //jpql문
    List<Post> findAllFetchJoin();
}