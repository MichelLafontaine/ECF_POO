package com.michel.metiers;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

import java.time.LocalDate;

public class Prospect extends Societe{

    private LocalDate dateProspect;
    private int interetProspect = -1;

    public LocalDate getDateProspect() {
        return dateProspect;
    }

    public void setDateProspect(LocalDate dateProspect) throws MetierException {
            if (dateProspect.isAfter(LocalDate.now())){
            throw new MetierException("Attention, la date doit être inférieur ou égal à aujourd'hui");
        }
        this.dateProspect = dateProspect;
    }

    public int getInteretProspect() {
        return interetProspect;
    }

    public void setInteretProspect(int interetProspect) throws MetierException {
        if (interetProspect < 0 || interetProspect > 1){
            throw new MetierException("Veuillez definir l'intérêt de votre Prospect");
        }
        this.interetProspect = interetProspect;
    }

    public Prospect() {
    }

    public Prospect(int identifiant, String raisonSociale, String email, String telephone, String commentaire,
                     Adresse adresse, LocalDate dateProspect, int interetProspect) throws MetierException {
        super(identifiant, raisonSociale, email, telephone, commentaire, adresse);
        setDateProspect(dateProspect);
        setInteretProspect(interetProspect);
    }

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
