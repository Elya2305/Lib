package com.lib.utils.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final Status status;
    private String message;
    private T body;


    public enum Status {
        OK, ERROR, WARN
    }
}
