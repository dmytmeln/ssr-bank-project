package bank.controllers;

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
    private final String SIGNUP_PAGE = "auth-signup";
    private final String LOGIN_PAGE = "auth-login";

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("userLogin", new UserLogin());
        return LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String loginUser(UserLogin userLogin, HttpSession session) {
        session.setAttribute("userId", userService.login(userLogin).getId());
        return "redirect:/user";
    }

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("userForm", new UserForm());
        return SIGNUP_PAGE;
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute @Validated UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_PAGE;
        }

        userService.signup(userForm);
        return "redirect:/auth/login";
    }


}
