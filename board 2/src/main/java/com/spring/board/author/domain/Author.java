package com.spring.board.author.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Entity

@NoArgsConstructor//jpa에서 데이터를 조립할 때 기본생성자를 만들어 기본생성자에 setter가 없다하더라도 reflection 기술을 이용해서 .. 뭐라고?
@Builder
@AllArgsConstructor
//Builder어노테이션을 통해 빌터페턴으로 객체생성
//매개변수의 세팅순서, 매개변수의 개수등을 유연하게 세팅할 수 있다.
//위와같이 모든 매개변수가 있는 생성자를 생성하는 어노테이션과 Builder를 클래스에 붙여
//모든 매개변수가 있는 생성자 위에 Builder어노테이션을 붙인것과 같은 효과가 있음.
public class Author {

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




    public Author(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }
    public void updateMember(String name, String password) {
        this.name = name;
        this.password = password;
    }


}
