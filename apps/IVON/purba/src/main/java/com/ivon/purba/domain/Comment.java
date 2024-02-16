package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "comment_data", length = 10000)
    private String commentData;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    private java.time.LocalDateTime creDate;
    private java.time.LocalDateTime updDate;
    private java.time.LocalDateTime delDate;
}
