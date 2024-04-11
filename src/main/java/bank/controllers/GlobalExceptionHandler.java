package bank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleSignupLoginExceptions(Exception ex) {
        ModelAndView model = new ModelAndView("html/error");
        model.addObject("exception", ex);
        return model;
    }

}
