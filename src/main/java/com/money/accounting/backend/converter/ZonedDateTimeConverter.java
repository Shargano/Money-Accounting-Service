package com.money.accounting.backend.converter;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ZonedDateTimeConverter {

    public ZonedDateTime convert(String string, ZoneId zoneId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(string, formatter);
        return localDate.atZone(zoneId);
    }

}
