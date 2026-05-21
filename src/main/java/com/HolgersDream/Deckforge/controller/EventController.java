package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import com.HolgersDream.Deckforge.domain.Event;
import com.HolgersDream.Deckforge.domain.Role;
import com.HolgersDream.Deckforge.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String showEventBase(HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        model.addAttribute("role", currentUser.getRole());
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

    @GetMapping("/event/registered-events")
    public String showRegisteredEvents(HttpSession session, Model model){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }

        List<Event> events = new ArrayList<>();
        Optional<List<Event>> result = service.findTheRegisteredEvents(currentUser.getUserId());
        if (result.isPresent()){
            events = result.get();
        }
        model.addAttribute("events", events);
        return "/event/registered-events";
    }


    @GetMapping("/event/add-event")
    public String addNewEvent(HttpSession session, Model model){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        Role userRole = currentUser.getRole();
        if (userRole != Role.ORGANIZER && userRole != Role.ADMIN ) {
            return "redirect:/event/base";
        }

        model.addAttribute("event", new EventRequest());
        return "/event/add-event";
    }

    @PostMapping("/event/add-event")
    public String tryToAddEvent(HttpSession session, @ModelAttribute EventRequest eventRequest){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        service.checkAddEvent(eventRequest, currentUser);
        return "redirect:/event/base";
    }

    @GetMapping("/event/details/{eventId}")
    public String showSpecificEvent(@PathVariable int eventId, HttpSession session, Model model){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        Event event = service.getSpecificEvent(eventId);

        boolean isParticipating = service.checkAlreadyParticipant(currentUser.getUserId(), event.getParticipants());

        model.addAttribute("event", event);
        model.addAttribute("isParticipating", isParticipating);

        return "event/details";
    }


}
