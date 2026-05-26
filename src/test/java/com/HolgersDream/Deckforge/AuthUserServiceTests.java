package com.HolgersDream.Deckforge;

import com.HolgersDream.Deckforge.controller.AuthRequest;
import com.HolgersDream.Deckforge.exceptions.LoginValidationException;
import com.HolgersDream.Deckforge.exceptions.RegisterValidationException;
import com.HolgersDream.Deckforge.service.AuthUserService;
import com.HolgersDream.Deckforge.service.RegisterValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthUserServiceTests {
    private AuthUserService service;

    @BeforeEach
    void setUp() {
        service = new AuthUserService(new FakeAuthUserRepository(), new RegisterValidationService());
    }

    @Test
    void checkRegister_success(){
        String repeatedPassword = "a1234567";
        AuthRequest authRequest = new AuthRequest();
        authRequest.setName("aa");
        authRequest.setEmail("1@1.aa");
        authRequest.setPassword(repeatedPassword);

        assertDoesNotThrow(()->service.checkRegister(authRequest, repeatedPassword));
    }

    @Test
    void checkRegister_shouldThrowWhenEmailAlreadyInSystem(){
        String repeatedPassword = "a1234567";
        AuthRequest authRequest = new AuthRequest();
        authRequest.setName("aa");
        authRequest.setEmail("1@1.aa");
        authRequest.setPassword(repeatedPassword);
        service.checkRegister(authRequest, repeatedPassword);

        assertThrows(RegisterValidationException.class, ()-> service.checkRegister(authRequest, repeatedPassword));
    }

    @Test
    void checkLogin_success(){
        String repeatedPassword = "a1234567";
        AuthRequest authRequest = new AuthRequest();
        authRequest.setName("aa");
        authRequest.setEmail("1@1.aa");
        authRequest.setPassword(repeatedPassword);
        service.checkRegister(authRequest, repeatedPassword);

        assertDoesNotThrow(()-> service.checkLogin(authRequest));
    }

    @Test
    void checkLogin_shouldThrowWhenWrongEmail(){
        String repeatedPassword = "a1234567";
        AuthRequest authRequest = new AuthRequest();
        authRequest.setName("aa");
        authRequest.setEmail("1@1.aa");
        authRequest.setPassword(repeatedPassword);
        service.checkRegister(authRequest, repeatedPassword);

        //Forkert email
        authRequest.setEmail("2@2.bb");

        assertThrows(LoginValidationException.class, () -> service.checkLogin(authRequest));
    }


    @Test
    void checkLogin_shouldThrowWhenWrongPassword(){
        String repeatedPassword = "a1234567";
        AuthRequest authRequest = new AuthRequest();
        authRequest.setName("aa");
        authRequest.setEmail("1@1.aa");
        authRequest.setPassword(repeatedPassword);
        service.checkRegister(authRequest, repeatedPassword);

        //Forkert kodeord
        authRequest.setPassword("7654321z");

        assertThrows(LoginValidationException.class, () -> service.checkLogin(authRequest));
    }


}
