
import com.michel.controllers.ControllerAccueil;
import com.michel.utilitaires.LancerLog;

/**
 * classe lancement programme
 */
public class Main {
    /**
     * lance le log et la page d'accueil
     * @param args
     */
    public static void main(String[] args){

        LancerLog.lancerLog();
        ControllerAccueil.initAcceuil();
    }
}