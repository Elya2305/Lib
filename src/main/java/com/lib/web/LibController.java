package com.lib.web;

import com.lib.dto.lib.BookDto;
import com.lib.service.LibraryService;
import com.lib.utils.api.ApiPageResponse;
import com.lib.utils.api.ApiResponse;
import com.lib.utils.api.PageDto;
import com.lib.utils.api.Responses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/lib")
public class LibController {
    private final LibraryService libraryService;

    @GetMapping
    public ApiPageResponse<BookDto> getBooks(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer pageSize) {
        log.info("Request on getting books, page - {}, pageSize - {}", page, pageSize);
        PageDto<BookDto> result = libraryService.getBooks(page, pageSize);
        log.info("Response's size on getting books, page - {}, pageSize - {}, size - {}", page, pageSize, result.getTotalPages());
        return Responses.okPageResponse(result);
    }

    @GetMapping("/{id}")
    public ApiResponse<BookDto> getBookById(@PathVariable String id) {
        log.info("Request on getting book by id, {}", id);
        BookDto result = libraryService.getBookById(Long.parseLong(id));
        log.info("Response on getting book - {}", result);
        return Responses.okResponse(result);
    }

    @PostMapping
    public ApiResponse<BookDto> createBook(@RequestBody BookDto book) {
        log.info("Request on creating book, {}", book);
        BookDto result = libraryService.createOrUpdateBook(book);
        log.info("Response on creating book, {}", result);
        return Responses.okResponse(result);
    }

    @PutMapping
    public ApiResponse<BookDto> updateBook(@RequestBody BookDto book) {
        log.info("Request on updating book, {}", book);
        BookDto result = libraryService.createOrUpdateBook(book);
        log.info("Response on updating book, {}", book);
        return Responses.okResponse(result);
    }

    @DeleteMapping
    public ApiResponse<Boolean> deleteBook(@RequestParam Long id) {
        log.info("Request on deleting book, id - {}", id);
        boolean result = libraryService.deleteBook(id);
        log.info("Response on deleting book - {}", result);
        return Responses.okResponse(result);
    }

    @GetMapping("/search")
    public ApiPageResponse<BookDto> getBooks(@RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String search) {
        log.info("Request on getting books, page - {}, pageSize - {}, search - {}", page, pageSize, search);
        PageDto<BookDto> result = libraryService.searchBooks(page, pageSize, search);
        log.info("Response's size on getting books - {}", result.getTotalPages());
        return Responses.okPageResponse(result);
    }
}
