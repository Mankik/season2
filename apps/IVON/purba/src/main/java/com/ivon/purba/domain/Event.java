package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event extends Content {

    @Column(name = "event_type")
    private Integer eventType;

    private Integer location;
}
