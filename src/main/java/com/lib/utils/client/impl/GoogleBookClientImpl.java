package com.lib.utils.client.impl;

import com.lib.dto.web.book.google_response.BookPageResponse;
import com.lib.dto.web.book.google_response.BookResponse;
import com.lib.utils.AbstractHttpClient;
import com.lib.utils.client.GoogleBookClient;
import com.lib.utils.pagination.FilterDto;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Component
public class GoogleBookClientImpl extends AbstractHttpClient implements GoogleBookClient {
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=%s";

    public GoogleBookClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public BookPageResponse searchBooks(String search, FilterDto filter) {
        return get(url(search, filter.getStartIndex(), filter.getMaxResult()), BookPageResponse.class);
    }

    /**
     * default startIndex = 0, pageSize = 10
     */
    @Override
    public BookPageResponse searchBooks(String search) {
        return get(url(search), BookPageResponse.class);
    }

    @Override
    public BookResponse searchBook(String selfLink) {
        return isNull(selfLink) ? BookResponse.empty()
                : get(selfLink, BookResponse.class);
    }

    private String url(String search) {
        return String.format(BASE_URL, search);
    }

    private String url(String search, int startIndex, int maxResult) {
        return url(search) + "&startIndex=" + startIndex + "&maxResults=" + maxResult;
    }

    @Override
    public HttpHeaders headers() {
        return new HttpHeaders();
    }
}
