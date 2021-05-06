package com.lib.web;

import com.lib.exception.CustomUserException;
import com.lib.utils.api.ApiResponse;
import com.lib.utils.api.Responses;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ExceptionController {
    private static final String SOMETHING_WENT_WRONG = "Something went wrong";
    private static final String DB_NOT_SUPPORTED = "H2 in-memory db doesn't support a search. Please configure postgres";

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception ex) {
        log.error("Was caught exception!", ex);
        return Responses.errorResponse(SOMETHING_WENT_WRONG);
    }

    @ExceptionHandler(CustomUserException.class)
    public ApiResponse<String> handleCustomException(CustomUserException ex) {
        log.error("Was caught exception!", ex);
        return Responses.errorResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ApiResponse<String> handlePostgresDisabledException(InvalidDataAccessResourceUsageException ex) {
        log.error("Was caught exception!", ex);
        return Responses.warnResponse(DB_NOT_SUPPORTED);
    }
}
