package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.UserConverter;
import com.money.accounting.backend.dto.request.EditUserRequest;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.enums.Role;
import com.money.accounting.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService extends EntityService<User, EditUserRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuditService auditService;
    private final UserConverter converter;

    public UserService(UserRepository userRepository,
                       AuditService auditService,
                       UserConverter converter) {
        this.userRepository = userRepository;
        this.auditService = auditService;
        this.converter = converter;
    }

    public User edit(EditUserRequest request, User user) {
        user = inflateEntity(request, user);
        user.setEnable(true);
        this.save(user);
        return user;
    }

    @Override
    public User inflateEntity(EditUserRequest request, User user) {
        user.setRoles(Collections.singleton(Role.USER));
        user.setTimeZone(request.getTimeZone());
        return user;
    }

    @Override
    public User findByUserIdAndId(Integer userId, Integer entityId) {
        return null;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
        LOGGER.info("Save user {}", user);
        auditService.save(user, converter);
    }

}