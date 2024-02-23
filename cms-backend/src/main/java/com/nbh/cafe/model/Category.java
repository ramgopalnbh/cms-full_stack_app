package com.nbh.cafe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Category implements Serializable {
    private static final Long serialId = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] image;
}
