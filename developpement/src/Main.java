
import com.michel.controllers.ControllerAccueil;
import com.michel.controllers.ControllerAffichage;
import com.michel.controllers.ControllerFormulaire;
import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.ControllerException;
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
    public static void main(String[] args) {

        LancerLog.lancerLog();
        ControllerAccueil.initAcceuil();
    }
}