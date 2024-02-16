package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer contentType;

    private String title;

    @Column(name = "content_data", length = 10000)
    private String contentData;

    @Column(length = 1000)
    private String summary;

    private LocalDateTime creDate;

    private LocalDateTime updDate;

    private LocalDateTime delDate;
}
