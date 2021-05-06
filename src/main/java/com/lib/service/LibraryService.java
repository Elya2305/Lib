package com.lib.service;

import com.lib.dto.lib.BookDto;
import com.lib.utils.api.PageDto;

public interface LibraryService {
    BookDto createOrUpdateBook(BookDto dto);

    PageDto<BookDto> getBooks(Integer page, Integer pageSize);

    PageDto<BookDto> searchBooks(Integer page, Integer pageSize, String search);

    BookDto getBookById(Long id);

    boolean deleteBook(Long id);
}
