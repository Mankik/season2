package com.ivon.purba.domain;

import com.ivon.purba.repository.UserRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    private String name;

    private String phoneNumber;

    private Integer location = 0;

    private LocalDateTime creDate;

    private LocalDateTime updDate;

    private LocalDateTime delDate;

    public User() {
        this.creDate = LocalDateTime.now();
    }
    public Long getId() {
        return (long) userId;
    }
}
