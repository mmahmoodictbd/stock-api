package com.unloadbrain.assignement.payconiq.stockapi.exception.handler;


import lombok.Data;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * BaseExceptionHandler provides -
 * 1. Handles any unknown error/exception.
 * 2. ErrorResponse, DTO containing error information.
 */
public abstract class BaseExceptionHandler {

    private final Logger log;

    protected BaseExceptionHandler(final Logger log) {
        this.log = log;
    }

    /**
     * Catch any unknown error/exception and provide readable response to end-user.
     *
     * @param ex Throwable object for error/exception.
     * @return the error response DTO {@link ErrorResponse}
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable ex) {
        log.error("Unexpected error", ex);
        return new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected internal server error occurred");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public void handleNoHandlerFoundException(final Throwable ex) {
        log.error("Unexpected error", ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public void handleMethodArgumentNotValidException(final Throwable ex) {
        log.error("Unexpected error", ex);
    }


    /**
     * DTO containing error information.
     */
    @Data
    public static class ErrorResponse {

        private final String code;
        private final String message;
    }

}
