package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.ProfitResponse;
import com.money.accounting.backend.model.Profit;
import org.springframework.stereotype.Service;

@Service
public class ProfitConverter extends MoneyEntityConverter<Profit, ProfitResponse> {

    protected ProfitConverter() {
        super(ProfitResponse::new);
    }

    @Override
    protected ProfitResponse inflateResponse(Profit profit, ProfitResponse profitResponse) {
        return profitResponse
                .setId(profit.getId())
                .setName(profit.getName())
                .setState(profit.getState())
                .setDateTime(profit.getDateTime())
                .setMoneyCount(profit.getMoneyCount());
    }
}
