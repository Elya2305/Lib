package com.lib.utils.api;

public class Responses {
    public static <T> ApiResponse<T> okResponse(T body) {
        return ofStatus(ApiResponse.Status.OK, null, body);
    }

    public static <T> ApiResponse<T> errorResponse(String msg) {
        return ofStatus(ApiResponse.Status.ERROR, msg, null);
    }

    public static <T> ApiResponse<T> warnResponse(String msg) {
        return ofStatus(ApiResponse.Status.WARN, msg, null);
    }

    private static <T> ApiResponse<T> ofStatus(ApiResponse.Status status, String msg, T body) {
        ApiResponse<T> apiResponse = new ApiResponse<>(status);
        apiResponse.setMessage(msg);
        apiResponse.setBody(body);
        return apiResponse;
    }

    public static <T> ApiPageResponse<T> okPageResponse(PageDto<T> page) {
        return new ApiPageResponse<>(
                ApiResponse.Status.OK,
                page.getObjectList(),
                page.getTotalPages(),
                page.getPage()
        );
    }
}
