package com.lib.entity;

import com.lib.dto.lib.ClassificationType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "classifications")
public class Classification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String icon;

    @Enumerated(EnumType.STRING)
    private ClassificationType type;
}
