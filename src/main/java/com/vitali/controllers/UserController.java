package com.vitali.controllers;

import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

//@Controller
//@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//    private final ReviewService reviewService;

//    @GetMapping
//    public String findAllByFilter(Model entities, @ModelAttribute("userFilterDto") UserFilterDto userFilterDto) {
//        entities.addAttribute("users", userService.findAllByFilter(userFilterDto));
//        return "user/users";
//    }

//    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
//                    entities.addAttribute("reviews", reviewService.findAllByUserId(id));
                    return "user/user";
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
