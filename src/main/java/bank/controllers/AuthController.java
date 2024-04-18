package bank.controllers;

import bank.domain.User;
import bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final String AUTH_PAGE = "html/auth";

    @GetMapping
    public String showAuth(Model model) {
        model.addAttribute("user", new User());
        return AUTH_PAGE;
    }

    @PostMapping("signup")
    public String signupUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return AUTH_PAGE;
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
