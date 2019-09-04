package com.money.accounting.backend.controller;

import com.money.accounting.backend.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class PageController {

    @GetMapping
    public String getRootPage(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        if (user != null) {
            data.put("userName", user.getLogin());
            data.put("isEnable", user.isEnable());
        }
        model.addAttribute("dataFromBack", data);
        return "index";
    }

}
