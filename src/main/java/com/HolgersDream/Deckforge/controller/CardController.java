package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.*;
import com.HolgersDream.Deckforge.service.CardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
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
    public String showAndSearchCollection(@RequestParam(required = false) String name, HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }

        int userId = currentUser.getUserId();

        List<OwnedCard> cards;

        if (name == null || name.isBlank()) {
            // Vis hele samlingen
            cards = service.getUserCollection(userId);
        } else {
            // Søg i samlingen
            cards = service.findOwnedCardByName(userId, name);
        }

        model.addAttribute("cards", cards);

        return "/collection/search";
    }

    @GetMapping("/collection/add")
    public String showAddableCards(HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }

        List<Card> cards = service.getAllCards();

        model.addAttribute("cards", cards);

        return "collection/add";
    }

    @GetMapping("/collection/card/{cardId}")
    public String showCardDetails(@PathVariable int cardId, HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }

        Card card = service.getCardById(cardId); // henter kortet fra card_list
        model.addAttribute("card", card);
        return "/collection/detail-view-all";
    }

    @PostMapping("/collection/addCard")
    public String addCardToCollection(@RequestParam int cardId, HttpSession session) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }

        OwnedCard ownedCard = new OwnedCard();
        ownedCard.setUserId(currentUser.getUserId());
        ownedCard.setCardId(cardId);

        service.addCardToCollection(ownedCard);

        return "redirect:/collection/search";
    }


    @GetMapping("/collection/owned/{ownedCardId}")
    public String showOwnedCardDetails(@PathVariable int ownedCardId, HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }

        OwnedCard ownedCard = service.getOwnedCardById(ownedCardId);
        model.addAttribute("ownedCard", ownedCard);

        return "/collection/detail-view-owned";
    }


    @PostMapping("/collection/remove/{ownedCardId}")
    public String removeOwnedCard(@PathVariable int ownedCardId, HttpSession session) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }

        service.removeCardFromCollection(ownedCardId, currentUser.getUserId());

        return "redirect:/collection/search";
    }

    @GetMapping("/collection/add/search")
    public String searchAllCards(@RequestParam(required = false) String name, HttpSession session, Model model) {
        AuthSessionUser currentUser = (AuthSessionUser) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }

        List<Card> cards;

        if (name == null || name.isBlank()) {
            // Vis hele samlingen
            cards = service.getAllCards();
        } else {
            // Søg i kort
            cards = service.findCardByName(name);
        }

        model.addAttribute("cards", cards);

        return "/collection/add";
    }
}
