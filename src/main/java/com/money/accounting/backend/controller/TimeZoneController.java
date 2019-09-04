package com.money.accounting.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.TreeSet;

@RestController
@RequestMapping("api/v1")
public class TimeZoneController {

    @GetMapping("timezone")
    public TreeSet<String> getAll() {
        return new TreeSet<>(ZoneId.getAvailableZoneIds());
    }

}
