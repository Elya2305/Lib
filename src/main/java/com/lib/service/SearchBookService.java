package com.lib.service;

import com.lib.dto.lib.BookDetailedDto;
import com.lib.dto.web.book.SearchBookDto;
import com.lib.utils.api.PageDto;

public interface SearchBookService {
    PageDto<SearchBookDto> searchBooks(String search, Integer page, Integer pageSize);

    BookDetailedDto getDetails(String selfLink);
}
