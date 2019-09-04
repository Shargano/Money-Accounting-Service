package com.money.accounting.backend.validator;

import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Profit;
import com.money.accounting.backend.model.enums.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProfitValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfitValidator.class);

    public void checkProfitIsInactive(Profit profit) throws MoneyFinanceException {
        if (profit.getState().equals(State.REMOVED)) {
            LOGGER.warn("Can't operate profit - profit have been archived");
            throw new MoneyFinanceException(MoneyFinanceErrorCode.PROFIT_NOT_ACTIVE);
        }
    }
}
