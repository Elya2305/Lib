package com.lib.utils.client;

import com.lib.dto.web.book.google_response.BookPageResponse;
import com.lib.dto.web.book.google_response.BookResponse;
import com.lib.utils.pagination.FilterDto;

public interface GoogleBookClient {
    BookPageResponse searchBooks(String search, FilterDto filter);

    BookPageResponse searchBooks(String search);

    BookResponse searchBook(String selfLink);
}
