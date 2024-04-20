package bank.controllers;

import bank.domain.User;
import bank.dto.UserForm;
import bank.dto.UserTransformer;
import bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller("UserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final String USER_PAGE = "html/user";

    @GetMapping
    public String showUser(@SessionAttribute Long userId, Model model) {
        User user = userService.findById(userId);
        UserForm userForm = UserTransformer.convertToUserForm(user);
        model.addAttribute("userForm", userForm);
        model.addAttribute("userId", userId);
        return USER_PAGE;
    }

    @PostMapping("update/{userId}")
    public String updateUser(
            @PathVariable Long userId,
            @ModelAttribute("userForm") @Validated UserForm userForm, BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return USER_PAGE;
        }

        User user = UserTransformer.convertToEntity(userForm, userId);
        userService.update(user);
        return "redirect:/user";
    }

}