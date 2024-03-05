package com.michel.vues;

import com.michel.controllers.ControllerAccueil;
import com.michel.controllers.ControllerFormulaire;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.utilitaires.LoggerReverso;
import com.michel.utilitaires.Utilitaires;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;

public class VueFormulaire extends JFrame {

    private Container formulaire;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel title;
    private JLabel reverso;
    private JButton exit;
    private JLabel trait;
    private JLabel traitMilieu;
    private JLabel traitBas;
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
            = { "", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    private final String[] mois
            = { "", "Janvier", "Février", "Mars", "Avril",
            "Mai", "Juin", "Juillet", "Août",
            "Septembre", "Octobre", "Novembre", "Décembre" };
    private ArrayList<String> annees;
    private JComboBox jComboBoxJours;
    private JComboBox jComboBoxMois;
    private JComboBox jComboBoxAnnee;
    private String dateJour;
    private String dateMois;
    private String dateAnnee;
    private LocalDate date;
    private JComboBox jComboBoxInterets;
    private String interet;
    private String choix; // client ou prospect

    public void setRaisonSociale(String raisonSociale) {
        this.tRaisonSociale.setText(raisonSociale);
    }

    public void setEmail(String email) {
        this.tEmail.setText(email);
    }

    public void setTelephone(String telephone) {
        this.tTelephone.setText(telephone);
    }

    public void setCommentaire(String commentaire) {
        this.tCommentaire.setText(commentaire);
    }

    public void setNumero(String numero) {
        this.tNumero.setText(numero);
    }

    public void setNomRue(String nomRue) {
        this.tNomRue.setText(nomRue);
    }

    public void setVille(String ville) {
        this.tVille.setText(ville);
    }

    public void setCodePostal(int codePostal) {
        this.tCodePostal.setText(String.valueOf(codePostal));
    }

    public void setCA(double tCA) {
        this.tCA.setText(String.valueOf(tCA));
    }

    public void settNbreEmploye(int nbreEmploye) {
        this.tNbreEmploye.setText(String.valueOf(nbreEmploye));
    }

    public void setjComboBoxJours(int jour) {
        this.jComboBoxJours.setSelectedIndex(jour);
    }

    public void setjComboBoxMois(String mois) {
        this.jComboBoxMois.setSelectedItem(mois);
    }

    public void setjComboBoxAnnee(int annee) {
        this.jComboBoxAnnee.setSelectedIndex(annees.indexOf(String.valueOf(annee)));
    }

    public void setInteret(String interet) {
        this.jComboBoxInterets.setSelectedItem(interet);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRaisonSociale() {
        return tRaisonSociale.getText();
    }

    public String getEmail() {
        return tEmail.getText();
    }

    public String getTelephone() {
        return tTelephone.getText();
    }

    public String getCommentaire() {
        return tCommentaire.getText();
    }

    public String getNumero() {
        return tNumero.getText();
    }

    public String getNomRue() {
        return tNomRue.getText();
    }

    public String getVille() {
        return tVille.getText();
    }

    public int getCodePostal() {
        return Integer.parseInt(tCodePostal.getText());
    }

    public double getCA() {
        return Double.parseDouble(tCA.getText());
    }

    public int gettNbreEmploye() {
        return Integer.parseInt(tNbreEmploye.getText());
    }

    public String getInteret() {
        return interet;
    }

    public VueFormulaire (String choixClientProspect, String option) {

        this.choix = choixClientProspect;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        formulaire = getContentPane();
        formulaire.setLayout(null);

        title = new JLabel("FORMULAIRE");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(screenWidth - 250, 50);
        title.setLocation(50, 150);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(title);

        reverso = new JLabel("REVERSO");
        reverso.setFont(new Font("Arial", Font.BOLD, 25));
        reverso.setSize(300, 50);
        reverso.setLocation(50, 50);
        reverso.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(reverso);

        exit = new JButton("Quitter");
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setSize(150,50);
        exit.setLocation(screenWidth - 200, 50);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });
        formulaire.add(exit);

        trait = new JLabel();
        trait.setSize(screenWidth - 250, 2);
        trait.setLocation(50,200);
        formulaire.add(trait);

        accueil = new JButton("Accueil");
        accueil.setFont(new Font("Arial", Font.PLAIN, 15));
        accueil.setSize(150,50);
        accueil.setLocation(screenWidth - 200, 120);
        accueil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onAccueil();
                } catch (MetierException metierException) {
                    JOptionPane.showMessageDialog(null, "erreur de saisie : " + metierException.getMessage());
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur BDD" + daoException.getMessage());
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Erreur, le logiciel va fermer");
                    System.exit(1);
                }
            }
        });
        formulaire.add(accueil);

        raisonSociale = new JLabel("Raison Sociale");
        raisonSociale.setFont(new Font("Arial", Font.BOLD, 25));
        raisonSociale.setSize(300, 50);
        raisonSociale.setLocation(50, 250);
        raisonSociale.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(raisonSociale);

        tRaisonSociale = new JTextField();
        tRaisonSociale.setFont(new Font("Arial", Font.BOLD, 25));
        tRaisonSociale.setSize(screenWidth - 550, 50);
        tRaisonSociale.setLocation(350, 250);
        tRaisonSociale.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(tRaisonSociale);

        numero = new JLabel("N°");
        numero.setFont(new Font("Arial", Font.BOLD, 25));
        numero.setSize(200, 50);
        numero.setLocation(50, 350);
        numero.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(numero);

        tNumero = new JTextField();
        tNumero.setFont(new Font("Arial", Font.BOLD, 25));
        tNumero.setSize(150, 50);
        tNumero.setLocation(250, 350);
        tNumero.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tNumero);

        nomRue = new JLabel("Nom de Rue");
        nomRue.setFont(new Font("Arial", Font.BOLD, 25));
        nomRue.setSize(200, 50);
        nomRue.setLocation(400, 350);
        nomRue.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(nomRue);

        tNomRue = new JTextField();
        tNomRue.setFont(new Font("Arial", Font.BOLD, 25));
        tNomRue.setSize(screenWidth-800, 50);
        tNomRue.setLocation(600, 350);
        tNomRue.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tNomRue);

        codePostal = new JLabel("Code Postal");
        codePostal.setFont(new Font("Arial", Font.BOLD, 25));
        codePostal.setSize(200, 50);
        codePostal.setLocation(50, 450);
        codePostal.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(codePostal);

        tCodePostal = new JTextField("0");
        tCodePostal.setFont(new Font("Arial", Font.BOLD, 25));
        tCodePostal.setSize(150, 50);
        tCodePostal.setLocation(250, 450);
        tCodePostal.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tCodePostal);

        ville = new JLabel("Ville");
        ville.setFont(new Font("Arial", Font.BOLD, 25));
        ville.setSize(200, 50);
        ville.setLocation(400,450);
        ville.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(ville);

        tVille = new JTextField();
        tVille.setFont(new Font("Arial", Font.BOLD, 25));
        tVille.setSize(screenWidth-800, 50);
        tVille.setLocation(600, 450);
        tVille.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tVille);

        email = new JLabel("email");
        email.setFont(new Font("Arial", Font.BOLD, 25));
        email.setSize(200, 50);
        email.setLocation(50,550);
        email.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(email);

        tEmail = new JTextField();
        tEmail.setFont(new Font("Arial", Font.BOLD, 25));
        tEmail.setSize((screenWidth-650)/2, 50);
        tEmail.setLocation(250, 550);
        tEmail.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tEmail);

        telephone = new JLabel("Téléphone");
        telephone.setFont(new Font("Arial", Font.BOLD, 25));
        telephone.setSize(200, 50);
        telephone.setLocation((screenWidth-650)/2 + 250,550);
        telephone.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(telephone);

        tTelephone = new JTextField();
        tTelephone.setFont(new Font("Arial", Font.BOLD, 25));
        tTelephone.setSize((screenWidth-650)/2, 50);
        tTelephone.setLocation((screenWidth-650)/2 + 450, 550);
        tTelephone.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tTelephone);

        traitMilieu = new JLabel();
        traitMilieu.setSize(screenWidth - 250, 2);
        traitMilieu.setLocation(50,650);
        formulaire.add(traitMilieu);

        option1 = new JLabel();
        option1.setFont(new Font("Arial", Font.BOLD, 25));
        option1.setSize(200, 50);
        option1.setLocation(50,700);
        option1.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(option1);

        option2 = new JLabel();
        option2.setFont(new Font("Arial", Font.BOLD, 25));
        option2.setSize(200, 50);
        option2.setLocation((screenWidth-650)/2 + 250,700);
        option2.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(option2);

        traitBas = new JLabel();
        traitBas.setSize(screenWidth - 250, 2);
        traitBas.setLocation(50,800);
        formulaire.add(traitBas);

        commentaire = new JLabel("Commentaire");
        commentaire.setFont(new Font("Arial", Font.BOLD, 25));
        commentaire.setSize(300, 50);
        commentaire.setLocation(50, 850);
        commentaire.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(commentaire);

        tCommentaire = new JTextField();
        tCommentaire.setFont(new Font("Arial", Font.BOLD, 25));
        tCommentaire.setSize(screenWidth - 550, 50);
        tCommentaire.setLocation(350, 850);
        tCommentaire.setHorizontalAlignment(SwingConstants.CENTER);
        formulaire.add(tCommentaire);

        if (choix.equals("client")){
            client();
        }
        if (choix.equals("prospect")){
            prospect();
        }

        switch (option) {
            case "creer" :
                setTitle("Créer un " + choixClientProspect);
                title.setText("CREER UN NOUVEAU " + choixClientProspect.toUpperCase());
                break;
            case "modifier" :
                setTitle("Modifier un " + choixClientProspect);
                title.setText("MODIFIER UN " + choixClientProspect.toUpperCase());
                break;
            case "supprimer" :
                setTitle(("Supprimer un " + choixClientProspect));
                title.setText("SUPPRIMER UN " + choixClientProspect.toUpperCase());
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
                if (choix.equals("client")) {
                    tCA.setEnabled(false);
                    tCA.setDisabledTextColor(Color.BLACK);
                    tNbreEmploye.setEnabled(false);
                    tNbreEmploye.setDisabledTextColor(Color.BLACK);
                } else if (choix.equals("prospect")) {
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

        valider = new JButton("Valider");
        valider.setFont(new Font("Arial", Font.PLAIN, 15));
        valider.setSize(screenWidth - 250, 50);
        valider.setLocation(50, 950);
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    actionValider(choix, option);

                } catch (MetierException metierException) {
                    JOptionPane.showMessageDialog(null, "erreur de saisie : " + metierException.getMessage());
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur BDD" + daoException.getMessage());
                } catch (ControllerException controllerException) {
                    JOptionPane.showMessageDialog(null, "erreur logiciel" + controllerException.getMessage());
                    System.exit(1);
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Erreur, le logiciel va fermer");
                    System.exit(1);
                }
            }
        });
        formulaire.add(valider);

        setVisible(true);
    }

    private void onAccueil() throws SQLException, MetierException, DaoException {
        dispose();
        ControllerAccueil.initAcceuil();
    }

    private void onClose() {
        // add your code here if necessary
        dispose();
    }

    private void client(){
        option1.setText("CA");
        option2.setText("nbre Employés");

        tCA = new JTextField("0");
        tCA.setFont(new Font("Arial", Font.BOLD, 25));
        tCA.setSize((screenWidth-650)/2, 50);
        tCA.setLocation(250, 700);
        tCA.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tCA);

        tNbreEmploye = new JTextField("0");
        tNbreEmploye.setFont(new Font("Arial", Font.BOLD, 25));
        tNbreEmploye.setSize((screenWidth-650)/2, 50);
        tNbreEmploye.setLocation((screenWidth-650)/2 + 450, 700);
        tNbreEmploye.setHorizontalAlignment(SwingConstants.LEFT);
        formulaire.add(tNbreEmploye);
    }

    private void prospect(){
        option1.setText("Date Prospection");
        option1.setSize(250,50);
        option2.setText("Intérêt");
        annees = ControllerFormulaire.listeYears();
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

        jComboBoxJours = new JComboBox(jours);
        jComboBoxJours.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxJours.setSize(100, 50);
        jComboBoxJours.setLocation(300, 700);
        jComboBoxJours.setRenderer(listRenderer);
        formulaire.add(jComboBoxJours);
        jComboBoxJours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateJour = (String) jComboBoxJours.getSelectedItem();
            }
        });

        jComboBoxMois = new JComboBox(mois);
        jComboBoxMois.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxMois.setSize(200, 50);
        jComboBoxMois.setLocation(400, 700);
        jComboBoxMois.setRenderer(listRenderer);
        formulaire.add(jComboBoxMois);
        jComboBoxMois.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateMois = (String) jComboBoxMois.getSelectedItem();
                System.out.println(dateMois);
            }
        });

        jComboBoxAnnee = new JComboBox(annees.toArray(new String[ControllerFormulaire.listeYears().size()]));
        jComboBoxAnnee.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxAnnee.setSize(150, 50);
        jComboBoxAnnee.setLocation(600, 700);
        jComboBoxAnnee.setRenderer(listRenderer);
        formulaire.add(jComboBoxAnnee);
        jComboBoxAnnee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dateAnnee = (String) jComboBoxAnnee.getSelectedItem();
            }
        });

        jComboBoxInterets = new JComboBox(new String[]{"","oui", "non"});
        jComboBoxInterets.setFont(new Font("Arial", Font.PLAIN, 25));
        jComboBoxInterets.setSize(150, 50);
        jComboBoxInterets.setLocation((screenWidth-650)/2 + 450, 700);
        jComboBoxInterets.setRenderer(listRenderer);
        formulaire.add(jComboBoxInterets);
        jComboBoxInterets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interet = (String) jComboBoxInterets.getSelectedItem();
            }
        });
    }

    public void actionValider(String choix,String option)
            throws MetierException, SQLException, DaoException, ControllerException {

        int choixValidation = JOptionPane.showConfirmDialog(null,
                "Voulez vous " + option + " le " + choix + " " + tRaisonSociale.getText());
        if (choixValidation == JOptionPane.YES_OPTION) {
            switch (option) {
                case "creer" :
                    int mois = 0;
                    if (choix.equals("client")){
                        mois = 1;
                        dateAnnee ="1900";
                        dateJour = "1";
                        interet = "oui";
                    }
                    if (choix.equals("prospect")) {
                        mois = Utilitaires.mois(dateMois);
                    }
                    date = LocalDate.of(Integer.parseInt(dateAnnee), mois, Integer.parseInt(dateJour));
                    ControllerFormulaire.creer(getRaisonSociale(), getNumero(), getNomRue(), getCodePostal(),
                            getVille(), getTelephone(), getEmail(), getCommentaire(), getCA(), gettNbreEmploye(),
                            date, interet, choix);
                    break;
                case "modifier":
                    ControllerFormulaire.modifier(getRaisonSociale(), getNumero(), getNomRue(), getCodePostal(),
                            getVille(), getTelephone(), getEmail(), getCommentaire(), getCA(), gettNbreEmploye(),
                            date, getInteret(), choix);
                    break;
                case "supprimer":
                    ControllerFormulaire.supprimer(choix);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Erreur lecture, le programme va fermer");
                    LoggerReverso.LOGGER.log(Level.SEVERE, "erreur option dans Vue Formulaire");
                    System.exit(1);
            }
            JOptionPane.showMessageDialog(null, "Vous avez " + option + " le " +
                    choix + " " + getRaisonSociale() + "\nVous allez retourner à l'accueil");
            onAccueil();
        }
    }
}
