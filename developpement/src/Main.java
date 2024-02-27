import com.michel.exceptions.MetierException;
import com.michel.metier.Adresse;
import com.michel.metier.Prospect;
import com.michel.metier.Societe;
import com.michel.utilitaires.LancerLog;
import com.michel.utilitaires.LoggerReverso;
import com.michel.utilitaires.Utilitaires;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws MetierException, ParseException {

        LancerLog.lancerLog();
        Adresse adresse = new Adresse("123", "rue de la rep", "Chezmoi", 54000);
        LocalDate date = LocalDate.of(2005,12,21);
//        Societe societe = new Societe(1, "ma petite entreprise", "dsqfsdf", "1235467894", " ", adresse);
        Societe prospect = new Prospect(2, "rs", "essai@test.fr", "0234567891", " ", adresse, date, 1);

        System.out.println(prospect.toString());


    }
}