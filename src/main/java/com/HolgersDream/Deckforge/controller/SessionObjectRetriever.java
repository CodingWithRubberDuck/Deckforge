package com.HolgersDream.Deckforge.controller;

import com.HolgersDream.Deckforge.domain.AuthSessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;


@Component
public class SessionObjectRetriever {
    public AuthSessionUser getSessionUser(HttpSession session){
        return (AuthSessionUser) session.getAttribute("currentUser");
    }
}
