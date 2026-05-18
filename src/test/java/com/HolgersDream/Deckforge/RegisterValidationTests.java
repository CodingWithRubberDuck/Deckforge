package com.HolgersDream.Deckforge;

import com.HolgersDream.Deckforge.domain.Role;
import com.HolgersDream.Deckforge.domain.User;
import com.HolgersDream.Deckforge.exceptions.RegisterValidationException;
import com.HolgersDream.Deckforge.service.RegisterValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegisterValidationTests {
    private RegisterValidationService registerValidation;

    @BeforeEach
    void setUp() {
        registerValidation = new RegisterValidationService();
    }

    //Baseline test
    @Test
    void registerValidation_minimumSuccessTest(){
        User minimumSuccessUser = new User(
                1,
                "aa",
                "1@1.aa",
                "a1234567",
                Role.USER);

        String repeatedPassword = "a1234567";
        assertDoesNotThrow(()->registerValidation.validate(minimumSuccessUser, repeatedPassword));
    }




    /**
     * Navn skal være mellem 2 og 100 tegn
     * Navn kan ikke indeholde mere end 1 tom plads i streg (efter hinanden)
     */

    //Navn
    @Test
    void registerValidation_shouldThrowWhenNameMultipleWhiteSpace(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "a  a",
                "1@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }


    @Test
    void registerValidation_shouldThrowNameWhenTooShort(){
        String repeatedPassword = "a1234567";
        User testUser = new User(
                1,
                "a",
                "1@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowNameWhenTooLong(){
        //Tester at 101 tegn fejler
        String repeatedPassword = "a1234567";
        User testUser = new User(
                1,
                "1234567890"+ //1
                "1234567890" +      //2
                "1234567890" +      //3
                "1234567890" +      //4
                "1234567890" +      //5
                "1234567890" +      //6
                "1234567890" +      //7
                "1234567890" +      //8
                "1234567890" +      //9
                "1234567890" +      //10
                "1",
                "1@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }




    /**
     * Email skal ligne den generelle idé af en email (regex)
     * eksempel(.mere)@yahoo(.com).au
     *
     * "^[_A-Za-z0-9+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
     *
     * Email kan ikke være længere end 100 tegn
     *
     */

    //Email
    @Test
    void registerValidation_shouldAcceptEmailOptionalParts(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "aa",
                "1.1@1.1.aa",
                repeatedPassword,
                Role.USER);

        assertDoesNotThrow(()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowEmailWhenNoLocalPart(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "aa",
                "@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowEmailWhenNoAtSign(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "aa",
                "11.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowEmailWhenNoPeriod(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "aa",
                "1@1aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowEmailWhenTopLevelDomainTooShort(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "aa",
                "1@1.a",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowEmailWhenTopLevelDomainNotLetters(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "aa",
                "1@1.11",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowEmailWhenTooLong(){
        String repeatedPassword = "a1234567";

        User testUser = new User(
                1,
                "aa",
                "1@1.aa" +          //6
                        "abcdefghij" +    //16
                        "abcdefghij" +    //26
                        "abcdefghij" +    //36
                        "abcdefghij" +    //46
                        "abcdefghij" +    //56
                        "abcdefghij" +    //66
                        "abcdefghij" +    //76
                        "abcdefghij" +    //86
                        "abcdefghij" +    //96
                        "abcde",          //101
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }





    /**
     * Kodeord skal være mellem 8 og 120 tegn
     * Kodeord skal indeholde et tal
     * Kodeord skal indeholde et bogstav
     */

    //Kodeord
    @Test
    void registerValidation_shouldThrowPasswordWhenNotContainNumber(){
        String repeatedPassword = "abcdefgh";
        User testUser = new User(
                1,
                "aa",
                "1@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowPasswordWhenNotContainLetter(){
        String repeatedPassword = "12345678";
        User testUser = new User(
                1,
                "aa",
                "1@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowPasswordWhenBothNotEqual(){
        String repeatedPassword = "a1234567";
        User testUser = new User(
                1,
                "aa",
                "1@1.aa",
                "7654321a",
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowPasswordWhenTooShort(){
        String repeatedPassword = "a123456";
        User testUser = new User(
                1,
                "aa",
                "1@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

    @Test
    void registerValidation_shouldThrowPasswordWhenTooLong(){
        //Kaster exception ved mere end 120 tegn
        String repeatedPassword = "a" +  //1
                "1234567890" +           //11
                "1234567890" +           //21
                "1234567890" +           //31
                "1234567890" +           //41
                "1234567890" +           //51
                "1234567890" +           //61
                "1234567890" +           //71
                "1234567890" +           //81
                "1234567890" +           //91
                "1234567890" +           //101
                "1234567890" +           //111
                "1234567890"             //121
                ;
        User testUser = new User(
                1,
                "aa",
                "1@1.aa",
                repeatedPassword,
                Role.USER);

        assertThrows(RegisterValidationException.class, ()-> registerValidation.validate(testUser, repeatedPassword));
    }

}
