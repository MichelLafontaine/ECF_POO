package com.michel.metiers;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

import java.time.LocalDate;

public class Prospect extends Societe{

    private LocalDate dateProspect;
    private int interetProspect = -1;

    /**
     * getDateProspect
     * @return
     */
    public LocalDate getDateProspect() {
        return dateProspect;
    }

    /**
     * doit etre inferieur à now()
     * @param dateProspect
     * @throws MetierException
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
     * getInteretProspect
     * @return
     */
    public int getInteretProspect() {
        return interetProspect;
    }

    /**
     * setInteretProspect
     * @param interetProspect
     * @throws MetierException
     */
    public void setInteretProspect(int interetProspect) throws MetierException {
        if (interetProspect < 0 || interetProspect > 1){
            throw new MetierException("Veuillez definir l'intérêt de votre Prospect");
        }
        this.interetProspect = interetProspect;
    }

    public Prospect() {
    }

    /**
     * constructeur sans identifiant
     * @param raisonSociale
     * @param email
     * @param telephone
     * @param commentaire
     * @param adresse
     * @param dateProspect
     * @param interetProspect
     * @throws MetierException
     */
    public Prospect(String raisonSociale, String email, String telephone, String commentaire, Adresse adresse,
                    LocalDate dateProspect, int interetProspect) throws MetierException {
        super(raisonSociale, email, telephone, commentaire, adresse);
        this.dateProspect = dateProspect;
        this.interetProspect = interetProspect;
    }

    /**
     * Prospect tous attributs
     * @param identifiant
     * @param raisonSociale
     * @param email
     * @param telephone
     * @param commentaire
     * @param adresse
     * @param dateProspect
     * @param interetProspect
     * @throws MetierException
     */
    public Prospect(int identifiant, String raisonSociale, String email, String telephone, String commentaire,
                    Adresse adresse, LocalDate dateProspect, int interetProspect) throws MetierException {
        super(identifiant, raisonSociale, email, telephone, commentaire, adresse);
        setDateProspect(dateProspect);
        setInteretProspect(interetProspect);
    }

    /**
     * toString
     * @return
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
