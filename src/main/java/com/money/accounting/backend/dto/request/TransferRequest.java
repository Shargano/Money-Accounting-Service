package com.money.accounting.backend.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class TransferRequest {
    @NotNull(message = "Source wallet for transfer can't be null")
    private Integer walletFrom;

    @NotNull(message = "Goal wallet for transfer can't be null")
    private Integer walletTo;

    @NotNull(message = "MoneyCount can't be null")
    @PositiveOrZero(message = "MoneyCount can't be negative")
    private BigDecimal moneyCount;

    public TransferRequest() {
    }

    public TransferRequest(int walletFrom, int walletTo, BigDecimal moneyCount) {
        this.walletFrom = walletFrom;
        this.walletTo = walletTo;
        this.moneyCount = moneyCount;
    }

    public int getWalletFrom() {
        return walletFrom;
    }

    public void setWalletFrom(int walletFrom) {
        this.walletFrom = walletFrom;
    }

    public int getWalletTo() {
        return walletTo;
    }

    public void setWalletTo(int walletTo) {
        this.walletTo = walletTo;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }
}
