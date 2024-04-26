package bank.controllers;

import bank.dto.UserForm;
import bank.model.User;
import bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller("UserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final String USER_PAGE = "user-info";
    private final String USER_UPDATE_PAGE = "user-update";
    private final ModelMapper modelMapper;

    @GetMapping
    public String showUserInfo(@SessionAttribute Long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        return USER_PAGE;
    }

    @GetMapping("/update/{userId}")
    public String showUpdateUser(@PathVariable Long userId, Model model) {
        model.addAttribute("userForm", modelMapper.map(userService.findById(userId), UserForm.class));
        model.addAttribute("userId", userId);
        return USER_UPDATE_PAGE;
    }

    @PostMapping("update/{userId}")
    public String updateUser(
            Model model, @PathVariable Long userId, @RequestParam("password") String password,
            @ModelAttribute("userForm") @Validated UserForm userForm, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return USER_UPDATE_PAGE;
        }

        User user = userService.findById(userId);
        if (!Objects.equals(password, user.getPassword())) {
            model.addAttribute("incorrectPassword",
                    "Incorrect password! It doesn't match with the old one!");
        }

        userService.update(userForm, userId);
        return "redirect:/user";
    }

}