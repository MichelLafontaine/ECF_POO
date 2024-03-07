package tests;

import com.michel.exceptions.MetierException;
import com.michel.metiers.Societe;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test métier de la classe Societe
 */
class SocieteTest {
    /**
     * tests erreurs de la raison sociale
     * @param invalide String
     */
    @ParameterizedTest
    @EmptySource
    @NullSource
    @ValueSource(strings = {"", "\n", "\r", " ", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
            "Aenean commodo ligula eget dolor. Aenean ma"})
    void raisonSocialeInvalide (String invalide){
        assertThrows(MetierException.class, ()->{
           new Societe().setRaisonSociale(invalide);
        });
    }

    /**
     * tests réussite raison sociale
     * @param valide String
     */
    @ParameterizedTest
    @ValueSource(strings = {"raison Sociale", "Ma Petite Entreprise"})
    void raisonSocialeValide (String valide){
        assertDoesNotThrow(()-> new Societe().setRaisonSociale(valide));
    }

    /**
     * tests erreurs email
     * @param invalide String
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "\n", "\r", "  ", "essai", "essai@test", "essai@test.com."})
    void emailInvalide (String invalide){
        assertThrows(MetierException.class, () -> {
            new Societe().setEmail(invalide);
        });
    }

    /**
     * tests réussite email
     * @param valide String
     */
    @ParameterizedTest
    @ValueSource(strings = {"essai@test.com", "essai@test.fr", "essai.test@reussi.io"})
    void emailValide (String valide) {
        assertDoesNotThrow(() -> new Societe().setEmail(valide));
    }

    /**
     * tests erreurs telephone
     * @param invalide String
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "\n", "\r", "  ", "1234567891", "1236554", "004312346578"})
    void telInvalide (String invalide){
        assertThrows(MetierException.class, () -> {
            new Societe().setTelephone(invalide);
        });
    }

    /**
     * tests réussite telephone
     * @param valide String
     */
    @ParameterizedTest
    @ValueSource(strings = {"0102030405", "0033102030405", "+33102030405"})
    void telValide (String valide) {
        assertDoesNotThrow(() -> new Societe().setTelephone(valide));
    }

}