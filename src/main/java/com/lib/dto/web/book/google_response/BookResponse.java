package com.lib.dto.web.book.google_response;

import lombok.Data;

@Data
public class BookResponse {
    private String selfLink;
    private BookInfoResponse volumeInfo;
    private SearchInfoDto searchInfo;
    private SaleInfoDto saleInfo;

    public static BookResponse empty() {
        return new BookResponse();
    }
}
