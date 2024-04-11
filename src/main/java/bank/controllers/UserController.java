package bank.controllers;

import bank.model.domain.User;
import bank.model.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller("UserController")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final String USER_PAGE = "html/user";

    @GetMapping
    public String showUser(@SessionAttribute Long userId, Model model) {
        User user = userService.findById(userId);

        model.addAttribute("user", user);

        return USER_PAGE;
    }

    @PostMapping("update/{userId}")
    public String updateUser(
            @PathVariable Long userId,
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult,
            HttpSession session
    ) {
        session.setAttribute("userId", userId);
        user.setId(userId);
        if (bindingResult.hasErrors()) {
            return USER_PAGE;
        }

        userService.update(user);

        return "redirect:/user";
    }

}