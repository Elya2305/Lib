package com.lib.dto.lib;

import lombok.Data;

@Data
public class ClassificationDto {
    private Long id;
    private String title;
    private String icon;
    private ClassificationType type;
}
