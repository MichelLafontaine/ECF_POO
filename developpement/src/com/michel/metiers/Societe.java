package com.michel.metiers;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

public class Societe {
    private int identifiant;
    private String raisonSociale;
    private String email;
    private String telephone;
    private String commentaire;
    private Adresse adresse;

    public int getIdentifiant() {
        return identifiant;
    }

    /**
     * setIdentifiant
     * @param identifiant
     */
    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    /**
     * getRaisonSociale
     * @return
     */
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * doit être non null
     * @param raisonSociale
     * @throws MetierException
     */
    public void setRaisonSociale(String raisonSociale) throws MetierException {
        if (raisonSociale == null || raisonSociale.trim().isEmpty() || raisonSociale.length() > 100){
            throw new MetierException("La raison sociale n'est pas correcte");
        }
        this.raisonSociale = raisonSociale.toUpperCase();
    }

    /**
     * getEmail
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * verification format email
     * @param email
     * @throws MetierException
     */
    public void setEmail(String email) throws MetierException {
        if (email == null || email.trim().isEmpty() || !Utilitaires.PATTERN_MAIL.matcher(email).matches()){
            throw new MetierException("l'adresse email n'est pas correcte");
        }
        this.email = email.toLowerCase();
    }

    /**
     * getTelephone
     * @return
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Vérification format téléphone
     * @param telephone
     * @throws MetierException
     */
    public void setTelephone(String telephone) throws MetierException {
        if (telephone == null || telephone.trim().isEmpty() || !Utilitaires.PATTERN_TEL.matcher(telephone).matches()){
            throw new MetierException("Le numéro de téléphone n'est pas correct");
        }
        this.telephone = telephone.toLowerCase();
    }

    /**
     *
     * @return
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * setCommentaire
     * @param commentaire
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * getAdresse
     * @return
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * setAdresse
     * @param adresse
     */
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Societe() {
    }

    /**
     * Constructeur sans indentifiant
     * @param raisonSociale
     * @param email
     * @param telephone
     * @param commentaire
     * @param adresse
     * @throws MetierException
     */
    public Societe(String raisonSociale, String email, String telephone,
                   String commentaire, Adresse adresse) throws MetierException {
        setRaisonSociale(raisonSociale);
        setEmail(email);
        setTelephone(telephone);
        setCommentaire(commentaire);
        setAdresse(adresse);
    }

    /**
     * Constructeur tous composants
     * @param identifiant
     * @param raisonSociale
     * @param email
     * @param telephone
     * @param commentaire
     * @param adresse
     * @throws MetierException
     */
    public Societe(int identifiant, String raisonSociale, String email,
                   String telephone, String commentaire, Adresse adresse) throws MetierException {
        setIdentifiant(identifiant);
        setRaisonSociale(raisonSociale);
        setEmail(email);
        setTelephone(telephone);
        setCommentaire(commentaire);
        setAdresse(adresse);
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "La societe " + raisonSociale + " a pour identifiant : " + identifiant +
                "\n" + adresse.toString() +
                "\nLe mail de contact est : " + email +
                "\nLe numéro de téléphone est : " + telephone +
                "\nCommentaire : " + commentaire;
    }
}
