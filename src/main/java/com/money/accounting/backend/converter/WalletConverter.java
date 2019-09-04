package com.money.accounting.backend.converter;

import com.money.accounting.backend.dto.response.WalletResponse;
import com.money.accounting.backend.model.Wallet;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Service
public class WalletConverter extends MoneyEntityConverter<Wallet, WalletResponse> {

    private final LimitConverter limitConverter;

    public WalletConverter(LimitConverter limitConverter) {
        super(WalletResponse::new);
        this.limitConverter = limitConverter;
    }

    @Override
    protected WalletResponse inflateResponse(Wallet wallet, WalletResponse walletResponse) {
        if (wallet.getLimits() != null) { // for audit under transaction
            walletResponse.setLimits(wallet.getLimits().stream()
                    .filter(limit -> limit.getDateTo().compareTo(ZonedDateTime.now(ZoneId.of(wallet.getUser().getTimeZone()))) > 0)
                    .map(limitConverter::from)
                    .collect(Collectors.toList()));
        }
        return walletResponse
                .setId(wallet.getId())
                .setName(wallet.getName())
                .setState(wallet.getState())
                .setMoneyCount(wallet.getMoneyCount());
    }
}
