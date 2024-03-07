package com.michel.metiers;

import com.michel.exceptions.MetierException;

/**
 * classe fille de la classe societe
 */
public class Client extends Societe{
    private double chiffreAffaire;
    private int nbreEmploye;

    /**
     * retourne le chiffre d'affaire
     * @return Double chiffre d'affaire
     */
    public double getChiffreAffaire() {
        return chiffreAffaire;
    }

    /**
     * initialisation/vérification chiffre d'affaire
     * @param chiffreAffaire Double chiffre d'affaire
     * @throws MetierException si inferieur ou egal 200
     */
    public void setChiffreAffaire(double chiffreAffaire) throws MetierException {
        if (chiffreAffaire <= 200){
            throw new MetierException("Le chiffre d'affaire doit dépasser 200,00 €");
        }
        this.chiffreAffaire = chiffreAffaire;
    }

    /**
     * retourne le nombre d'employé
     * @return int nbre d'employe
     */
    public int getNbreEmploye() {
        return nbreEmploye;
    }

    /**
     * initialisation/vérification du nombre d'employé
     * @param nbreEmploye int nbre d'employe
     * @throws MetierException si inferieur 0
     */
    public void setNbreEmploye(int nbreEmploye) throws MetierException {
        if(nbreEmploye <= 0){
            throw new MetierException("Le nombre d'employés doit être supérieur à 0");
        }
        this.nbreEmploye = nbreEmploye;
    }

    /**
     * constructeur sans attribut
     */
    public Client() {
    }

    /**
     * Constructeur sans attribut identifiant
     * @param raisonSociale String ne doit pas être null
     * @param email String ne doit pas être null
     * @param telephone String ne doit pas être null
     * @param commentaire String ne doit pas être null
     * @param adresse Object Adresse
     * @param chiffreAffaire Double ne doit pas être null
     * @param nbreEmploye int ne doit pas être null
     * @throws MetierException propagation
     */
    public Client(String raisonSociale, String email, String telephone, String commentaire, Adresse adresse,
                  double chiffreAffaire, int nbreEmploye) throws MetierException {
        super(raisonSociale, email, telephone, commentaire, adresse);
        setChiffreAffaire(chiffreAffaire);
        setNbreEmploye(nbreEmploye);
    }

    /**
     * Constructeur tout attribut
     * @param identifiant int
     * @param raisonSociale String ne doit pas être null
     * @param email String ne doit pas être null
     * @param telephone String ne doit pas être null
     * @param commentaire String ne doit pas être null
     * @param adresse Object Adresse
     * @param chiffreAffaire Double ne doit pas être null
     * @param nbreEmploye int ne doit pas être null
     * @throws MetierException propagation
     */
    public Client(int identifiant, String raisonSociale, String email, String telephone,
                  String commentaire, Adresse adresse, double chiffreAffaire, int nbreEmploye) throws MetierException {
        super(identifiant, raisonSociale, email, telephone, commentaire, adresse);
        setChiffreAffaire(chiffreAffaire);
        setNbreEmploye(nbreEmploye);
    }

    /**
     * toString
     * @return String
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
