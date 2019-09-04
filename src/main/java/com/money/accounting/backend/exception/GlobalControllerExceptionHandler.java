package com.money.accounting.backend.exception;

import com.money.accounting.backend.dto.response.ErrorItem;
import com.money.accounting.backend.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleFieldValidation(MethodArgumentNotValidException ex) {
        List<ErrorItem> items = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors())
            items.add(new ErrorItem(fieldError.getField(), fieldError.getDefaultMessage()));
        return new ErrorResponse(items);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MoneyFinanceException.class)
    @ResponseBody
    public ErrorResponse handleMoneyFinanceException(MoneyFinanceException ex) {
        List<ErrorItem> items = new ArrayList<>();
        MoneyFinanceErrorCode errorCode = ex.getErrorCode();
        items.add(new ErrorItem(errorCode.getField(), errorCode.getMessage()));
        return new ErrorResponse(items);
    }

}
