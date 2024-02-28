import com.michel.dao.DaoClient;
import com.michel.dao.DaoConnection;
import com.michel.dao.DaoUtilitaires;
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
            Connection connection = DaoConnection.getInstance();
            Client client = DaoClient.findByNameClient("AFPA");
            System.out.println(client.getAdresse().getCodePostal());
//            DaoUtilitaires.creerAdresse(adresse);
        }catch (SQLException sqlE){

        } catch (Exception e){

        }

    }
}