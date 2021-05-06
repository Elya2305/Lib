package com.lib.utils.api;

import lombok.Data;

import java.util.List;


@Data
public class ApiPageResponse<T> {
    private ApiResponse.Status status;
    private Integer totalPages;
    private Integer page;
    private List<T> objects;

    public ApiPageResponse(ApiResponse.Status status, List<T> objects, Integer total, Integer page) {
        this.status = status;
        this.objects = objects;
        this.totalPages = total;
        this.page = page;
    }
}
