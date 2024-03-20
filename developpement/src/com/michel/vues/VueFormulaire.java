package com.michel.vues;

import com.michel.controllers.ControllerAccueil;
import com.michel.controllers.ControllerFormulaire;
import com.michel.dao.DaoClient;
import com.michel.dao.DaoProspect;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Adresse;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.metiers.Societe;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.EnumOption;
import com.michel.utilitaires.InteretProspect;
import com.michel.utilitaires.LoggerReverso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Vue du formulaire de création/suppression/modification d'un client ou prospect
 */
public class VueFormulaire extends JFrame {

    private EnumOption option;
    private int identifiant;
    private Container formulaire;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel title;
    private JLabel reverso;
    private JButton exit;
    private JSeparator trait;
    private JSeparator traitMilieu;
    private JSeparator traitBas;
    private JButton accueil;
    private JLabel raisonSociale;
    private JTextField tRaisonSociale;
    private JLabel email;
    private JTextField tEmail;
    private JLabel telephone;
    private JTextField tTelephone;
    private JLabel commentaire;
    private JTextField tCommentaire;
    private JLabel numero;
    private JTextField tNumero;
    private JLabel nomRue;
    private JTextField tNomRue;
    private  JLabel ville;
    private JTextField tVille;
    private JLabel codePostal;
    private JTextField tCodePostal;
    private JLabel option1;
    private JLabel option2;
    private JTextField tCA;
    private JTextField tNbreEmploye;
    private JButton valider;
    private final String[] jours
            = { "jours", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    private final String[] mois
            = { "mois", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12" };
    private ArrayList<String> annees;
    private JComboBox jComboBoxJours;
    private JComboBox jComboBoxMois;
    private JComboBox jComboBoxAnnee;
    private String dateJour;
    private String dateMois;
    private String dateAnnee;
    private LocalDate date = LocalDate.of(1900,1,1);
    private JComboBox jComboBoxInterets;
    private InteretProspect interet = InteretProspect.OUI;
    private ChoixClientProspect choix; // client ou prospect

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    /**
     *initialise raison Social du JtextField
     * @param raisonSociale String raison sociale
     */
    private void setRaisonSociale(String raisonSociale) {
        this.tRaisonSociale.setText(raisonSociale);
    }

    /**
     *initialise email du JtextField
     * @param email String email
     */
    private void setEmail(String email) {
        this.tEmail.setText(email);
    }

    /**
     *initialise telephone du JtextField
     * @param telephone String telephone
     */
    private void setTelephone(String telephone) {
        this.tTelephone.setText(telephone);
    }

    /**
     *initialise commentaire du JtextField
     * @param commentaire String commentaire
     */
    private void setCommentaire(String commentaire) {
        this.tCommentaire.setText(commentaire);
    }

    /**
     *initialise telephone du JtextField
     * @param numero String numero de rue
     */
    private void setNumero(String numero) {
        this.tNumero.setText(numero);
    }

    /**
     * initialise nom de rue du JtextField
     * @param nomRue String nom de rue
     */
    private void setNomRue(String nomRue) {
        this.tNomRue.setText(nomRue);
    }

    /**
     *initialise vile du JtextField
     * @param ville String ville
     */
    private void setVille(String ville) {
        this.tVille.setText(ville);
    }

    /**
     *initialise codepostal du JtextField
     * @param codePostal String code postal
     */
    private void setCodePostal(String codePostal) {
        this.tCodePostal.setText(codePostal);
    }

    /**
     *initialise chiffre d'affaire du JtextField
     * @param tCA double chiffre d'affaire
     */
    private void setCA(double tCA) {
        this.tCA.setText(String.valueOf(tCA));
    }

    /**
     *initialise nombre d'employé du JtextField
     * @param nbreEmploye String nombre emloye
     */
    private void settNbreEmploye(String nbreEmploye) {
        this.tNbreEmploye.setText(nbreEmploye);
    }

    /**
     *initialise le jour de date prospection (combobox)
     * @param jour String jour
     */
    private void setjComboBoxJours(int jour) {
        this.jComboBoxJours.setSelectedIndex(jour);
    }

    /**
     *initialise le mois de date prospection (combobox)
     * @param mois String mois
     */
    private void setjComboBoxMois(int mois) {
        this.jComboBoxMois.setSelectedIndex(mois);
    }

    /**
     *initialise l'année de date prospection (combobox)
     * @param annee String annee
     */
    private void setjComboBoxAnnee(String annee) {
        this.jComboBoxAnnee.setSelectedIndex(annees.indexOf(annee));
    }

    /**
     *initialise interet du prospect (combobox)
     * @param interet String oui ou non
     */
    private void setInteret(int interet) {
        if (interet == 1){
            this.interet = InteretProspect.OUI;
        }
        if (interet == 0){
            this.interet = InteretProspect.NON;
        }
        this.jComboBoxInterets.setSelectedItem(this.interet);
    }

    /**
     *initialise date de prospection
     * @param date LocalDate
     */
    private void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     *retourne raison sociale du JTextField
     * @return String raison Sociale
     */
    private String getRaisonSociale() {
        return tRaisonSociale.getText();
    }

    /**
     *retourne email du JTextField
     * @return String email
     */
    private String getEmail() {
        return tEmail.getText();
    }

    /**
     *retourne telephone du JTextField
     * @return String telephone
     */
    private String getTelephone() {
        return tTelephone.getText();
    }

    /**
     *retourne commentaire du JTextField
     * @return String commentaire
     */
    private String getCommentaire() {
        return tCommentaire.getText();
    }

    /**
     *retourne numero du JTextField
     * @return String numero de rue
     */
    private String getNumero() {
        return tNumero.getText();
    }

    /**
     *retourne nom rue du JTextField
     * @return String nom de rue
     */
    private String getNomRue() {
        return tNomRue.getText();
    }

    /**
     *retourne ville du JTextField
     * @return String ville
     */
    private String getVille() {
        return tVille.getText();
    }

    /**
     *retourne code postal du JTextField
     * @return String code postal
     */
    private String getCodePostal() {
        return tCodePostal.getText();
    }

    /**
     *retourne chiffre d'affaire du JTextField
     * @return Double chiffre d'affaire
     */
    private double getCA() {
        return Double.parseDouble(tCA.getText());
    }

    /**
     *retourne nombre d'employé du JTextField
     * @return int nbre employé
     */
    private int gettNbreEmploye() {
        return Integer.parseInt(tNbreEmploye.getText());
    }

    /**
     *retourne intéret du JTextField
     * @return int 0 ou 1
     */
    private int getInteret(InteretProspect interet) {
        if (interet.equals(InteretProspect.OUI)){
            return 1;
        }
        if (interet.equals(InteretProspect.NON)){
            return 0;
        }
        return -1;
    }

    public VueFormulaire(ChoixClientProspect choix, EnumOption option) {
        this.choix = choix;
        this.option = option;

        afficher();
    }

    public VueFormulaire (ChoixClientProspect choix, EnumOption option, Object entite) {


        this.choix = choix;
        this.option = option;

        afficher();

        Societe societe = (Societe) entite;
        setRaisonSociale(societe.getRaisonSociale());
        setIdentifiant(societe.getIdentifiant());
        setNumero(societe.getAdresse().getNumero());
        setNomRue(societe.getAdresse().getNomRue());
        setCodePostal(societe.getAdresse().getCodePostal());
        setVille(societe.getAdresse().getVille());
        setEmail(societe.getEmail());
        setTelephone(societe.getTelephone());
        setCommentaire(societe.getCommentaire());

        if (choix.equals(ChoixClientProspect.CLIENT)){
            Client client = (Client) entite;
            setCA(client.getChiffreAffaire());
            settNbreEmploye(String.valueOf(client.getNbreEmploye()));
        }
        if (choix.equals(ChoixClientProspect.PROSPECT)) {
            Prospect prospect = (Prospect) entite;
            setInteret(prospect.getInteretProspect());
            setDate(prospect.getDateProspect());
            setjComboBoxJours(date.getDayOfMonth());
            setjComboBoxAnnee(String.valueOf(date.getYear()));
            setjComboBoxMois(date.getMonthValue());
        }
    }

    /**
     * initalisation Vue
     */
    private void afficher () {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        formulaire = getContentPane();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        title = new JLabel("FORMULAIRE");
        title.setFont(new Font("Arial", Font.PLAIN, 30));

        reverso = new JLabel("REVERSO");
        reverso.setFont(new Font("Arial", Font.BOLD, 25));

        exit = new JButton("Quitter");
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setPreferredSize(new Dimension(250, 50));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });

        trait = new JSeparator();

        accueil = new JButton("Accueil");
        accueil.setFont(new Font("Arial", Font.PLAIN, 15));
        accueil.setPreferredSize(new Dimension(250, 50));
        accueil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAccueil();
            }
        });

        raisonSociale = new JLabel("Raison Sociale");
        raisonSociale.setFont(new Font("Arial", Font.BOLD, 25));
        raisonSociale.setHorizontalAlignment(SwingConstants.RIGHT);

        tRaisonSociale = new JTextField();
        tRaisonSociale.setFont(new Font("Arial", Font.BOLD, 25));

        numero = new JLabel("N°");
        numero.setFont(new Font("Arial", Font.BOLD, 25));
        numero.setHorizontalAlignment(SwingConstants.RIGHT);

        tNumero = new JTextField();
        tNumero.setFont(new Font("Arial", Font.BOLD, 25));

        nomRue = new JLabel("Nom de Rue");
        nomRue.setFont(new Font("Arial", Font.BOLD, 25));
        nomRue.setHorizontalAlignment(SwingConstants.RIGHT);

        tNomRue = new JTextField();
        tNomRue.setFont(new Font("Arial", Font.BOLD, 25));

        codePostal = new JLabel("Code Postal");
        codePostal.setFont(new Font("Arial", Font.BOLD, 25));
        codePostal.setHorizontalAlignment(SwingConstants.RIGHT);

        tCodePostal = new JTextField("0");
        tCodePostal.setFont(new Font("Arial", Font.BOLD, 25));

        ville = new JLabel("Ville");
        ville.setFont(new Font("Arial", Font.BOLD, 25));
        ville.setHorizontalAlignment(SwingConstants.RIGHT);

        tVille = new JTextField();
        tVille.setFont(new Font("Arial", Font.BOLD, 25));

        email = new JLabel("email");
        email.setFont(new Font("Arial", Font.BOLD, 25));
        email.setHorizontalAlignment(SwingConstants.RIGHT);

        tEmail = new JTextField();
        tEmail.setFont(new Font("Arial", Font.BOLD, 25));

        telephone = new JLabel("Téléphone");
        telephone.setFont(new Font("Arial", Font.BOLD, 25));
        telephone.setHorizontalAlignment(SwingConstants.RIGHT);

        tTelephone = new JTextField();
        tTelephone.setFont(new Font("Arial", Font.BOLD, 25));

        traitMilieu = new JSeparator();

        option1 = new JLabel();
        option1.setFont(new Font("Arial", Font.BOLD, 25));
        option1.setHorizontalAlignment(SwingConstants.RIGHT);

        option2 = new JLabel();
        option2.setFont(new Font("Arial", Font.BOLD, 25));
        option2.setHorizontalAlignment(SwingConstants.RIGHT);

        tCA = new JTextField("0");
        tCA.setFont(new Font("Arial", Font.BOLD, 25));
        tCA.setVisible(false);

        tNbreEmploye = new JTextField("0");
        tNbreEmploye.setFont(new Font("Arial", Font.BOLD, 25));
        tNbreEmploye.setVisible(false);

        traitBas = new JSeparator();

        commentaire = new JLabel("Commentaire");
        commentaire.setFont(new Font("Arial", Font.BOLD, 25));
        commentaire.setHorizontalAlignment(SwingConstants.RIGHT);

        tCommentaire = new JTextField();
        tCommentaire.setFont(new Font("Arial", Font.BOLD, 25));

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        annees = years();

        jComboBoxJours = new JComboBox(jours);
        jComboBoxJours.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxJours.setPreferredSize(new Dimension(200,50));
        jComboBoxJours.setRenderer(listRenderer);
        formulaire.add(jComboBoxJours);
        jComboBoxJours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateJour = (String) jComboBoxJours.getSelectedItem();
            }
        });
        jComboBoxJours.setVisible(false);


        jComboBoxMois = new JComboBox(mois);
        jComboBoxMois.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxMois.setPreferredSize(jComboBoxJours.getPreferredSize());
        jComboBoxMois.setRenderer(listRenderer);
        formulaire.add(jComboBoxMois);
        jComboBoxMois.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateMois = (String) jComboBoxMois.getSelectedItem();
                System.out.println(dateMois);
            }
        });
        jComboBoxMois.setVisible(false);
        jComboBoxAnnee = new JComboBox(annees.toArray(new String[0]));
        jComboBoxAnnee.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxAnnee.setPreferredSize(jComboBoxJours.getPreferredSize());
        jComboBoxAnnee.setRenderer(listRenderer);
        formulaire.add(jComboBoxAnnee);
        jComboBoxAnnee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateAnnee = (String) jComboBoxAnnee.getSelectedItem();
            }
        });
        jComboBoxAnnee.setVisible(false);

        jComboBoxInterets = new JComboBox(new InteretProspect[]{InteretProspect.OUI, InteretProspect.NON});
        jComboBoxInterets.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxInterets.setRenderer(listRenderer);
        jComboBoxInterets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interet = (InteretProspect) jComboBoxInterets.getSelectedItem();
            }
        });
        jComboBoxInterets.setVisible(false);

        if (choix.equals(ChoixClientProspect.CLIENT)){
            client();
        }
        if (choix.equals(ChoixClientProspect.PROSPECT)){
            prospect();
        }

        initOption();

        valider = new JButton("Valider");
        valider.setFont(new Font("Arial", Font.PLAIN, 15));
        valider.setPreferredSize(new Dimension(250, 50));
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    actionValider();

                } catch (MetierException metierException) {
                    JOptionPane.showMessageDialog(null, "erreur de saisie : " + metierException.getMessage());
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur BDD : " + daoException.getMessage());
                    if (daoException.getCritere() == 2){
                        System.exit(1);
                    }
                } catch (NumberFormatException numberFormatException){
                    if (choix.equals(ChoixClientProspect.PROSPECT)) {
                        JOptionPane.showMessageDialog(null, "Attention, ne mettre que des nombres dans la date");
                    }
                    if (choix.equals(ChoixClientProspect.CLIENT)){
                        JOptionPane.showMessageDialog(null, "Attention, nbre d'employé et CA sont des nombres");
                    }
                } catch (Exception exception){
                    StringBuilder messageLog = new StringBuilder("Exception bouton valider");
                    messageLog.append(exception.getMessage()).append(" ").append(e);
                    LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
                    JOptionPane.showMessageDialog(null, "Erreur, le logiciel va fermer");
                    System.exit(1);
                }
            }
        });

        //Création d'un damier
        JLabel[] labels = new JLabel[15];
        for (int i = 0; i < 15; i++) {
            labels[i] = new JLabel();
        }

        labels[1].setPreferredSize(new Dimension(screenWidth/6, 1));
        labels[2].setPreferredSize(labels[1].getPreferredSize());
        labels[3].setPreferredSize(labels[1].getPreferredSize());
        labels[4].setPreferredSize(labels[1].getPreferredSize());
        labels[5].setPreferredSize(labels[1].getPreferredSize());
        labels[6].setPreferredSize(new Dimension(1, screenWidth/17));
        labels[7].setPreferredSize(labels[6].getPreferredSize());
        labels[8].setPreferredSize(labels[6].getPreferredSize());
        labels[9].setPreferredSize(labels[6].getPreferredSize());
        labels[10].setPreferredSize(labels[6].getPreferredSize());
        labels[11].setPreferredSize(labels[6].getPreferredSize());
        labels[12].setPreferredSize(labels[6].getPreferredSize());
        labels[13].setPreferredSize(labels[6].getPreferredSize());
        labels[14].setPreferredSize(labels[6].getPreferredSize());

        gbc.gridx = gbc.gridy = 0;
        formulaire.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formulaire.add(labels[1], gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        formulaire.add(labels[2], gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        formulaire.add(labels[3], gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        formulaire.add(labels[4], gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        formulaire.add(labels[5], gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formulaire.add(labels[6], gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formulaire.add(labels[7], gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formulaire.add(labels[8], gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formulaire.add(labels[9], gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formulaire.add(labels[10], gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formulaire.add(labels[11], gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formulaire.add(labels[12], gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formulaire.add(labels[13], gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formulaire.add(labels[14], gbc);

        //Ajout des composants en spécifiant les contraintes de types GridBagContraints
        gbc.gridx = gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        formulaire.add(reverso, gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(exit, gbc);

        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(accueil, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(title, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 5, 0, 5);
        formulaire.add(trait, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(this.raisonSociale, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tRaisonSociale, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(numero, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tNumero, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(nomRue, gbc);

        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tNomRue, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(codePostal, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tCodePostal, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(ville, gbc);

        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tVille, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(telephone, gbc);

        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tTelephone, gbc);

        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(email, gbc);

        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(option1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tNbreEmploye, gbc);
        formulaire.add(jComboBoxInterets,gbc);


        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(option2, gbc);

        JPanel date = new JPanel();
        date.setLayout(new GridBagLayout());
        GridBagConstraints gbcDate = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        date.add(jComboBoxJours, gbcDate);
        gbc.gridx = 1;
        date.add(jComboBoxMois, gbcDate);
        gbc.gridx = 2;
        date.add(jComboBoxAnnee, gbcDate);

        gbc.gridx = 4;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(date, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        formulaire.add(tCA, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(commentaire, gbc);

        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(tCommentaire, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        formulaire.add(valider, gbc);

        setVisible(true);
    }

    /**
     * retour accueil
     */
    private void onAccueil() {
        dispose();
        ControllerFormulaire.accueil();
    }

    /**
     * fermeture logiciel
     */
    private void onClose() {
        dispose();
    }

    /**
     * initilisation vue si client
     */
    private void client() {
        option2.setText("CA");
        option1.setText("nbre Employés");

        tCA.setVisible(true);
        tNbreEmploye.setVisible(true);
    }

    /**
     * initialisation vue si prospect
     */
    private void prospect(){
        option2.setText("Date Prospection");
        option1.setText("Intérêt");

        jComboBoxJours.setVisible(true);
        jComboBoxMois.setVisible(true);
        jComboBoxAnnee.setVisible(true);
        jComboBoxInterets.setVisible(true);
    }

    /**
     * Valider le formulaire
     * @throws MetierException propagation
     * @throws DaoException propagation
     * @throws ControllerException propagation
     */
    private void actionValider()
            throws Exception {
        int choixValidation = JOptionPane.showConfirmDialog(null,
                "Voulez vous " + option + " le " + choix + " " + tRaisonSociale.getText());
        if (choixValidation == JOptionPane.YES_OPTION) {
            ControllerFormulaire controllerFormulaire = new ControllerFormulaire(choix, option, identifiant);
            Adresse adresse = new Adresse(getNumero(), getNomRue(), getVille(), getCodePostal());
            if (choix.equals(ChoixClientProspect.CLIENT)){
                Client client = new Client(identifiant, getRaisonSociale(), getEmail(), getTelephone(),
                        getCommentaire(), adresse, getCA(), gettNbreEmploye());
                controllerFormulaire.valider(client);
            }
            if (choix.equals(ChoixClientProspect.PROSPECT)){
                date = LocalDate.of(Integer.parseInt(dateAnnee), Integer.parseInt(dateMois), Integer.parseInt(dateJour));
                Prospect prospect = new Prospect(identifiant, getRaisonSociale(), getEmail(), getTelephone(),
                        getCommentaire(), adresse, date, getInteret(interet));
                controllerFormulaire.valider(prospect);
            }
            JOptionPane.showMessageDialog(null, "Vous avez " + option + " le " +
                    choix + " " + getRaisonSociale() + "\nVous allez retourner à l'accueil");
            onAccueil();
        }
    }
    /**
     * liste des années depuis 2000
     * @return ArrayList String
     */
    private ArrayList<String> years (){
        ArrayList<String> listAnnee = new ArrayList<>();
        listAnnee.add("années");
        int anneeActuelle = LocalDate.now().getYear();

        for (int annee = anneeActuelle; annee >= 2000 ; annee--){
            listAnnee.add(String.valueOf(annee));
        }
        return listAnnee;
    }

    private void initOption(){
        switch (option) {
            case EnumOption.CREER:
                setTitle("Créer un " + choix);
                title.setText("CREER UN NOUVEAU " + choix);
                break;
            case EnumOption.MODIFIER :
                setTitle("Modifier un " + choix);
                title.setText("MODIFIER UN " + choix);
                break;
            case EnumOption.SUPPRIMER:
                setTitle(("Supprimer un " + choix));
                title.setText("SUPPRIMER UN " + choix);
                //On bloque toute possibilité de modification
                tRaisonSociale.setEnabled(false);
                tRaisonSociale.setDisabledTextColor(Color.BLACK);
                tNumero.setEnabled(false);
                tNumero.setDisabledTextColor(Color.BLACK);
                tNomRue.setEnabled(false);
                tNomRue.setDisabledTextColor(Color.BLACK);
                tCodePostal.setEnabled(false);
                tCodePostal.setDisabledTextColor(Color.BLACK);
                tVille.setEnabled(false);
                tVille.setDisabledTextColor(Color.BLACK);
                tTelephone.setEnabled(false);
                tTelephone.setDisabledTextColor(Color.BLACK);
                tEmail.setEnabled(false);
                tEmail.setDisabledTextColor(Color.BLACK);
                tCommentaire.setEnabled(false);
                tCommentaire.setDisabledTextColor(Color.BLACK);
                if (choix.equals(ChoixClientProspect.CLIENT)) {
                    tCA.setEnabled(false);
                    tCA.setDisabledTextColor(Color.BLACK);
                    tNbreEmploye.setEnabled(false);
                    tNbreEmploye.setDisabledTextColor(Color.BLACK);
                } else if (choix.equals(ChoixClientProspect.PROSPECT)) {
                    jComboBoxAnnee.setEnabled(false);
                    jComboBoxAnnee.setBackground(Color.BLACK);
                    jComboBoxJours.setEnabled(false);
                    jComboBoxMois.setEnabled(false);
                    jComboBoxInterets.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null,"Erreur lecture, le programme va fermer");
                    LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option dans Vue Formulaire / cas supprimer ");
                    System.exit(1);
                }
                break;
            default:
                JOptionPane.showMessageDialog(null,"Erreur lecture, le programme va fermer");
                LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option dans Vue Formulaire");
                System.exit(1);
        }
    }


}
