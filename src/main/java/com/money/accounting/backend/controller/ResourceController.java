package com.money.accounting.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ResourceController {

    @GetMapping("ping")
    public String getResourcePage() {
        return "PONG";
    }

}