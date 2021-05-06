package com.lib.service.impl;

import com.lib.dto.lib.BookDetailedDto;
import com.lib.dto.web.book.SearchBookDto;
import com.lib.dto.web.book.google_response.BookPageResponse;
import com.lib.dto.web.book.google_response.BookResponse;
import com.lib.service.SearchBookService;
import com.lib.utils.api.PageDto;
import com.lib.utils.client.GoogleBookClient;
import com.lib.utils.pagination.FilterDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class SearchBookServiceImpl implements SearchBookService {
    private final GoogleBookClient bookClient;

    @Override
    public PageDto<SearchBookDto> searchBooks(String search, Integer page, Integer pageSize) {
        FilterDto filter = FilterDto.of(page, pageSize);
        BookPageResponse response = bookClient.searchBooks(search, filter);

        return PageDto.of(map(response.getItems()),
                filter.totalPages(response.getTotalItems()),
                filter.getPage());
    }

    @Override
    public BookDetailedDto getDetails(String selfLink) {
        return mapDetails(bookClient.searchBook(selfLink));
    }

    private List<SearchBookDto> map(List<BookResponse> source) {
        if (isNull(source) || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(this::map).collect(Collectors.toList());
    }

    private BookDetailedDto mapDetails(BookResponse source) {
        BookDetailedDto destination = new BookDetailedDto();
        destination.setDescription(source.getVolumeInfo().getDescription());
        destination.setPageCount(source.getVolumeInfo().getPageCount());
        destination.setBuyLink(source.getSaleInfo().getBuyLink());
        destination.setPublishedDate(source.getVolumeInfo().getPublishedDate());
        return destination;
    }

    private SearchBookDto map(BookResponse source) {
        SearchBookDto destination = new SearchBookDto();
        destination.setAuthors(source.getVolumeInfo().getAuthors());
        destination.setDescription(source.getVolumeInfo().getDescription());
        destination.setTitle(source.getVolumeInfo().getTitle());
        destination.setShortDescription(isNull(source.getSearchInfo()) ? null : source.getSearchInfo().getTextSnippet());
        destination.setCategories(source.getVolumeInfo().getCategories());
        destination.setSelfLink(source.getSelfLink());
        if (nonNull(source.getVolumeInfo().getImageLinks())) {
            destination.setImageLink(source.getVolumeInfo().getImageLinks().getSmallThumbnail());
        }
        return destination;
    }
}
