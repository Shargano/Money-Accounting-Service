package com.money.accounting.backend.controller;

import com.money.accounting.backend.converter.TransferConverter;
import com.money.accounting.backend.dto.request.TransferRequest;
import com.money.accounting.backend.dto.response.TransferResponse;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.service.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/wallet")
public class TransferController {

    private final WalletService walletService;
    private final TransferConverter transferConverter;

    public TransferController(WalletService walletService, TransferConverter transferConverter) {
        this.walletService = walletService;
        this.transferConverter = transferConverter;
    }

    @PostMapping("transfer")
    public TransferResponse createTransfer(@Valid @RequestBody TransferRequest request,
                                           @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return transferConverter.from(walletService.makeTransfer(request, user));
    }

}
