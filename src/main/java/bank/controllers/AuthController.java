package bank.controllers;

import bank.domain.User;
import bank.dto.UserForm;
import bank.dto.UserLogin;
import bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("userLogin", new UserLogin());
        return AUTH_PAGE;
    }

    @PostMapping("signup")
    public String signupUser(@ModelAttribute @Validated UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return AUTH_PAGE;
        }

        userService.signup(userForm);
        return "redirect:/auth";
    }

    @PostMapping("login")
    public String loginUser(UserLogin userLogin, HttpSession session) {
        session.setAttribute("userId", userService.login(userLogin).getId());
        return "redirect:/user";
    }

}
