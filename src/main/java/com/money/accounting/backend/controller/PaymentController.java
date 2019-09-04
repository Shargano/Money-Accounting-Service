package com.money.accounting.backend.controller;

import com.money.accounting.backend.converter.PaymentConverter;
import com.money.accounting.backend.dto.response.PaymentResponse;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.service.WalletService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/wallet")
public class PaymentController {

    private final WalletService walletService;
    private final PaymentConverter paymentConverter;

    public PaymentController(WalletService walletService, PaymentConverter paymentConverter) {
        this.walletService = walletService;
        this.paymentConverter = paymentConverter;
    }

    @GetMapping(value = "{id}/payment")
    public List<PaymentResponse> getWalletPayments(@PathVariable(value = "id") Wallet wallet) {
        return walletService.getPayments(wallet).stream().map(paymentConverter::from).collect(Collectors.toList());
    }

}
