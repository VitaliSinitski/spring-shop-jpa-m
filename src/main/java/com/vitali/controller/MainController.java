package com.vitali.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping({"/", "/index", "/main"})
public class MainController {
    @RequestMapping
    public String main() {
        return "products";
    }
}
