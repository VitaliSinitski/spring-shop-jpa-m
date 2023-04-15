package com.vitali.controllers;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/user";
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute UserCreateDto user){
        return "redirect:/users/" + userService.create(user);
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute UserCreateDto user) {
//        userService.update(id, user);
        return "redirect:/users/{id}";
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id/delete}")
    public String delete(@PathVariable Integer id) {
//        userService.delete(id);
        return "redirect:/users";
    }


}
