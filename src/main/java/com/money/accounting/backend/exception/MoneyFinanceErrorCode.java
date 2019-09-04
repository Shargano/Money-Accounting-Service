package com.money.accounting.backend.exception;

public enum MoneyFinanceErrorCode {
    USER_ALREADY_EXISTS("login", "User with the same login is already exists"),
    WALLET_NOT_EXIST("id", "Wallet with this id doesn't exist"),
    WALLET_ALREADY_EXISTS("name", "You already have wallet with the same name"),
    NOT_ENOUGH_MONEY("moneyCount", "You don't have enough money to make the loss"),
    WALLET_NOT_ACTIVE("state", "This wallet inactive"),
    PROFIT_NOT_EXIST("id", "Profit with this id doesn't exist"),
    PROFIT_NOT_ACTIVE("state", "This profit inactive"),
    LOSS_NOT_EXIST("id", "Loss with this id doesn't exist"),
    LOSS_NOT_ACTIVE("state", "This loss inactive"),
    PAYMENT_NOT_EXIST("id", "Payment with this id doesn't exist"),
    REPORT_NOT_EXIST("id", "Report with this id doesn't exist");

    private String field;
    private String message;

    MoneyFinanceErrorCode(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}