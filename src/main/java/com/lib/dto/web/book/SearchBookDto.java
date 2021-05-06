package com.lib.dto.web.book;

import lombok.Data;

import java.util.List;

@Data
public class SearchBookDto {
    private String title;
    private List<String> authors;
    private String description;
    private List<String> categories;
    private String shortDescription;
    private String selfLink;
    private String imageLink;
}
