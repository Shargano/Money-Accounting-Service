package com.money.accounting.backend.controller;

import com.money.accounting.backend.converter.UserConverter;
import com.money.accounting.backend.dto.request.EditUserRequest;
import com.money.accounting.backend.dto.response.EditUserResponse;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final UserConverter userConverter;
    private final UserService userService;

    public UserController(UserConverter userConverter, UserService userService) {
        this.userConverter = userConverter;
        this.userService = userService;
    }

    @PostMapping("registration")
    public EditUserResponse editInfo(@Valid @RequestBody EditUserRequest request,
                                     @AuthenticationPrincipal User user) {
        return userConverter.from(userService.edit(request, user));
    }
}