package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.*;
import com.HolgersDream.Deckforge.service.DeckService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DeckController {

    private final DeckService service;
    private final SessionObjectRetriever sessionObjectRetriever;

    public DeckController(DeckService service, SessionObjectRetriever sessionObjectRetriever) {
        this.service = service;
        this.sessionObjectRetriever = sessionObjectRetriever;
    }

    @GetMapping("/deck/personal-decks")
    public String getPersonalDecks(HttpSession session, Model model) {
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null) {
            return "redirect:/authentication/login";
        }
        List<Deck> decks = service.getUserDecks(currentUser.getUserId());

        model.addAttribute("decks", decks);
        return "/deck/personal-decks";
    }

    @GetMapping("/deck/add-deck")
    public String showAddDeck(HttpSession session, Model model){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        model.addAttribute("deck", new DeckRequest());
        model.addAttribute("format", Format.values());
        return "deck/add-deck";
    }

    @PostMapping("/deck/add-deck")
    public String tryToAddDeck(HttpSession session, @ModelAttribute DeckRequest deckRequest){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        deckRequest.setUserId(currentUser.getUserId());
        service.checkAddDeck(deckRequest);
        return "redirect:/deck/personal-decks";
    }

    @GetMapping("/deck/deck-details/{deckId}")
    public String showPersonalDeck(@PathVariable int deckId, HttpSession session, Model model){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        Deck personalDeck = service.getPersonalDeckAndCards(deckId, currentUser.getUserId());

        model.addAttribute("deck", personalDeck);
        model.addAttribute("cards", personalDeck.getCards());

        return "deck/deck-details";
    }

    @GetMapping("/deck/add-cards-list/{deckId}")
    public String searchCardsForAdd(@PathVariable int deckId, @RequestParam(required = false) String name, HttpSession session, Model model){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        Deck personalOnlyDeck = service.getOnlyPersonalDeck(deckId, currentUser.getUserId());

        List<Card> cards;

        if (name == null || name.isBlank()) {
            // Vis hele samlingen
            cards = service.getAllCardsForDeck();
        } else {
            // Søg i kort
            cards = service.getCardsForDeckByName(name);
        }
        model.addAttribute("deck", personalOnlyDeck);
        model.addAttribute("cards", cards);

        return "deck/add-cards-list";
    }

    @GetMapping("/deck/add-card-detail/{deckId}/{cardId}")
    public String showDetailAddCardToDeck(@PathVariable int deckId, @PathVariable int cardId, HttpSession session, Model model){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        Deck personalDeck = service.getPersonalDeckAndCards(deckId, currentUser.getUserId());

        Card newCard = service.getCardForDeck(cardId);

        model.addAttribute("deck", personalDeck);
        model.addAttribute("card", newCard);
        model.addAttribute("canAddCommander", service.checkAllowedToBeCommander(personalDeck, newCard));

        return "deck/add-card-detail";
    }


    @PostMapping("/deck/add-card-commander/{deckId}/{cardId}")
    public String tryAddCommanderToDeck(@PathVariable int deckId, @PathVariable int cardId, HttpSession session, RedirectAttributes redirectAttributes){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }

        Card newCard = service.checkAddCommanderToDeck(deckId, currentUser.getUserId(), cardId);

        redirectAttributes.addFlashAttribute("confirmMessage", "Du har tilføjet kortet '" + newCard.getName() + "' som commander til dit deck");
        return "redirect:/deck/add-cards-list/" + deckId;
    }


    @PostMapping("/deck/add-card-generic/{deckId}/{cardId}")
    public String tryAddGenericToDeck(@PathVariable int deckId, @PathVariable int cardId, HttpSession session, RedirectAttributes redirectAttributes){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }

        Card newCard = service.checkAddGenericToDeck(deckId, currentUser.getUserId(), cardId);

        redirectAttributes.addFlashAttribute("confirmMessage", "Du har tilføjet kortet '" + newCard.getName() + "' til dit deck");
        return "redirect:/deck/add-cards-list/" + deckId;
    }

    @GetMapping("/deck/remove-card-detail/{deckId}/{deckContainId}")
    public String showDetailRemoveCardFromDeck(@PathVariable int deckId, @PathVariable int deckContainId, HttpSession session, Model model){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }
        Deck personalDeck = service.getOnlyPersonalDeck(deckId, currentUser.getUserId());

        DeckCard deckCard = service.getDeckCard(deckContainId, deckId);

        model.addAttribute("deck", personalDeck);
        model.addAttribute("deckCard", deckCard);

        return "deck/remove-card-detail";
    }

    @PostMapping("/deck/remove-card-detail/{deckId}/{deckContainId}")
    public String tryDeleteCardFromDeck(@PathVariable int deckId, @PathVariable int deckContainId, HttpSession session, RedirectAttributes redirectAttributes){
        AuthSessionUser currentUser = sessionObjectRetriever.getSessionUser(session);
        if (currentUser == null){
            return "redirect:/authentication/login";
        }

        DeckCard deckCardForRemoval = service.checkDeleteCardFromDeck(deckId, currentUser.getUserId(), deckContainId);

        redirectAttributes.addFlashAttribute("confirmMessage", "Du har fjernet kortet '" + deckCardForRemoval.getName() + "' fra dit deck");
        return "redirect:/deck/deck-details/" + deckId;
    }

}
