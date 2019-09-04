package com.money.accounting.backend.controller;

import com.money.accounting.backend.converter.ProfitConverter;
import com.money.accounting.backend.dto.request.EditOperationRequest;
import com.money.accounting.backend.dto.request.ProfitRequest;
import com.money.accounting.backend.dto.response.ProfitResponse;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.service.ProfitService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/profit")
public class ProfitController {
    private final ProfitConverter profitConverter;
    private final ProfitService profitService;

    public ProfitController(ProfitConverter profitConverter, ProfitService profitService) {
        this.profitConverter = profitConverter;
        this.profitService = profitService;
    }

    @PostMapping
    public ProfitResponse createProfit(@Valid @RequestBody ProfitRequest request,
                                       @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return profitConverter.from(profitService.create(request, user));
    }

    @PutMapping(value = "{id}")
    public ProfitResponse editProfit(@PathVariable(value = "id") int profitId,
                                     @Valid @RequestBody EditOperationRequest request,
                                     @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return profitConverter.from(profitService.update(profitId, request, user));
    }

    @DeleteMapping(value = "{id}")
    public ProfitResponse archiveProfit(@PathVariable(value = "id") int profitId,
                                        @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return profitConverter.from(profitService.archive(profitId, user));
    }
}