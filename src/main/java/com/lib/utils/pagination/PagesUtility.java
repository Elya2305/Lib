package com.lib.utils.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static java.util.Objects.nonNull;

public class PagesUtility {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer DEFAULT_PAGE = 0;

    public static Pageable createPageableUnsorted(Integer page, Integer pageSize) {
        return PageRequest.of(nonNull(page) ? page : DEFAULT_PAGE,
                nonNull(pageSize) ? pageSize : DEFAULT_PAGE_SIZE);
    }

    public static Pageable createSortPageRequest(Integer page, Integer pageSize, SortOrder asc, String... fields) {
        return PageRequest.of(nonNull(page) ? page : DEFAULT_PAGE,
                nonNull(pageSize) ? pageSize : DEFAULT_PAGE_SIZE,
                asc == SortOrder.ASC ? Sort.by(fields).ascending() : Sort.by(fields).descending());
    }

    public enum SortOrder {
        ASC, DESC
    }
}
