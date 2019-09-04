package com.money.accounting.backend.dto.response;

public class AccountInfoResponse {

    private String userName;
    private boolean isEnable;

    public AccountInfoResponse() {
    }

    public AccountInfoResponse(String userName, boolean isEnable) {
        this.userName = userName;
        this.isEnable = isEnable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
