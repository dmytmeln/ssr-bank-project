package bank.controllers;

import bank.model.domain.User;
import bank.model.services.servicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller("UserController")
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userService;

    private String formattedDateTime;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(@SessionAttribute Long userId, Model model) {
        User user = userService.findById(userId);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(user.getCreationDate(), ZoneId.of("+2"));
        formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd LLLL yyyy HH:mm"));

        model.addAttribute("user", user);
        model.addAttribute("formattedDate", formattedDateTime);

        return "html/user";
    }

    @PostMapping("update/{userId}")
    public String updateUser(
            Model model,
            @PathVariable Long userId,
            @ModelAttribute("user") @Valid User user,
            BindingResult bindingResult
    ) {
        model.addAttribute("formattedDate", formattedDateTime);
        user.setId(userId);
        if (bindingResult.hasErrors()) {
            return "html/user";
        }

        userService.update(user);

        return "redirect:/user";
    }

}