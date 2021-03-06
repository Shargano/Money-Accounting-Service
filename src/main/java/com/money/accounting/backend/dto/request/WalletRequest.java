package com.money.accounting.backend.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class WalletRequest {
    @NotBlank(message = "Name can't be empty")
    private String name;

    @NotNull(message = "MoneyCount can't be null")
    @PositiveOrZero(message = "MoneyCount can't be negative")
    private BigDecimal moneyCount;

    public WalletRequest() {
    }

    public WalletRequest(String name, BigDecimal moneyCount) {
        this.name = name;
        this.moneyCount = moneyCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(BigDecimal moneyCount) {
        this.moneyCount = moneyCount;
    }
}
