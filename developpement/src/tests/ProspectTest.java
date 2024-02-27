package tests;

import com.michel.exceptions.MetierException;
import com.michel.metier.Prospect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProspectTest {

    @ParameterizedTest
    @ValueSource(ints = {-1,2,3,-5})
    void interetProspectInvalide(int invalide){
        assertThrows(MetierException.class, ()->{
           new Prospect().setInteretProspect(invalide);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1})
    void interetProspectValide (int valide){
        assertDoesNotThrow(()->{new Prospect().setInteretProspect(valide);});
    }

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