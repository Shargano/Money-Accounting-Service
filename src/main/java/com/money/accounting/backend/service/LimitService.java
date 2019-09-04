package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.ZonedDateTimeConverter;
import com.money.accounting.backend.dto.request.LimitRequest;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Limit;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.model.Wallet;
import com.money.accounting.backend.model.enums.State;
import com.money.accounting.backend.repository.LimitRepository;
import com.money.accounting.backend.validator.WalletValidator;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LimitService {
    private final LimitRepository limitRepository;
    private final WalletValidator walletValidator;
    private final ZonedDateTimeConverter dateConverter;

    public LimitService(LimitRepository limitRepository,
                        WalletValidator walletValidator,
                        ZonedDateTimeConverter dateConverter) {
        this.limitRepository = limitRepository;
        this.walletValidator = walletValidator;
        this.dateConverter = dateConverter;
    }

    public Wallet addLimit(Wallet wallet, LimitRequest request) throws MoneyFinanceException {
        walletValidator.checkWalletIsInactive(wallet);
        Limit limit = inflateEntity(request, new Limit(), wallet.getUser());
        limit.setWallet(wallet);
        limitRepository.save(limit);
        return wallet;
    }

    public List<Limit> getAllBetweenDates(User user, ZonedDateTime from, ZonedDateTime to) {
        return limitRepository
                .findAllByWalletUserIdAndDateFromBetweenOrDateToBetween(user.getId(), from, to, from, to)
                .stream()
                .filter(limit -> limit.getWallet().getState().equals(State.ACTIVE))
                .collect(Collectors.toList());
    }

    private Limit inflateEntity(LimitRequest request, Limit limit, User user) {
        limit.setMoneyCount(request.getMoneyCount());
        String stringFrom = request.getDateFrom() + " 00:00:00";
        String stringTo = request.getDateTo() + " 00:00:00";
        ZonedDateTime dateFrom = dateConverter.convert(stringFrom, ZoneId.of(user.getTimeZone()));
        ZonedDateTime dateTo = dateConverter.convert(stringTo, ZoneId.of(user.getTimeZone()));
        limit.setDateFrom(dateFrom);
        limit.setDateTo(dateTo);
        return limit;
    }


}
