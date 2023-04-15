package com.vitali.controllers;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.entities.User;
import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
//        boolean isAuthenticated = userService.authenticate(username, password);
//        if (isAuthenticated) {
//            return ResponseEntity.ok("Authenticated");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "registration";
//    }
//
//    @PostMapping("/register")
//    public String register(@ModelAttribute User user) {
//        userService.create(converter.convert(user));
//        return "redirect:/login";
//    }

//    @GetMapping
//    public String findAllByFilter(Model entities, @ModelAttribute("userFilterDto") UserFilterDto userFilterDto) {
//        entities.addAttribute("users", userService.findAllByFilter(userFilterDto));
//        return "user/users";
//    }

//    @GetMapping("/{id}")
//    public String findById(@PathVariable("id") Integer id, Model model) {
//        return userService.findById(id)
//                .map(user -> {
//                    model.addAttribute("user", user);
////                    entities.addAttribute("reviews", reviewService.findAllByUserId(id));
//                    return "user/user";
//                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
}
