package com.spring.homepage.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String email;
        private String password;
        private String address;

        @Enumerated(EnumType.STRING)
        private Role role; // Role은 Enum 타입

        private LocalDateTime createdTime;
        private LocalDateTime updatedTime;

        @OneToMany(mappedBy = "member") // "author"에서 "member"로 변경
        private List<Ordering> orderings;

        // Getters, Setters, Constructors
}
