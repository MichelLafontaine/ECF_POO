package tests;

import com.michel.exceptions.MetierException;
import com.michel.metier.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.opentest4j.MultipleFailuresError;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @ParameterizedTest
    @ValueSource (doubles = {100.78, -500, 200})
    void chiffreAffaireInvalide (double invalide){
        assertThrows(MetierException.class, ()-> {
            new Client().setChiffreAffaire(invalide);
        });
    }

    @ParameterizedTest
    @ValueSource (doubles = {200.01, 15000, 1000000.54})
    void chiffreAffaireValide (double valide){
        assertDoesNotThrow(()-> {new Client().setChiffreAffaire(valide);});
    }

    @ParameterizedTest
    @ValueSource(ints = {-12, 0, -500})
    void nbreEmployeInvalide (int invalide){
        assertThrows(MetierException.class, ()->{
            new Client().setNbreEmploye(invalide);
        });
    }

    @ParameterizedTest
    @ValueSource (ints = {1, 150, 100000})
    void nbreEmployeValide (int valide){
        assertDoesNotThrow(()->{new Client().setNbreEmploye(valide);});
    }
}