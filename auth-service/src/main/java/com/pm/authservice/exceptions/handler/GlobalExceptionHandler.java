package com.pm.authservice.exceptions.handler;

import com.pm.authservice.dtos.wrapper.ErrorResponse;
import com.pm.authservice.enums.Message;
import com.pm.authservice.exceptions.AccountNonActiveException;
import com.pm.authservice.exceptions.AlreadyExistsException;
import com.pm.authservice.exceptions.BadRequestException;
import com.pm.authservice.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.info("Validation Error");

        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        log.info(errors.toString());

        return ResponseEntity.status(BAD_REQUEST).body(
                ErrorResponse.error(BAD_REQUEST.value(), Message.VALIDATION_ERROR.getMessage(), errors));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleNonUniqueException(AlreadyExistsException ex) {

        log.info("Already Exists Error: {}", ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.error(BAD_REQUEST.value(), ex.getMessage()));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {

        log.info("Max Upload Size Exceeded Error: {}", ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse.error(BAD_REQUEST.value(), "File Size should not be more than 10MB"));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {

        log.info("Bad Request Error: {}", ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.error(BAD_REQUEST.value(), ex.getMessage()));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AccountNonActiveException.class)
    public ResponseEntity<ErrorResponse> handleAccountNonActiveException(AccountNonActiveException ex) {

        log.info("Account Non Active Error: {}", ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.error(BAD_REQUEST.value(), ex.getMessage()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

        log.info("Resource Not Found Error: {}", ex.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.error(NOT_FOUND.value(), ex.getMessage()));
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {

        log.info("No Resource Found Error: {}", ex.getMessage());
        return ResponseEntity.status(NOT_FOUND)
                .body(ErrorResponse.error(NOT_FOUND.value(), Message.RESOURCE_NOT_FOUND.getMessage()));
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {

        log.error("Internal Server Error: {}", ex.getMessage());
        log.error("Error: ", ex.fillInStackTrace());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ErrorResponse.error(INTERNAL_SERVER_ERROR.value(), Message.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
