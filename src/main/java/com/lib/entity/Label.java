package com.lib.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "labels")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String color;
}
