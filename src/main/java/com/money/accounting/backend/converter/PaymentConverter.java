package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.PaymentResponse;
import com.money.accounting.backend.model.Payment;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PaymentConverter extends MoneyEntityConverter<Payment, PaymentResponse> {

    public PaymentConverter() {
        super(PaymentResponse::new);
    }

    @Override
    protected PaymentResponse inflateResponse(Payment payment, PaymentResponse paymentResponse) {
        if (payment.getCorrectingPayments() != null) { // for audit under transaction
            paymentResponse.setCorrectingPayments(payment.getCorrectingPayments().stream()
                    .map(this::from).collect(Collectors.toList()));
        }
        return paymentResponse
                .setId(payment.getId())
                .setType(payment.getType())
                .setGoalId(payment.getGoalId())
                .setDateTime(payment.getDateTime())
                .setMoneyCount(payment.getMoneyCount());
    }
}
