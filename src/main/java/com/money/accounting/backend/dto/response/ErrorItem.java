package com.money.accounting.backend.dto.response;

public class ErrorItem {
    private String field;
    private String message;

    public ErrorItem() {
    }

    public ErrorItem(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}