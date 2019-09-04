package com.money.accounting.backend.dto.request;

import javax.validation.constraints.NotBlank;

public class ReportRequest {
    @NotBlank(message = "DateFrom can't be empty")
    private String dateFrom;

    @NotBlank(message = "DateTo can't be empty")
    private String dateTo;

    public ReportRequest() {
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
