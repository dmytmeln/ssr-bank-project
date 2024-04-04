package bank.controllers;

import bank.model.domain.User;
import bank.model.services.servicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserServiceImpl userService;
    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

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
        session.setAttribute("user", userService.login(user));
        return "redirect:/user";
    }

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleSignupLoginExceptions(PersistenceException ex) {
        ModelAndView model = new ModelAndView("html/error");
        model.addObject("exception", ex);
        return model;
    }

}
