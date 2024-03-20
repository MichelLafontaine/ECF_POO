package com.michel.metiers;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

/**
 * Créeer objet adresse et tests des entrées
 */
public class Adresse {
    private String numero;
    private String nomRue;
    private String ville;
    private String codePostal;

    /**
     * retourne le numéro de rue
     * @return String numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * vérification format téléphone
     * @param numero String ne doit pas etre null
     * @throws MetierException envoi message à 'utilisateur
     */
    public void setNumero(String numero) throws MetierException {
        if (numero == null || numero.trim().isEmpty()){
            throw new MetierException("Le numéro de la rue ne doit pas être vide");
        }
        if (numero.length() > 10) {
            throw new MetierException("Le numéro de la rue dépasse 10 caractères");
        }
        this.numero = numero.toUpperCase();
    }

    /**
     * retourne le nom de rue
     * @return String nom de rue
     */
    public String getNomRue() {
        return nomRue;
    }

    /**
     * initialisation/vérification de nom de rue
     * @param nomRue String Nom de rue
     * @throws MetierException envoi message à 'utilisateur
     */
    public void setNomRue(String nomRue) throws MetierException {
        if (nomRue == null || nomRue.trim().isEmpty()){
            throw new MetierException("Le nom de la rue ne doit pas être vide");
        }
        if (nomRue.length() > 150){
            throw new MetierException("Le nom de la rue dépasse les 150 caractères");
        }
        this.nomRue = nomRue.toUpperCase();
    }

    /**
     * retourne la ville
     * @return String ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * initialisation/vérification de la ville
     * @param ville String ville
     * @throws MetierException envoi message à 'utilisateur
     */
    public void setVille(String ville) throws MetierException {
        if (ville == null || ville.trim().isEmpty()){
            throw new MetierException("Le nom de la ville ne doit pas être vide");
        }
        if (ville.length() > 50){
            throw new MetierException("Le nom de la rue dépasse les 50 caractères");
        }
        this.ville = ville.toUpperCase();
    }

    /**
     * retourne le code postal
     * @return String code postal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * initialisation/vérification du format
     * @param codePostal String code postal
     * @throws MetierException envoi message à 'utilisateur
     */
    public void setCodePostal(String codePostal) throws MetierException {
        if (codePostal == null|| codePostal.trim().isEmpty()){
            throw new MetierException("le Code Postal ne doit pas être vide");
        }
        if (!Utilitaires.PATTERN_CODEPOSTAL.matcher((codePostal)).matches()){
            throw new MetierException("le Code Postal n'est pas au bon format : 5 chiffres");
        }
        this.codePostal = codePostal;
    }

    /**
     * Constructeur sans attribut
     */
    public Adresse() {
    }

    /**
     * Constructeur avec tous les attributs
     * @param numero String ne doit pas etre null
     * @param nomRue String ne doit pas etre null
     * @param ville String ne doit pas etre null
     * @param codePostal String 5 chiffres
     * @throws MetierException exception ne met pas fin au logiciel
     */
    public Adresse(String numero, String nomRue, String ville, String codePostal) throws MetierException {
        setNumero(numero);
        setNomRue(nomRue);
        setVille(ville);
        setCodePostal(codePostal);
    }

    /**
     *surcharger to String
     * @return phrase de présentation
     */
    @Override
    public String toString() {
        return "L'adresse est : " + numero + " " + nomRue + " " + codePostal + " " + ville;
    }
}
