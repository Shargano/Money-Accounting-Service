package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.EditUserResponse;
import com.money.accounting.backend.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserConverter extends MoneyEntityConverter<User, EditUserResponse> {
    protected UserConverter() {
        super(EditUserResponse::new);
    }

    @Override
    protected EditUserResponse inflateResponse(User user, EditUserResponse userResponse) {
        return userResponse
                .setId(user.getId())
                .setRoles(user.getRoles())
                .setTimeZone(user.getTimeZone());
    }
}
