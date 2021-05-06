package com.lib.utils.pagination;

import lombok.Data;

import static java.util.Objects.isNull;

@Data
public class FilterDto {
    private Integer startIndex;
    private Integer maxResult;
    private Integer page;

    private final static Integer DEFAULT_START_INDEX = 0;
    private final static Integer DEFAULT_MAX_RESULT = 10;

    public static FilterDto of(Integer page, Integer pageSize) {
        FilterDto filter = new FilterDto();
        filter.setPage(isNull(page) ? DEFAULT_START_INDEX : page);
        filter.setMaxResult(isNull(pageSize) ? DEFAULT_MAX_RESULT : pageSize);
        filter.setStartIndex(filter.getPage() * filter.getMaxResult());
        return filter;
    }

    public Integer totalPages(Long totalElem) {
        return (int) Math.ceil(Double.valueOf(totalElem) / maxResult);
    }
}
