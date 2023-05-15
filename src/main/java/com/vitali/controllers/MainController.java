package com.vitali.controllers;

import com.vitali.services.CategoryService;
import com.vitali.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping
public class MainController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping({"/", "/index", "/main"})
    public String main(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "redirect:/products";
    }

    @GetMapping("/?lang={lang}")
    public String changeLocale(@PathVariable String lang,
                               HttpServletRequest request) {
        Locale locale = Locale.forLanguageTag(lang);
        request.getSession().setAttribute(SessionLocaleResolver.class.getName() + ".LOCALE", locale);
        String referrer = request.getHeader("Referer");
        return "redirect:" + referrer;
    }
}
