package com.HolgersDream.Deckforge.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseConnectionException.class)
    public String handleDatabaseConnection(DatabaseConnectionException dce, Model model){
        model.addAttribute("type", "Database fejl");
        model.addAttribute("errorMessage", dce.getMessage());
        return "error";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalStatement(IllegalArgumentException iae, Model model){
        model.addAttribute("type", "Ugyldigt Input");
        model.addAttribute("errorMessage", iae.getMessage());
        return "error";
    }

    @ExceptionHandler(RegisterValidationException.class)
    public String handleRegisterValidation(RegisterValidationException rve, Model model){
        model.addAttribute("type", "Ugyldig registrering");
        model.addAttribute("errorMessage", rve.getMessage());
        return "error";
    }

    @ExceptionHandler(LoginValidationException.class)
    public String handleLoginValidation(LoginValidationException lve, Model model){
        model.addAttribute("type", "ugyldigt login");
        model.addAttribute("errorMessage", lve.getMessage());
        return "error";
    }


}
