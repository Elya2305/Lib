package com.lib.dto.web.book.google_response;

import lombok.Data;

import java.util.List;

@Data
public class BookPageResponse {
    private Long totalItems;
    private List<BookResponse> items;
}
