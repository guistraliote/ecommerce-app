package com.gstraliote.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private RestErrorMessage getErrorResponse(
            HttpStatus httpStatus,
            String defaultMessage,
            String message,
            String defaultMessageKey){

        return RestErrorMessage.builder()
                .code(String.valueOf(httpStatus.value()))
                .message(defaultMessage)
                .details(message)
                .messageCode(defaultMessageKey)
                .build();
    }
}