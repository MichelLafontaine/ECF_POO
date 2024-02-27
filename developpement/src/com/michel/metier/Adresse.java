package com.michel.metier;

import com.michel.exceptions.MetierException;
import com.michel.utilitaires.Utilitaires;

public class Adresse {
    private String numero;
    private String nomRue;
    private String ville;
    private int codePostal;

    public String getNumero() {
        return numero;
    }

    /**
     * setNumero
     * @param numero
     * @throws MetierException
     */
    public void setNumero(String numero) throws MetierException {
        if (numero == null || numero.trim().isEmpty() || numero.length() > 10){
            throw new MetierException("Le numéro de la rue n'est pas correct");
        }
        this.numero = numero;
    }

    public String getNomRue() {
        return nomRue;
    }

    /**
     * setNomRue
     * @param nomRue
     * @throws MetierException
     */
    public void setNomRue(String nomRue) throws MetierException {
        if (nomRue == null || nomRue.trim().isEmpty() || nomRue.length() > 150){
            throw new MetierException("Le nom de la rue n'est pas correct");
        }
        this.nomRue = nomRue;
    }

    public String getVille() {
        return ville;
    }

    /**
     * setVille
     * @param ville
     * @throws MetierException
     */
    public void setVille(String ville) throws MetierException {
        if (ville == null || ville.trim().isEmpty() || ville.length() > 50){
            throw new MetierException("Le nom de la ville n'est pas correct");
        }
        this.ville = ville.toUpperCase();
    }

    public int getCodePostal() {
        return codePostal;
    }

    /**
     * setCodePostal
     * @param codePostal
     * @throws MetierException
     */
    public void setCodePostal(int codePostal) throws MetierException {
        if (codePostal < 0 || !Utilitaires.PATTERN_CODEPOSTAL.matcher(String.valueOf(codePostal)).matches()){
            throw new MetierException("le Code Postal n'est pas correct");
        }
        this.codePostal = codePostal;
    }

    public Adresse() {
    }

    /**
     * Constructeur
     * @param numero
     * @param nomRue
     * @param ville
     * @param codePostal
     * @throws MetierException
     */
    public Adresse(String numero, String nomRue, String ville, int codePostal) throws MetierException {
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
