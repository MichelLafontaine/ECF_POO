
import com.michel.controllers.ControllerAccueil;
import com.michel.controllers.ControllerAffichage;
import com.michel.controllers.ControllerFormulaire;
import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.utilitaires.LancerLog;
import com.michel.vues.VueAccueil;
import com.michel.vues.VueFormulaire;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws SQLException, MetierException, DaoException {

        LancerLog.lancerLog();



//            Adresse adresse = new Adresse("123", "rue de la rep", "MAXEVILLE", 54000);
//            Client client = new Client(1, "ma petite", "michel_lafontaine@orange.fr", "0235467894", " ", adresse, 500, 21);
//        DaoClient.createClient(client);
//            ControllerAccueil.initAcceuil();
//        ControllerAffichage.findAll("client");
        ControllerAffichage.affichageInit("client");
    }
}