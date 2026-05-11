package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.service.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EventController {

    private final EventService service;

    public EventController(EventService service){
        this.service = service;
    }

    @GetMapping("/event/search")
    public String search() {
        return "/event/search";
    }
}
