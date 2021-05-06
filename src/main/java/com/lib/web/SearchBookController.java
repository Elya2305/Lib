package com.lib.web;

import com.lib.dto.lib.BookDetailedDto;
import com.lib.dto.web.book.SearchBookDto;
import com.lib.service.SearchBookService;
import com.lib.utils.api.ApiPageResponse;
import com.lib.utils.api.ApiResponse;
import com.lib.utils.api.PageDto;
import com.lib.utils.api.Responses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class SearchBookController {
    private final SearchBookService bookService;

    @GetMapping("/all")
    public ApiPageResponse<SearchBookDto> searchBook(@RequestParam String search,
                                                     @RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer pageSize) {
        log.info("Request on searching book, search - {}, page - {}, pageSize - {}", search, page, pageSize);
        PageDto<SearchBookDto> result = bookService.searchBooks(search, page, pageSize);
        log.info("Response's size on searching book - {}", result.getTotalPages());
        return Responses.okPageResponse(result);
    }

    @GetMapping
    public ApiResponse<BookDetailedDto> getDetails(@RequestParam String selfLink) {
        log.info("Request on getting extra information");
        BookDetailedDto result = bookService.getDetails(selfLink);
        log.info("Response on getting extra information - {}", result);
        return Responses.okResponse(result);
    }
}
