package bank.controllers;

import bank.model.domain.User;
import bank.model.services.servicesImpl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;

    @GetMapping
    public String showAuth(User user) {
        return "html/auth";
    }

    @PostMapping("signup")
    public String signupUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "html/auth";
        }

        userService.signup(user);

        return "redirect:/auth";
    }

    @PostMapping("login")
    public String loginUser(User user, HttpSession session) {
        session.setAttribute("userId", userService.login(user).getId());
        return "redirect:/user";
    }

}
