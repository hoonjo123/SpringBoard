package com.spring.board.post.domain;

import com.spring.board.author.domain.Author;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class Post {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 3000)
    private String contents;


    private String appointment;
    private LocalDateTime appointmentTime;


    @CreationTimestamp
    // 개발자가 DB를 바꾸는 게 risky한 것. 프로그램적으로 다루는 것이 좋다.
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    LocalDateTime updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;


    //    public Post(String title, String contents){
//        this.title = title;
//        this.contents = contents;
//    }
    public void updatePost(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
    public void updateAppointment(String appointment) {
        this.appointment = appointment;
    }
}