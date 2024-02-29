import com.michel.dao.DaoClient;
import com.michel.dao.DaoConnection;
import com.michel.dao.DaoProspect;
import com.michel.dao.DaoUtilitaires;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.utilitaires.LancerLog;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        LancerLog.lancerLog();

//        LocalDate date = LocalDate.of(2005,12,21);
//        Societe societe = new Societe(1, "ma petite entreprise", "dsqfsdf", "1235467894", " ", adresse);
//        Societe prospect = new Prospect(2, "rs", "essai@test.fr", "0234567891", " ", adresse, date, 1);
//
//        System.out.println(prospect.toString());

        try{
//            Adresse adresse = new Adresse("123", "rue de la rep", "MAXEVILLE", 54000);
//            System.out.println(DaoUtilitaires.creerAdresse(adresse));
//            Client client = new Client(1, "ma petite", "michel_lafontaine@orange.fr", "0235467894", " ", adresse, 500, 21);
//            System.out.println(client);
//            DaoClient.deleteClient(4);
            System.out.println(DaoProspect.findAllProspect());
        } catch (MetierException metierException){
            System.out.println("Erreur de Saise : " + metierException.getMessage());
        }
        catch (Exception e){

        }

    }
}