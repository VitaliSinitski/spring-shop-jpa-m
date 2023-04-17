package com.vitali.controllers;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.database.entities.enums.Role;
import com.vitali.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;



    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user";
                })
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("user") UserCreateDto user) {
//    public String showRegistrationPage(Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute UserCreateDto user,
                         RedirectAttributes redirectAttributes){
//        if (true) {
//            redirectAttributes.addAttribute("username", user.getUsername());
//            redirectAttributes.addAttribute("firstName", user.getFirstName());
//            redirectAttributes.addAttribute("lastName", user.getLastName());
//            redirectAttributes.addFlashAttribute("user", user);
//            return "redirect:/users/registration";
//        }
        return "redirect:/users/" + userService.create(user).getId();
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute UserCreateDto user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
