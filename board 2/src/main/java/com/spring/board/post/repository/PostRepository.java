package com.spring.board.post.repository;

import com.spring.board.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //pageable객체안에는 pageNumber(page=1), page마다개수(size=10), 정렬(sort=createdTime,desc)이 들어가있다.
    Page<Post> findAll(Pageable pageable);
//    List<Post> findAllByOrderByCreatedTimeDesc();
    Page<Post> findByAppointment(String appointment, Pageable pageable);

    // SELECT P.* from post p left join author a on p.author_id = a.id (SQL쿼리)
    //jpql의 join문은 author객체를 통해 post를 스크리닝하고 싶은 상황에 적합
    //page객체는 List<Post> + 해당Page의 각종정보가 들어있습니다.
//    @Query("select p from Post p left join p.author") //jpql문
//    List<Post> findAlljoin();
    // SELECT P.*, a.* from post p left join author a on p.author_id = a.id (SQL쿼리)

    @Query("select p from Post p left join fetch p.author") //jpql문
    List<Post> findAllFetchJoin();
}