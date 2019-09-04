package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.TransferResponse;
import com.money.accounting.backend.model.Transfer;
import org.springframework.stereotype.Service;

@Service
public class TransferConverter extends MoneyEntityConverter<Transfer, TransferResponse> {

    private final WalletConverter walletConverter;

    public TransferConverter(WalletConverter walletConverter) {
        super(TransferResponse::new);
        this.walletConverter = walletConverter;
    }

    @Override
    protected TransferResponse inflateResponse(Transfer transfer, TransferResponse transferResponse) {
        return transferResponse
                .setMoneyCount(transfer.getMoneyCount())
                .setWalletTo(walletConverter.from(transfer.getTo()))
                .setWalletFrom(walletConverter.from(transfer.getFrom()));
    }
}
