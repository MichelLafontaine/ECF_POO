package com.michel.metiers;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

import java.time.LocalDate;

/**
 * classe fille de la classe societe
 */
public class Prospect extends Societe{

    private LocalDate dateProspect;
    private int interetProspect = -1;

    /**
     * retourne date prospection
     * @return LocalDate date prospection
     */
    public LocalDate getDateProspect() {
        return dateProspect;
    }

    /**
     * vérification/initialisation de la date
     * @param dateProspect LocalDate date de prospection
     * @throws MetierException > aujourd'hui ou = 1900,1,1
     */
    public void setDateProspect(LocalDate dateProspect) throws MetierException {
        if (dateProspect.isAfter(LocalDate.now())){
            throw new MetierException("Attention, la date doit être inférieur ou égal à aujourd'hui");
        }
        if (dateProspect.isEqual(LocalDate.of(1900,1,1))){
            throw new MetierException("Attention la date n'est pas bonne");
        }
        this.dateProspect = dateProspect;
    }

    /**
     * reourne interet prospect
     * @return int interet 0 ou 1
     */
    public int getInteretProspect() {
        return interetProspect;
    }

    /**
     * vérification/initialisation de l'interet prospect
     * @param interetProspect int 0 ou 1
     * @throws MetierException di != 0 ou 1
     */
    public void setInteretProspect(int interetProspect) throws MetierException {
        if (interetProspect < 0 || interetProspect > 1){
            throw new MetierException("Veuillez definir l'intérêt de votre Prospect");
        }
        this.interetProspect = interetProspect;
    }

    /**
     * constructeur sans attribut
     */
    public Prospect() {
    }

    /**
     * constructeur sans attribut identifiant
     * @param raisonSociale String non null
     * @param email String non null
     * @param telephone String non null
     * @param commentaire String non null
     * @param adresse Object adresse
     * @param dateProspect LocalDate
     * @param interetProspect 0 ou 1
     * @throws MetierException propagation
     */
    public Prospect(String raisonSociale, String email, String telephone, String commentaire, Adresse adresse,
                    LocalDate dateProspect, int interetProspect) throws MetierException {
        super(raisonSociale, email, telephone, commentaire, adresse);
        setDateProspect(dateProspect);
        setInteretProspect(interetProspect);
    }

    /**
     * Prospect tous attributs
     * @param identifiant int
     * @param raisonSociale String non null
     * @param email String non null
     * @param telephone String non null
     * @param commentaire String non null
     * @param adresse Object adresse
     * @param dateProspect LocalDate
     * @param interetProspect int 0 ou 1
     * @throws MetierException propagation
     */
    public Prospect(int identifiant, String raisonSociale, String email, String telephone, String commentaire,
                    Adresse adresse, LocalDate dateProspect, int interetProspect) throws MetierException {
        super(identifiant, raisonSociale, email, telephone, commentaire, adresse);
        setDateProspect(dateProspect);
        setInteretProspect(interetProspect);
    }

    /**
     *surcharge toString
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder phrase = new StringBuilder();
        phrase.append("L'entreprise ").append(super.getRaisonSociale()).append("est un prospect.")
                .append("\nElle a pour identifiant : ").append(super.getIdentifiant())
                .append("\n").append(super.getAdresse().toString())
                .append("\nLe mail de contact est : ").append(super.getEmail())
                .append("\nLe numéro de téléphone est : ").append(super.getTelephone());
        if (interetProspect == 1){
            phrase.append("\nElle est intéréssé par nos services");
        } else {
            phrase.append("\nElle n'est pas intéréssé par nos services");
        }
        phrase.append("\nNous nous sommes rencontré le ").append(dateProspect.format(Utilitaires.formatDate()));
        phrase.append("\nCommentaire : ").append(super.getCommentaire());

        return phrase.toString();
    }
}
