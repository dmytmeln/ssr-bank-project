package bank.controllers;

import bank.model.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("auth")
public class AuthController {

    @GetMapping
    public String showAuth(User user) {
        return "html/auth";
    }

    @PostMapping("signup")
    public String signupUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "html/auth";
        }

        return "redirect:/";
    }

    @PostMapping("login")
    public String loginUser(User user) {
        return "redirect:/";
    }

}
