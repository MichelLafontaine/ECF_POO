package com.michel.metiers;

import com.michel.exceptions.MetierException;

public class Client extends Societe{
    private double chiffreAffaire;
    private int nbreEmploye;

    public double getChiffreAffaire() {
        return chiffreAffaire;
    }

    /**
     * setChiffreAffaire
     * @param chiffreAffaire
     * @throws MetierException
     */
    public void setChiffreAffaire(double chiffreAffaire) throws MetierException {
        if (chiffreAffaire <= 200){
            throw new MetierException("Le chiffre d'affaire doit dépasser 200,00 €");
        }
        this.chiffreAffaire = chiffreAffaire;
    }

    public int getNbreEmploye() {
        return nbreEmploye;
    }

    /**
     * setNbreEmploye
     * @param nbreEmploye
     * @throws MetierException
     */
    public void setNbreEmploye(int nbreEmploye) throws MetierException {
        if(nbreEmploye <= 0){
            throw new MetierException("Le nombre d'employés doit être supérieur à 0");
        }
        this.nbreEmploye = nbreEmploye;
    }

    public Client() {
    }

    /**
     * Constructeur
     * @param identifiant
     * @param raisonSociale
     * @param email
     * @param telephone
     * @param commentaire
     * @param adresse
     * @param chiffreAffaire
     * @param nbreEmploye
     * @throws MetierException
     */
    public Client(int identifiant, String raisonSociale, String email, String telephone,
                  String commentaire, Adresse adresse, double chiffreAffaire, int nbreEmploye) throws MetierException {
        super(identifiant, raisonSociale, email, telephone, commentaire, adresse);
        setChiffreAffaire(chiffreAffaire);
        setNbreEmploye(nbreEmploye);
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "L'entreprise " + super.getRaisonSociale() + " est un client." +
                "\nElle a pour identifiant : " + super.getIdentifiant() +
                "\n" + super.getAdresse().toString() +
                "\nLe mail de contact est : " + super.getEmail() +
                "\nLe numéro de téléphone est : " + super.getTelephone() +
                "\nSon CA est de " + chiffreAffaire + " €" +
                "\nSon effectif est de " + nbreEmploye + " personnes" +
                "\nCommentaire : " + super.getCommentaire();
    }
}
