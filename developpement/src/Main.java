
import com.michel.controllers.ControllerAccueil;
import com.michel.controllers.ControllerAffichage;
import com.michel.controllers.ControllerFormulaire;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.utilitaires.LancerLog;
import com.michel.vues.VueAccueil;
import com.michel.vues.VueFormulaire;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws SQLException, MetierException, DaoException {

        LancerLog.lancerLog();

//        LocalDate date = LocalDate.of(2005,12,21);
//        Societe societe = new Societe(1, "ma petite entreprise", "dsqfsdf", "1235467894", " ", adresse);
//        Societe prospect = new Prospect(2, "rs", "essai@test.fr", "0234567891", " ", adresse, date, 1);
//
//        System.out.println(prospect.toString());


//            Adresse adresse = new Adresse("123", "rue de la rep", "MAXEVILLE", 54000);
//            System.out.println(DaoUtilitaires.creerAdresse(adresse));
//            Client client = new Client(1, "ma petite", "michel_lafontaine@orange.fr", "0235467894", " ", adresse, 500, 21);
//            System.out.println(client);
//            DaoClient.deleteClient(4);
//            System.out.println(DaoProspect.findAllProspect());
//            System.out.println(ControllerAccueil.listeSociete("prospect"));
            ControllerAccueil.initAcceuil();

    }
}