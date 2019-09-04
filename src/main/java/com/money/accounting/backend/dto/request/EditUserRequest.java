package com.money.accounting.backend.dto.request;

import javax.validation.constraints.NotBlank;

public class EditUserRequest {
    @NotBlank(message = "Zone id can't be empty")
    private String timeZone;

    public EditUserRequest() {
    }

    public EditUserRequest(String timeZone) {
        this.timeZone = timeZone;

    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
