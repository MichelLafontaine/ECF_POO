package com.michel.metiers;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

/**
 * classe mere
 */
public abstract class Societe {
    private int identifiant;
    private String raisonSociale;
    private String email;
    private String telephone;
    private String commentaire;
    private Adresse adresse;

    /**
     * retourne identifiant
     * @return int
     */
    public int getIdentifiant() {
        return identifiant;
    }

    /**
     * initialise identifiant
     * @param identifiant int
     */
    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    /**
     * retourne raison sociale
     * @return String
     */
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * vérification/initialisation raison sociale
     * @param raisonSociale String
     * @throws MetierException si null ou vide ou >100
     */
    public void setRaisonSociale(String raisonSociale) throws MetierException {
        if (raisonSociale == null || raisonSociale.trim().isEmpty()){
            throw new MetierException("La raison sociale ne doit pas être vide");
        }
        if (raisonSociale.length() > 100){
            throw new MetierException("La raison sociale dépasse les 100 caractères");
        }
        this.raisonSociale = raisonSociale.toUpperCase();
    }

    /**
     * retourne email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * verification/initialisation email
     * @param email String
     * @throws MetierException si null ou vide ou != Pattern
     */
    public void setEmail(String email) throws MetierException {
        if (email == null || email.trim().isEmpty() || !Utilitaires.PATTERN_MAIL.matcher(email).matches()){
            throw new MetierException("l'adresse email ne doit pas être vide");
        }
        if (!Utilitaires.PATTERN_MAIL.matcher(email).matches()) {
            throw new MetierException("L'email n'est pas au bon format");
        }
        this.email = email.toLowerCase();
    }

    /**
     * retourne numero téléphone
     * @return String
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Vérification/initilisation format téléphone
     * @param telephone String
     * @throws MetierException si null ou vide ou != pattern
     */
    public void setTelephone(String telephone) throws MetierException {
        if (telephone == null || telephone.trim().isEmpty()){
            throw new MetierException("Le numéro de téléphone ne doit pas être vide");
        }
        if (!Utilitaires.PATTERN_TEL.matcher(telephone).matches()) {
            throw new MetierException("Le numéro de téléphone n'est pas au bon format");
        }
        this.telephone = telephone.toLowerCase();
    }

    /**
     *retour commentaire
     * @return String
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * initilisation commentaire
     * @param commentaire String
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * retour Object Adresse
     * @return Object Adresse
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * initilisation Object Adresse
     * @param adresse Object Adresse
     */
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    /**
     * constructeur sans attribut
     */
    protected Societe() {
    }

    /**
     * Constructeur sans attribut : indentifiant
     * @param raisonSociale String ne doit pas etre null
     * @param email String ne doit pas etre null
     * @param telephone String ne doit pas etre null
     * @param commentaire String
     * @param adresse Object Adresse
     * @throws MetierException envoi message à 'utilisateur
     */
    protected Societe(String raisonSociale, String email, String telephone,
                   String commentaire, Adresse adresse) throws MetierException {
        setRaisonSociale(raisonSociale);
        setEmail(email);
        setTelephone(telephone);
        setCommentaire(commentaire);
        setAdresse(adresse);
    }

    /**
     * Constructeur tous attributs
     * @param identifiant int
     * @param raisonSociale String ne doit pas etre null
     * @param email String ne doit pas etre null
     * @param telephone String ne doit pas etre null
     * @param commentaire String
     * @param adresse Object adresse
     * @throws MetierException envoi message à 'utilisateur
     */
    protected Societe(int identifiant, String raisonSociale, String email,
                   String telephone, String commentaire, Adresse adresse) throws MetierException {
        setIdentifiant(identifiant);
        setRaisonSociale(raisonSociale);
        setEmail(email);
        setTelephone(telephone);
        setCommentaire(commentaire);
        setAdresse(adresse);
    }

    /**
     *surcharger toString
     * @return phrase de présentation
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
