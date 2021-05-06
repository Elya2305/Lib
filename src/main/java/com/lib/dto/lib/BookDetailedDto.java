package com.lib.dto.lib;

import lombok.Data;

@Data
public class BookDetailedDto {
    private String description;
    private String publishedDate;
    private Integer pageCount;
    private String buyLink;
}
