package tests;

import com.michel.exceptions.MetierException;
import com.michel.metiers.Client;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test métier de la classe Client
 */
class ClientTest {
    /**
     * tests erreurs sur chiffre d'affaire
     * @param invalide Double
     */
    @ParameterizedTest
    @ValueSource (doubles = {100.78, -500, 200})
    void chiffreAffaireInvalide (double invalide){
        assertThrows(MetierException.class, ()-> {
            new Client().setChiffreAffaire(invalide);
        });
    }

    /**
     * tests réussite sur chiffre d'affaire
     * @param valide Double
     */
    @ParameterizedTest
    @ValueSource (doubles = {200.01, 15000, 1000000.54})
    void chiffreAffaireValide (double valide){
        assertDoesNotThrow(()-> {new Client().setChiffreAffaire(valide);});
    }

    /**
     * tests erreurs sur le nombre d'employé
     * @param invalide int
     */
    @ParameterizedTest
    @ValueSource(ints = {-12, 0, -500})
    void nbreEmployeInvalide (int invalide){
        assertThrows(MetierException.class, ()->{
            new Client().setNbreEmploye(invalide);
        });
    }

    /**
     * tests réussites sur le nombre d'employé
     * @param valide int
     */
    @ParameterizedTest
    @ValueSource (ints = {1, 150, 100000})
    void nbreEmployeValide (int valide){
        assertDoesNotThrow(()->{new Client().setNbreEmploye(valide);});
    }
}