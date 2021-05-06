package com.lib.dto.lib;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private Long id;
    private String title;
    private List<ClassificationDto> classification;
    private List<LabelDto> labels;
    private List<String> authors;
    private String selfLink;
    private String imageLink;
}
