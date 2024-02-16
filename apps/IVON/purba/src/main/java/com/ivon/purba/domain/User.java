package com.ivon.purba.domain;

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
    private Integer userId;

    private String name;

    private Integer location;

    @Column(nullable = false)
    private Boolean easyDelete;

    private LocalDateTime creDate;

    private LocalDateTime updDate;

    private LocalDateTime delDate;
}
