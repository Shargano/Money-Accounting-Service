package com.money.accounting.backend.validator;

import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserValidator.class);

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkUserAlreadyExist(User user) throws MoneyFinanceException {
        if (userRepository.getByLogin(user.getLogin()) != null) {
            LOGGER.error("Can't create user {} - already exists", user);
            throw new MoneyFinanceException(MoneyFinanceErrorCode.USER_ALREADY_EXISTS);
        }
    }
}
