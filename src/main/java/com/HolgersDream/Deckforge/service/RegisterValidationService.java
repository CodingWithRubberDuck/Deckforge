package com.HolgersDream.Deckforge.service;

import com.HolgersDream.Deckforge.domain.User;
import com.HolgersDream.Deckforge.exceptions.RegisterValidationException;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegisterValidationService {
    private Pattern emailPattern;

    public void validate(User user){
        validateName(user.getName());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
    }


    /**
     * Navn skal være mellem 2 og 100 tegn
     * Navn kan ikke indeholde mere end 1 tom plads i streg (efter hinanden)
     */
    private void validateName(String name){
        final int MIN_NAME = 2, MAX_NAME = 100;
         if (name.length() < MIN_NAME || name.length() > MAX_NAME) {
            throw new RegisterValidationException("Et navn skal have mellem " + MIN_NAME + " og " + MAX_NAME + " tegn");
            //Tjekker for 2 eller flere white spaces i streg et sted i navnet
        } else if (Pattern.matches(".*\\s{2,}.*", name)) {
            throw new RegisterValidationException("Et navn kan ikke have mere end 1 tom plads (f.eks. mellemrum) i streg noget sted i navnet");
        }
    }


    /**
     * Email skal ligne den generelle idé af en email (regex)
     * eksempel(.mere)@yahoo(.com).au
     *
     * "^[_A-Za-z0-9+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
     *
     * Email kan ikke være længere end 100 tegn
     */
    private void validateEmail(String email){
        final int MAX_EMAIL = 100;
        // Regex nedenunder tjekker for den lokale del (example.more), '@' symbol, domæne (yahoo) og én eller to Tld  (.com, .au, .net) (hvoraf den ene skal være 2+ tegn)
        // example.more@yahoo.com.au
        final String EMAIL_REGEX = "^[_A-Za-z0-9+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        // Sætter Pattern op hvis det ikke allerede er gjort.
        if (emailPattern == null){
            emailPattern = Pattern.compile(EMAIL_REGEX);
        }
        Matcher matcher = emailPattern.matcher(email);
        if (!matcher.matches()){
            throw new RegisterValidationException("Der skal bruges en reel email, f.eks. lokal@domæne.com");
        }

        if (MAX_EMAIL < email.length()){
            throw new RegisterValidationException("En email bør umiddelbart ikke være længere end " + MAX_EMAIL + " tegn");
        }
    }





    /**
     * Kodeord skal være mellem 8 og 120 tegn
     * Kodeord skal indeholde et tal
     * Kodeord skal indeholde et bogstav
     */
    private void validatePassword(String password){
        final int MIN_PASSWORD = 8;
        final int MAX_PASSWORD = 120;
        if (password.length() < MIN_PASSWORD || password.length() > MAX_PASSWORD) {
            throw new RegisterValidationException("Et kode skal have mellem " + MIN_PASSWORD + " og " + MAX_PASSWORD + " tegn");
        } else if (!(Pattern.matches(".*\\d.*", password))) {
            throw new RegisterValidationException("Et kodeord skal af sikkerhedsmæssige årsager indeholde mindst et tal");
        } else if (!checkStringContainsLetter(password)) {
            throw new RegisterValidationException("Et kodeord skal af sikkerhedsmæssige årsager indeholde mindst et bogstav");
        }
    }

    private boolean checkStringContainsLetter(String password){
        for (int i = 0; i < password.length(); i++){
            if (Character.isLetter(password.charAt(i))){
                return true;
            }
        }
        return false;
    }
}
