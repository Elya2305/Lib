package com.lib.dto.web.book.google_response;

import lombok.Data;

import java.util.List;

@Data
public class BookInfoResponse {
    private String title;
    private List<String> authors;
    private String description;
    private List<String> categories;
    private ImageDto imageLinks;
    private Integer pageCount;
    private String publishedDate;
}
