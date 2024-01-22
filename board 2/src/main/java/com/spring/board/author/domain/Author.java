package com.spring.board.author.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

enum Role {
    ADMIN,
    USER
}
@Getter
@Entity
@NoArgsConstructor//jpa에서 데이터를 조립할 때 기본생성자를 만들어 기본생성자에 setter가 없다하더라도 reflection 기술을 이용해서 .. 뭐라고?


public class Author {

    public Author(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 20)
    private String name;

    @Setter
    @Column(nullable = false, unique = true, length = 20)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTime;

    @Enumerated(EnumType.STRING)
    private Role role;


    public Author(String name, String email, String password, LocalDateTime createdTime) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdTime = LocalDateTime.now();
    }
    public void updateMember(String name, String password) {
        this.name = name;
        this.password = password;
    }


}
