package com.vitali.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

//@Slf4j
//@Controller
//@RequiredArgsConstructor
//@RequestMapping
public class LanguageController {

//    @GetMapping("/language")
//    public String setLanguage(@RequestParam("lang") String lang,
//                              HttpServletRequest request,
//                              HttpServletResponse response) {
//        Locale locale = Locale.forLanguageTag(lang);
//        Cookie cookie = new Cookie("lang", lang);
//        response.addCookie(cookie);
//        request.setAttribute(SessionLocaleResolver.class.getName() + ".LOCALE", locale);
//        return "redirect:" + request.getHeader("Referer");
//    }
}
