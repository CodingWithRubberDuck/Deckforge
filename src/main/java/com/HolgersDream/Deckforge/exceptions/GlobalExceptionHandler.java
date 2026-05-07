package com.HolgersDream.Deckforge.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseConnectionException.class)
    public String handleDatabaseConnection(DatabaseConnectionException dce, Model model){
        model.addAttribute("type", "Database fejl");
        model.addAttribute("errorMessage", dce.getMessage());
        return "error";
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccess(DataAccessException dae, Model model){
        model.addAttribute("type", "Data-adgangs fejl");
        model.addAttribute("errorMessage", dae.getMessage());
        return "error";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalStatement(IllegalArgumentException iae, Model model){
        model.addAttribute("type", "Ugyldigt Input");
        model.addAttribute("errorMessage", iae.getMessage());
        return "error";
    }

    @ExceptionHandler(RegisterValidationException.class)
    public String handleRegisterValidation(RegisterValidationException rve, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("responseMessage", rve.getMessage());
        return "redirect:/authentication/register";
    }

    @ExceptionHandler(LoginValidationException.class)
    public String handleLoginValidation(LoginValidationException lve, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("responseMessage", lve.getMessage());
        return "redirect:/authentication/login";
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFound(NoResourceFoundException nrfe, Model model){
        model.addAttribute("type", "Ukendt side");
        model.addAttribute("errorMessage", "Siden eller resourcen du prøvede at få fat på virker ikke til at eksistere");
        return "error";
    }



    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model){
        model.addAttribute("type", "Ukendt fejl");
        model.addAttribute("errorMessage", "Det er ukendt hvad der gik galt");
        return "error";
    }


}
