package com.money.accounting.backend.controller;

import com.money.accounting.backend.converter.LossConverter;
import com.money.accounting.backend.dto.request.EditOperationRequest;
import com.money.accounting.backend.dto.request.LossRequest;
import com.money.accounting.backend.dto.response.LossResponse;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.service.LossService;
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
@RequestMapping("/api/v1/loss")
public class LossController {
    private final LossConverter lossConverter;
    private final LossService lossService;

    public LossController(LossConverter lossConverter, LossService lossService) {
        this.lossConverter = lossConverter;
        this.lossService = lossService;
    }

    @PostMapping
    public LossResponse createLoss(@Valid @RequestBody LossRequest request,
                                   @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return lossConverter.from(lossService.create(request, user));
    }

    @PutMapping(value = "{id}")
    public LossResponse editLoss(@PathVariable(value = "id") int lossId,
                                 @Valid @RequestBody EditOperationRequest request,
                                 @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return lossConverter.from(lossService.update(lossId, request, user));
    }

    @DeleteMapping(value = "{id}")
    public LossResponse archiveLoss(@PathVariable(value = "id") int lossId,
                                    @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return lossConverter.from(lossService.archive(lossId, user));
    }
}
