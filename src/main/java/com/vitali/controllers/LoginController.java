package com.vitali.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
