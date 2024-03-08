package com.michel.utilitaires;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * classe pour lancer le log
 */
public class LancerLog {

    private LancerLog(){}
    /**
     * lancerLog
     */
    public static void lancerLog (){
        FileHandler fh;
        try {

            // nom du fichier : logReverso
            fh = new FileHandler("log_REVERSO.log", true);

            // Le handler "console" est par défault. Il faut enlever tous les
            // handlers puis mettre celui pour le fichier
            LoggerReverso.LOGGER.setUseParentHandlers(false);
            LoggerReverso.LOGGER.addHandler(fh);

            // On passe en paramètre un objet de la classe FormatterLog
            fh.setFormatter(new FormatterLog());
            LoggerReverso.LOGGER.log(Level.INFO, "début pg");
        }
        //Pas de fichier log, le programme se ferme
        catch (IOException | SecurityException fe) {
            StringBuilder messageLog = new StringBuilder("message d'erreur fichier  ");
            messageLog.append(fe.getMessage()).append(" ").append(fe);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
            System.exit(1);
        }

        catch (Exception e ) {
            StringBuilder messageLog = new StringBuilder("message de la classe Exception  ");
            messageLog.append(e.getMessage()).append(" ").append(e);
            LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
        }
    }
}
