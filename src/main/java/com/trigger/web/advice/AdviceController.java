package com.trigger.web.advice;

import com.trigger.exception.BadRequestException;
import com.trigger.exception.DuplicateEntityException;
import com.trigger.exception.NotFoundException;
import com.trigger.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response<NotFoundException> handleNotFoundException(NotFoundException e) {
        return new Response<>(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response<List<String>> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return new Response<>("Bad Request Exception", errors);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public Response<DuplicateEntityException> handleDuplicateException(DuplicateEntityException e) {
        return new Response<>(e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response<HttpMediaTypeNotSupportedException> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return new Response<>(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response<BadRequestException> handleBadRequestException(BadRequestException e) {
        return new Response<>(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response<HttpMessageNotReadableException> handleHttpMediaTypeNotSupportedException(HttpMessageNotReadableException e) {
        return new Response<>(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response<Exception> handleException(Exception e) {
        log.error("Server Error", e);
        return new Response<>(e.getMessage());

    }
}
