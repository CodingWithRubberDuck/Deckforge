package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/personal/profile")
    public String profile() {
        return "personal/profile";
    }
}
