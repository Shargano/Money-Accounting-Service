package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.LimitResponse;
import com.money.accounting.backend.model.Limit;
import org.springframework.stereotype.Service;

@Service
public class LimitConverter extends MoneyEntityConverter<Limit, LimitResponse> {

    public LimitConverter() {
        super(LimitResponse::new);
    }

    @Override
    protected LimitResponse inflateResponse(Limit limit, LimitResponse limitResponse) {
        return limitResponse
                .setId(limit.getId())
                .setDateTo(limit.getDateTo())
                .setDateFrom(limit.getDateFrom())
                .setMoneyCount(limit.getMoneyCount());
    }
}
