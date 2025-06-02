package com.cgfoundry.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class ApiControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                        HttpServletRequest request) {
        return ResponseEntity.badRequest()
                .body(buildErrorResponse(
                        request.getServletPath(),
                        "/errors/invalid-body",
                        exception.getMessage(),
                        exception.getLocalizedMessage(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    @ExceptionHandler(value = UserAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException exception,
                                                                       HttpServletRequest request) {

        return ResponseEntity.badRequest()
                .body(buildErrorResponse(
                        request.getServletPath(),
                        "/errors/email-taken",
                        exception.getMessage(),
                        exception.getDetail(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

    private ErrorResponse buildErrorResponse(String instance, String type, String title, String detail, int status) {
        return ErrorResponse.builder()
                .instance(instance)
                .title(title)
                .type(type)
                .detail(detail)
                .status(status)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
