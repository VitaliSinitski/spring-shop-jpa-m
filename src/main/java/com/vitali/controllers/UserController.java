package com.vitali.controllers;

import com.vitali.dto.user.UserCreateDto;
import com.vitali.database.entities.enums.Role;
import com.vitali.services.UserInformationService;
import com.vitali.services.UserService;
import com.vitali.validation.group.CreateAction;
import com.vitali.validation.group.UpdateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.groups.Default;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserInformationService userInformationService;


    @GetMapping("/users")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
//        model.addAttribute("userInformation", userInformationService.findAll());
        model.addAttribute("currentUser", userService.getCurrentUserByUsernameFromSecurityContext());
        return "users";
    }

    @GetMapping("/users/{id}")
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

    @PostMapping("/users")
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) UserCreateDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }
        userService.create(user);
        return "redirect:/login";
    }

//    @PutMapping("/{id}")
    @PostMapping("/users/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) UserCreateDto user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/users/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
