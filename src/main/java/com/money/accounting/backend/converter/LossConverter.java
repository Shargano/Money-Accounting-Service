package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.LossResponse;
import com.money.accounting.backend.model.Loss;
import org.springframework.stereotype.Service;

@Service
public class LossConverter extends MoneyEntityConverter<Loss, LossResponse> {

    protected LossConverter() {
        super(LossResponse::new);
    }

    @Override
    protected LossResponse inflateResponse(Loss loss, LossResponse lossResponse) {
        return lossResponse
                .setId(loss.getId())
                .setName(loss.getName())
                .setState(loss.getState())
                .setDateTime(loss.getDateTime())
                .setMoneyCount(loss.getMoneyCount());
    }
}
