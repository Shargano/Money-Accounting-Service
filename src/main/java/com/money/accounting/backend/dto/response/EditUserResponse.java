package com.money.accounting.backend.dto.response;

import com.money.accounting.backend.model.enums.Role;

import java.util.Set;

public class EditUserResponse {
    private Integer id;
    private String timeZone;
    private Set<Role> roles;

    public EditUserResponse() {
    }

    public Integer getId() {
        return id;
    }

    public EditUserResponse setId(Integer id) {
        this.id = id;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public EditUserResponse setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public EditUserResponse setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    @Override
    public String toString() {
        return "EditUserResponse{" +
                "id=" + id +
                ", timeZone='" + timeZone + '\'' +
                ", roles=" + roles +
                '}';
    }
}
