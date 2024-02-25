package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event extends Content {

    @ManyToOne
    @JoinColumn(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "photo_url")
    private String photoUrl;
}
