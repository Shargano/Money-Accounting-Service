package com.money.accounting.backend.dto.response;

import java.time.ZonedDateTime;
import java.util.List;

public class ReportResponse {
    private Integer id;
    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;
    private List<ProfitResponse> profits;
    private List<LossResponse> losses;
    private List<LimitResponse> limits;

    public ReportResponse() {
    }

    public Integer getId() {
        return id;
    }

    public ReportResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public ReportResponse setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    public ReportResponse setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public List<ProfitResponse> getProfits() {
        return profits;
    }

    public ReportResponse setProfits(List<ProfitResponse> profits) {
        this.profits = profits;
        return this;
    }

    public List<LossResponse> getLosses() {
        return losses;
    }

    public ReportResponse setLosses(List<LossResponse> losses) {
        this.losses = losses;
        return this;
    }

    public List<LimitResponse> getLimits() {
        return limits;
    }

    public ReportResponse setLimits(List<LimitResponse> limits) {
        this.limits = limits;
        return this;
    }
}
