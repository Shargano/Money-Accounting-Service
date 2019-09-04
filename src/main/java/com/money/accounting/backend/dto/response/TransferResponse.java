package com.money.accounting.backend.dto.response;

import java.math.BigDecimal;

public class TransferResponse {
    private BigDecimal moneyCount;
    private WalletResponse walletFrom;
    private WalletResponse walletTo;

    public TransferResponse() {
    }

    public TransferResponse(BigDecimal moneyCount, WalletResponse walletFrom, WalletResponse walletTo) {
        this.moneyCount = moneyCount;
        this.walletFrom = walletFrom;
        this.walletTo = walletTo;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public TransferResponse setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
        return this;
    }

    public WalletResponse getWalletFrom() {
        return walletFrom;
    }

    public TransferResponse setWalletFrom(WalletResponse walletFrom) {
        this.walletFrom = walletFrom;
        return this;
    }

    public WalletResponse getWalletTo() {
        return walletTo;
    }

    public TransferResponse setWalletTo(WalletResponse walletTo) {
        this.walletTo = walletTo;
        return this;
    }

    @Override
    public String toString() {
        return "TransferResponse{" +
                "moneyCount=" + moneyCount +
                ", walletFrom=" + walletFrom +
                ", walletTo=" + walletTo +
                '}';
    }
}
