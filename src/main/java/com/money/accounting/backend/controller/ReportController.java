package com.money.accounting.backend.controller;

import com.money.accounting.backend.converter.ReportConverter;
import com.money.accounting.backend.dto.request.ReportRequest;
import com.money.accounting.backend.dto.response.ReportResponse;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.service.ReportService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;
    private final ReportConverter reportConverter;

    public ReportController(ReportService reportService, ReportConverter reportConverter) {
        this.reportService = reportService;
        this.reportConverter = reportConverter;
    }

    @PostMapping
    public ReportResponse createReport(@AuthenticationPrincipal User user,
                                       @Valid @RequestBody ReportRequest request) {
        return reportConverter.from(reportService.create(request, user));
    }

    @GetMapping
    public List<ReportResponse> getAll(@AuthenticationPrincipal User user) {
        return reportService.getAll(user).stream()
                .map(reportConverter::from)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    public ReportResponse get(@PathVariable(value = "id") int reportId,
                              @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return reportConverter.from(reportService.get(user, reportId));
    }
}
