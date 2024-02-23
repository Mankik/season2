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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_type", nullable = false, updatable = false)
    private ContentType contentType;

    @Column(name = "content_data", length = 10000)
    private String data;

    //AI Analyst
    private String title;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(length = 1000)
    private String summary;

    private Integer charge;

    private String location;

    @Column(name = "bank_account")
    private String bankAccount;

    //CRUD Date
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "content_cre_date", nullable = false, updatable = false)
    private Date creDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "content_upd_date", nullable = false)
    private Date updDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "content_del_date")
    private Date delDate;

    @PreRemove
    private void preRemove() {
        this.delDate = new Date();
    }
}
