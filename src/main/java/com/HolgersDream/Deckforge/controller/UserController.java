package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import com.HolgersDream.Deckforge.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/personal/profile")
    public String showProfile(HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("email", currentUser.getEmail());
        return "personal/profile";
    }

    @GetMapping("/personal/deregister")
    public String showDeregister(HttpSession session){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        return "personal/deregister";
    }

    @PostMapping("/personal/deregister")
    public String markToDelete(HttpSession session){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        service.markingToDelete(currentUser.getUserId());
        return "redirect:/logout";
    }
}
