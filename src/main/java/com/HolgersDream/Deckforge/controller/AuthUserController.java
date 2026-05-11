package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import com.HolgersDream.Deckforge.service.AuthUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthUserController {

    private final AuthUserService service;

    public AuthUserController(AuthUserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/authentication/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("auth", new AuthRequest());
        return "authentication/register";
    }

    @PostMapping("/authentication/register")
    public String tryToRegister(@ModelAttribute AuthRequest authRequest) {
        service.checkRegister(authRequest);
        return "redirect:/authentication/login";
    }

    @GetMapping("/authentication/login")
    public String showLoginForm(Model model) {
        model.addAttribute("auth", new AuthRequest());
        return "authentication/login";
    }

    @PostMapping("/authentication/login")
    public String tryToLogin(@ModelAttribute AuthRequest authRequest, HttpSession session, Model model) {
        AuthSessionUser loggedIn = service.checkLogin(authRequest);
        session.setAttribute("currentUser", loggedIn);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String showWelcome(HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/return")
    public String returnBack(HttpSession session) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/";
        } else {
            return "redirect:/welcome";
        }
    }
}
