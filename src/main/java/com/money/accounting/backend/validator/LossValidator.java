package com.money.accounting.backend.validator;

import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Loss;
import com.money.accounting.backend.model.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LossValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LossValidator.class);

    public void checkLossIsInactive(Loss loss) throws MoneyFinanceException {
        if (loss.getState().equals(State.REMOVED)) {
            LOGGER.error("Can't operate wallet - wallet have been archived");
            throw new MoneyFinanceException(MoneyFinanceErrorCode.LOSS_NOT_ACTIVE);
        }
    }
}
