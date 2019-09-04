package com.money.accounting.backend.exception;

public class MoneyFinanceException extends Exception {
    private MoneyFinanceErrorCode moneyFinanceErrorCode;

    public MoneyFinanceException(MoneyFinanceErrorCode moneyFinanceErrorCode) {
        this.moneyFinanceErrorCode = moneyFinanceErrorCode;
    }

    public MoneyFinanceErrorCode getErrorCode() {
        return moneyFinanceErrorCode;
    }
}