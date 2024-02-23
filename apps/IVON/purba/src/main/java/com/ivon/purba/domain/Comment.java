package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "comment_data", length = 10000)
    private String data;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_cre_date", nullable = false, updatable = false)
    private Date creDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "content_upd_date", nullable = false)
    private Date updDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_del_date")
    private Date delDate;
    @PreRemove
    private void preRemove() {
        this.delDate = new Date();
    }
}
