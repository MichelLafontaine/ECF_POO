package tests;

import com.michel.exceptions.MetierException;
import com.michel.metiers.Prospect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test métier de la classe Prospect
 */
class ProspectTest {
    /**
     * tests erreurs de l'interet prospect
     * @param invalide int
     */
    @ParameterizedTest
    @ValueSource(ints = {-1,2,3,-5})
    void interetProspectInvalide(int invalide){
        assertThrows(MetierException.class, ()->{
           new Prospect().setInteretProspect(invalide);
        });
    }

    /**
     * test réussite de l'interet prospect
     * @param valide int
     */
    @ParameterizedTest
    @ValueSource(ints = {0,1})
    void interetProspectValide (int valide){
        assertDoesNotThrow(()->{new Prospect().setInteretProspect(valide);});
    }

    /**
     * tests de la date prospection
     */
    @Test
    public void testDateProspect() {
        LocalDate dateBeforeToday = LocalDate.now().minusDays(1);
        assertDoesNotThrow(()->{new Prospect().setDateProspect(dateBeforeToday);});

        LocalDate today = LocalDate.now();
        assertDoesNotThrow(()->{new Prospect().setDateProspect(today);});

        LocalDate dateAfterToday = LocalDate.now().plusDays(1);
        assertThrows(MetierException.class,()-> {
            new Prospect().setDateProspect(dateAfterToday);
        });
    }

}