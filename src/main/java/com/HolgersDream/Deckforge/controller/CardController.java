package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.config.DatabaseConfig;
import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import com.HolgersDream.Deckforge.domain.Deck;
import com.HolgersDream.Deckforge.domain.Format;
import com.HolgersDream.Deckforge.service.CardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CardController {

    private final CardService service;

    public CardController(CardService service, DatabaseConfig databaseConfig) {
        this.service = service;
    }

    @GetMapping("/deck/personal-decks")
    public String getPersonalDecks(HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }
        List<Deck> decks = new ArrayList<>();
        Optional<List<Deck>> result = service.getUserDecks(currentUser.getUserId());
        if (result.isPresent()){
            decks = result.get();
        }
        model.addAttribute("decks", decks);
        return "/deck/personal-decks";
    }

    @GetMapping("/deck/add-deck")
    public String showAddDeck(HttpSession session, Model model){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        model.addAttribute("deck", new DeckRequest());
        model.addAttribute("format", Format.values());
        return "deck/add-deck";
    }

    @PostMapping("/deck/add-deck")
    public String tryToAddDeck(HttpSession session, @ModelAttribute DeckRequest deckRequest){
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        deckRequest.setUserId(currentUser.getUserId());
        service.checkAddDeck(deckRequest);
        return "redirect:/deck/personal-decks";
    }




    @GetMapping("/collection/search")
    public String searchCollection() {
        return "/collection/search";
    }
}
