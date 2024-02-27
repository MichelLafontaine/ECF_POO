package tests;

import com.michel.exceptions.MetierException;
import com.michel.metier.Adresse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AdresseTest {


    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "\n", "\r", " ", "trop long123"})
    void numInvalide(String invalide){
        assertThrows(MetierException.class, ()-> {
            new Adresse().setNumero(invalide);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"23", "1B", "1 ter"})
    void numValide(String valide){
        assertDoesNotThrow(() -> new Adresse().setNumero(valide));
    }

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

    @ParameterizedTest
    @ValueSource(strings = {"rue de la concorde", "place de la république", "chemin de traverse"})
    void nomValide(String valide){
        assertDoesNotThrow(() -> new Adresse().setNomRue(valide));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "\n", "\r", " ", "Lorem ipsum dolor sit amet, consectetuer adipiscing"})
    void villeInvalide(String invalide){
        assertThrows(MetierException.class, ()-> {
            new Adresse().setVille(invalide);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pompey", "Frouard", "Grande ville"})
    void villeValide(String valide){
        assertDoesNotThrow(() -> new Adresse().setVille(valide));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,12,123,1234,123456,123456789})
    void codePostalInvalide(int invalide){
        assertThrows(MetierException.class, ()-> {
            new Adresse().setCodePostal(invalide);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {12345,54000,88470})
    void codePostalValide(int valide){
        assertDoesNotThrow(() -> new Adresse().setCodePostal(valide));
    }
}