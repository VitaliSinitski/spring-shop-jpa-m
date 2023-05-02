package com.vitali.controllers;

import com.vitali.converters.UserCreateConverter;
import com.vitali.converters.UserInformationCreateConverter;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.entities.enums.Role;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.services.UserInformationService;
import com.vitali.services.UserService;
import com.vitali.validation.group.CreateAction;
import com.vitali.validation.group.UpdateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserInformationService userInformationService;
    private final UserCreateConverter userCreateConverter;
    private final UserInformationCreateConverter userInformationCreateConverter;


    @GetMapping("/users")
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
//        model.addAttribute("userInformation", userInformationService.findAll());
        model.addAttribute("currentUser", userService.getCurrentUserByUsernameFromSecurityContext());
        return "users";
    }

//    @GetMapping("/users/{id}")
//    public String findById(@PathVariable Integer id, Model model) {
//        return userService.findById(id)
//                .map(user -> {
//                    model.addAttribute("user", user);
//                    model.addAttribute("roles", Role.values());
//                    return "user";
//                })
//                .orElseThrow(
////                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//                        () -> new EntityNotFoundException("User not found"));
//    }

    @GetMapping("/users/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        UserReadDto user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user";
    }


    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("user") UserCreateDto user,
                               @ModelAttribute("userInformation") UserInformationCreateDto userInformation) {
        log.info("UserController - registration - start");
        model.addAttribute("user", user);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("roles", Role.values());
        log.info("UserController - registration - user: {}", user);
        log.info("UserController - registration - userInformation: {}", userInformation);
        return "registration";
    }

    @PostMapping("/registration/add")
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) UserCreateDto user,
                         @ModelAttribute @Validated({Default.class, CreateAction.class}) UserInformationCreateDto userInformation,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {
        log.info("UserController - create - start");
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("userInformation", userInformation);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }

        log.info("UserController - create - user: {}", user);
        log.info("UserController - create - userInformation: {}", userInformation);

//        UserCreateDto userCreateDto = userCreateConverter.convert(request);
//        UserInformationCreateDto userInformationCreateDto = userInformationCreateConverter.convert(request);
//
//        log.info("UserController - create - userCreateDto: {}", userCreateDto);
//        log.info("UserController - create - userInformationCreateDto: {}", userInformationCreateDto);

        userService.create(user, userInformation);
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
