package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
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

    @Column(name = "easy_delete")
    @ColumnDefault("false")
    private Boolean easyDelete;

    //Photo & Event attributes & some attributes for AI Analyst
    private String title;

    @Column(name = "content_data", length = 10000)
    private String data;

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

    public static class Builder {
        private ContentType contentType;
        private EventType eventType;
        private User user;
        private String location;
        private String title;
        private String data;
        private String photoUrl;
        private Date startDate;
        private Date endDate;
        private Integer charge;
        private String bankAccount;
        private boolean easyDelete;
        private String summary;

        public Builder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder eventType(EventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Builder photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder charge(Integer charge) {
            this.charge = charge;
            return this;
        }

        public Builder bankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }

        public Builder easyDelete(boolean easyDelete) {
            this.easyDelete = easyDelete;
            return this;
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Event build() {
            Event event = new Event();
            event.setContentType(this.contentType);
            event.setEventType(this.eventType);
            event.setUser(this.user);
            event.setTitle(this.title);
            event.setData(this.data);
            event.setPhotoUrl(this.photoUrl);
            event.setStartDate(this.startDate);
            event.setEndDate(this.endDate);
            event.setCharge(this.charge);
            event.setBankAccount(this.bankAccount);
            event.setEasyDelete(this.easyDelete);
            event.setSummary(this.summary);
            return event;
        }
    }
}
