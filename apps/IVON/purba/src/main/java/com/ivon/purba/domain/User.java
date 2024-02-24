package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private Long id;

    private String name;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    private Integer location = 0;

    @Column(name = "verification_code", unique = true)
    private String verificationCode;

    @Column(name = "conde_cre_time", unique = true)
    private LocalDateTime codeCreTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date delDate;

    @PreRemove
    private void preRemove() {
        this.delDate = new Date();
    }
}
