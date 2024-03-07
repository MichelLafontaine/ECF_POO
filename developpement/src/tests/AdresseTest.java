package tests;

import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * classe des test de l'Objet Adresse
 */
class AdresseTest {

    /**
     * test erreur numero
     * @param invalide String
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "\n", "\r", " ", "trop long123"})
    void numInvalide(String invalide){
        assertThrows(MetierException.class, ()-> {
            new Adresse().setNumero(invalide);
        });
    }

    /**
     * test réussite numéro rue
     * @param valide String
     */
    @ParameterizedTest
    @ValueSource(strings = {"23", "1B", "1 ter"})
    void numValide(String valide){
        assertDoesNotThrow(() -> new Adresse().setNumero(valide));
    }

    /**
     * test erreurs nom rue
     * @param invalide String
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "\n", "\r", " ", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean " +
            "commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis po"})
    void nomInvalide(String invalide){
        assertThrows(MetierException.class, ()-> {
            new Adresse().setNomRue(invalide);
        });
    }

    /**
     * test réussite nom rue
     * @param valide String
     */
    @ParameterizedTest
    @ValueSource(strings = {"rue de la concorde", "place de la république", "chemin de traverse"})
    void nomValide(String valide){
        assertDoesNotThrow(() -> new Adresse().setNomRue(valide));
    }

    /**
     * tests erreurs ville
     * @param invalide String
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "\n", "\r", " ", "Lorem ipsum dolor sit amet, consectetuer adipiscing"})
    void villeInvalide(String invalide){
        assertThrows(MetierException.class, ()-> {
            new Adresse().setVille(invalide);
        });
    }

    /**
     * test réussi ville
     * @param valide String
     */
    @ParameterizedTest
    @ValueSource(strings = {"Pompey", "Frouard", "Grande ville"})
    void villeValide(String valide){
        assertDoesNotThrow(() -> new Adresse().setVille(valide));
    }

    /**
     * tests erreurs code postal
     * @param invalide String
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"1","12","123","1234","123456","123456789"})
    void codePostalInvalide(String invalide){
        assertThrows(MetierException.class, ()-> {
            new Adresse().setCodePostal(invalide);
        });
    }

    /**
     * test réussite code postal
     * @param valide String
     */
    @ParameterizedTest
    @ValueSource(strings = {"12345","54000","88470"})
    void codePostalValide(String valide){
        assertDoesNotThrow(() -> new Adresse().setCodePostal(valide));
    }
}