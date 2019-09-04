package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.ReportResponse;
import com.money.accounting.backend.model.Report;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ReportConverter extends MoneyEntityConverter<Report, ReportResponse> {

    private final LossConverter lossConverter;
    private final ProfitConverter profitConverter;
    private final LimitConverter limitConverter;

    protected ReportConverter(LossConverter lossConverter,
                              ProfitConverter profitConverter,
                              LimitConverter limitConverter) {
        super(ReportResponse::new);
        this.lossConverter = lossConverter;
        this.profitConverter = profitConverter;
        this.limitConverter = limitConverter;
    }

    @Override
    protected ReportResponse inflateResponse(Report report, ReportResponse reportResponse) {
        return reportResponse
                .setId(report.getId())
                .setDateTo(report.getDateTo())
                .setDateFrom(report.getDateFrom())
                .setLosses(report.getLosses().stream().map(lossConverter::from).collect(Collectors.toList()))
                .setProfits(report.getProfits().stream().map(profitConverter::from).collect(Collectors.toList()))
                .setLimits(report.getLimits().stream().map(limitConverter::from).collect(Collectors.toList()));
    }
}
