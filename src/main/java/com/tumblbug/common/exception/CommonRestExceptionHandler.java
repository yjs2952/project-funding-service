package com.tumblbug.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class CommonRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = this.getErrorResponse(status, ex.getBindingResult());
        return handleExceptionInternal(ex, errorResponse, null, status, request);
    }

    /**
     * Bean Validation 에러에 대한 Response
     *
     * @param status
     * @param bindingResult
     * @return
     */
    private ErrorResponse getErrorResponse(HttpStatus status, BindingResult bindingResult) {
        ErrorResponse errorResponse = new ErrorResponse(CommonError.COMMON_BAD_REQUEST.getMessage(), status);
        List<String> errors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : bindingResult.getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        errorResponse.setErrors(errors);
        return errorResponse;
    }
}
