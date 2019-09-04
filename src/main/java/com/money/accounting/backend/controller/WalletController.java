package com.money.accounting.backend.controller;

import com.money.accounting.backend.converter.WalletConverter;
import com.money.accounting.backend.dto.request.LimitRequest;
import com.money.accounting.backend.dto.request.WalletRequest;
import com.money.accounting.backend.dto.response.WalletResponse;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.service.LimitService;
import com.money.accounting.backend.service.WalletService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletConverter walletConverter;
    private final WalletService walletService;
    private final LimitService limitService;

    public WalletController(WalletConverter walletConverter,
                            WalletService walletService,
                            LimitService limitService) {
        this.walletConverter = walletConverter;
        this.walletService = walletService;
        this.limitService = limitService;
    }

    @PostMapping
    public WalletResponse createWallet(@Valid @RequestBody WalletRequest request,
                                       @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return walletConverter.from(walletService.create(request, user));
    }

    @PostMapping(value = "{id}/limit")
    public WalletResponse createLimit(@PathVariable(value = "id") Wallet wallet,
                                      @Valid @RequestBody LimitRequest request) throws MoneyFinanceException {
        return walletConverter.from(limitService.addLimit(wallet, request));
    }

    @GetMapping
    public List<WalletResponse> getAllWallets(@AuthenticationPrincipal User user) {
        return walletService.getAllWallets(user).stream().map(walletConverter::from).collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    public WalletResponse getWallet(@PathVariable(value = "id") int walletId,
                                    @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return walletConverter.from(walletService.get(user, walletId));
    }

    @PutMapping(value = "{id}")
    public WalletResponse editWallet(@PathVariable(value = "id") int walletId,
                                     @Valid @RequestBody WalletRequest request,
                                     @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return walletConverter.from(walletService.update(walletId, request, user));
    }

    @DeleteMapping(value = "{id}")
    public WalletResponse deleteWallet(@PathVariable(value = "id") int walletId,
                                       @AuthenticationPrincipal User user) throws MoneyFinanceException {
        return walletConverter.from(walletService.archive(walletId, user));
    }
}
