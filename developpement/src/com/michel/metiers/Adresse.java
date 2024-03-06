package com.michel.metiers;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

public class Adresse {
    private String numero;
    private String nomRue;
    private String ville;
    private String codePostal;

    public String getNumero() {
        return numero;
    }

    /**
     * vérification format téléphone
     * @param numero
     * @throws MetierException
     */
    public void setNumero(String numero) throws MetierException {
        if (numero == null || numero.trim().isEmpty() || numero.length() > 10){
            throw new MetierException("Le numéro de la rue n'est pas correct");
        }
        this.numero = numero.toUpperCase();
    }

    public String getNomRue() {
        return nomRue;
    }

    /**
     * non null
     * @param nomRue
     * @throws MetierException
     */
    public void setNomRue(String nomRue) throws MetierException {
        if (nomRue == null || nomRue.trim().isEmpty() || nomRue.length() > 150){
            throw new MetierException("Le nom de la rue n'est pas correct");
        }
        this.nomRue = nomRue.toUpperCase();
    }

    public String getVille() {
        return ville;
    }

    /**
     * non null
     * @param ville
     * @throws MetierException
     */
    public void setVille(String ville) throws MetierException {
        if (ville == null || ville.trim().isEmpty() || ville.length() > 50){
            throw new MetierException("Le nom de la ville n'est pas correct");
        }
        this.ville = ville.toUpperCase();
    }

    public String getCodePostal() {
        return codePostal;
    }

    /**
     * vérification du format
     * @param codePostal
     * @throws MetierException
     */
    public void setCodePostal(String codePostal) throws MetierException {
        if (codePostal == null|| codePostal.trim().isEmpty() || !Utilitaires.PATTERN_CODEPOSTAL.matcher((codePostal)).matches()){
            throw new MetierException("le Code Postal n'est pas correct");
        }
        this.codePostal = codePostal;
    }

    public Adresse() {
    }

    /**
     * Constructeur avec tous les attributs
     * @param numero
     * @param nomRue
     * @param ville
     * @param codePostal
     * @throws MetierException
     */
    public Adresse(String numero, String nomRue, String ville, String codePostal) throws MetierException {
        setNumero(numero);
        setNomRue(nomRue);
        setVille(ville);
        setCodePostal(codePostal);
    }

    @Override
    public String toString() {
        return "L'adresse est : " + numero + " " + nomRue + " " + codePostal + " " + ville;
    }
}
