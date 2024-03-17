package com.ivon.purba.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo extends Content {

    @Column(name = "photo_url", nullable = false, length = 200)
    private String url;
}
