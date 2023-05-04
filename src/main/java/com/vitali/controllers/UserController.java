package com.vitali.controllers;

import com.vitali.converters.UserCreateConverter;
import com.vitali.converters.UserInformationCreateConverter;
import com.vitali.database.entities.UserInformation;
import com.vitali.database.entities.enums.Role;
import com.vitali.dto.user.UserCreateDto;
import com.vitali.dto.user.UserReadDto;
import com.vitali.dto.userInformation.UserInformationCreateDto;
import com.vitali.dto.userInformation.UserInformationReadDto;
import com.vitali.services.UserInformationService;
import com.vitali.services.UserService;
import com.vitali.validation.group.UpdateValidationGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import java.util.List;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
@SessionAttributes({"userId", "cartId", "userInformationId"})
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




    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("user") UserCreateDto user,
                               @ModelAttribute("userInformation") UserInformationCreateDto userInformation) {
        model.addAttribute("user", user);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @PostMapping("/registration/add")
    public String create(@ModelAttribute @Validated UserCreateDto user,
                         BindingResult userBindingResult,
                         @ModelAttribute @Validated UserInformationCreateDto userInformation,
                         BindingResult userInformationBindingResult,
                         RedirectAttributes redirectAttributes) {
        if ((userBindingResult.hasErrors() || userInformationBindingResult.hasErrors()) || (userBindingResult.hasErrors() && userInformationBindingResult.hasErrors())) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("userInformation", userInformation);
            redirectAttributes.addFlashAttribute("userErrors", userBindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("userInformationErrors", userInformationBindingResult.getAllErrors());
            return "redirect:/registration";
        }

//        UserCreateDto userCreateDto = userCreateConverter.convert(request);
//        UserInformationCreateDto userInformationCreateDto = userInformationCreateConverter.convert(request);
//
//        log.info("UserController - create - userCreateDto: {}", userCreateDto);
//        log.info("UserController - create - userInformationCreateDto: {}", userInformationCreateDto);

        userService.create(user, userInformation);
        return "redirect:/login";
    }

    @GetMapping("/users/{id}")
    public String findById(@PathVariable Integer id,
                           Model model) {
        UserReadDto user = userService.findById(id);
        UserInformationReadDto userInformation = userInformationService.findUserInformationByUserId(user.getId());
//        log.info("UserController - findById - user: {}", user);
//        log.info("UserController - findById - userInformation: {}", userInformation);
        model.addAttribute("user", user);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("roles", Role.values());

        return "user";
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable("id") Integer userId,
                         @ModelAttribute("user") @Validated({Default.class, UpdateValidationGroup.class}) UserCreateDto userCreateDto,
                         BindingResult userBindingResult,
                         @ModelAttribute("userInformation") @Validated({Default.class, UpdateValidationGroup.class}) UserInformationCreateDto userInformationCreateDto,
                         BindingResult userInformationBindingResult,
                         RedirectAttributes redirectAttributes) {
        if ((userBindingResult.hasErrors() || userInformationBindingResult.hasErrors()) || (userBindingResult.hasErrors() && userInformationBindingResult.hasErrors())) {
            redirectAttributes.addFlashAttribute("user", userCreateDto);
            redirectAttributes.addFlashAttribute("userInformation", userInformationCreateDto);
            redirectAttributes.addFlashAttribute("userErrors", userBindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("userInformationErrors", userInformationBindingResult.getAllErrors());
            return "redirect:/users/{id}";
        }
        UserReadDto user = userService.findById(userId);
        UserInformationReadDto userInformationReadDto = userInformationService.findUserInformationByUserId(user.getId());
        Integer userInformationId = userInformationReadDto.getId();
        userService.update(userId, userCreateDto);
        userInformationService.updateUserInformation(userInformationId, userInformationCreateDto);
        return "redirect:/users/{id}";
    }

    @PostMapping("/users/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

    @GetMapping("/user/edit")
    public String editUserForm(Model model,
                               @ModelAttribute("userId") Integer userId) {
        UserReadDto user = userService.findById(userId);
        UserInformationReadDto userInformation = userInformationService.findUserInformationByUserId(userId);
        model.addAttribute("user", user);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("roles", Role.values());
        return "user/user-edit";
    }

    @PostMapping("/user/edit")
    public String editUser(@ModelAttribute @Validated({Default.class, UpdateValidationGroup.class}) UserCreateDto userCreateDto,
                           BindingResult userBindingResult,
                           @ModelAttribute @Validated({Default.class, UpdateValidationGroup.class}) UserInformationCreateDto userInformationCreateDto,
                           BindingResult userInformationBindingResult,
                           @ModelAttribute("userId") Integer userId,
                           RedirectAttributes redirectAttributes) {
        if ((userBindingResult.hasErrors() || userInformationBindingResult.hasErrors()) || (userBindingResult.hasErrors() && userInformationBindingResult.hasErrors())) {
            redirectAttributes.addFlashAttribute("user", userCreateDto);
            redirectAttributes.addFlashAttribute("userInformation", userInformationCreateDto);
            redirectAttributes.addFlashAttribute("userErrors", userBindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("userInformationErrors", userInformationBindingResult.getAllErrors());
            return "redirect:/user/edit";
        }

        UserReadDto user = userService.findById(userId);
        UserInformationReadDto userInformationReadDto = userInformationService.findUserInformationByUserId(user.getId());
        Integer userInformationId = userInformationReadDto.getId();

        userService.update(userId, userCreateDto);
        userInformationService.updateUserInformation(userInformationId, userInformationCreateDto);
        return "redirect:/products";
    }
}
