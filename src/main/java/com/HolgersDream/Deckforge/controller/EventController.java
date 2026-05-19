package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import com.HolgersDream.Deckforge.domain.Event;
import com.HolgersDream.Deckforge.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EventController {

    private final EventService service;

    public EventController(EventService service){
        this.service = service;
    }

    @GetMapping("/event/base")
    public String showEventBase(HttpSession session) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        return "/event/base";
    }

    @GetMapping("/event/future-events")
    public String showComingEvents(HttpSession session, Model model){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }

        List<Event> events = new ArrayList<>();
        Optional<List<Event>> result = service.findTheComingEvents();
        if (result.isPresent()){
            events = result.get();
        }
        model.addAttribute("events", events);
        return "/event/future-events";
    }


}
