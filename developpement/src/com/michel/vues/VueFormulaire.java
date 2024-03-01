package com.michel.vues;

import com.michel.controllers.ControllerAccueil;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueFormulaire extends JFrame {

    private Container formulaire;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel title;
    private JLabel reverso;
    private JButton exit;
    private JLabel trait;
    private JButton accueil;
    private Client client;
    private Prospect prospect;
    private JLabel raisonSociale;
    private JTextField tRaisonSociale;
    private JLabel email;
    private JTextField tEmail;
    private JLabel telephone;
    private JTextField tTelephone;
    private JLabel commentaire;
    private JLabel numero;
    private JTextField tNumero;
    private JLabel nomRue;
    private JTextField tNomRue;
    private  JLabel ville;
    private JTextField tVille;
    private JLabel codePostal;
    private JTextField tCodePostal;
    private JLabel chiffreAffaire;
    private JLabel nbreEmploye;
    private JLabel dateProspection;
    private JLabel interetProspect;

    public VueFormulaire (){

        setTitle("formulaire");
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
        reverso.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
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
        trait.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        formulaire.add(trait);

        accueil = new JButton("Accueil");
        accueil.setFont(new Font("Arial", Font.PLAIN, 15));
        accueil.setSize(150,50);
        accueil.setLocation(screenWidth - 200, 120);
        accueil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAccueil();
            }
        });
        formulaire.add(accueil);

        raisonSociale = new JLabel("Raison Sociale");
        raisonSociale.setFont(new Font("Arial", Font.BOLD, 25));
        raisonSociale.setSize(300, 50);
        raisonSociale.setLocation(50, 250);
        raisonSociale.setHorizontalAlignment(SwingConstants.CENTER);
        raisonSociale.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        raisonSociale.setVisible(true);
        formulaire.add(raisonSociale);

        tRaisonSociale = new JTextField();
        tRaisonSociale.setFont(new Font("Arial", Font.BOLD, 25));
        tRaisonSociale.setSize(screenWidth - 600, 50);
        tRaisonSociale.setLocation(350, 250);
        tRaisonSociale.setHorizontalAlignment(SwingConstants.CENTER);
        tRaisonSociale.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tRaisonSociale.setVisible(true);
        formulaire.add(tRaisonSociale);

        numero = new JLabel("N°");
        numero.setFont(new Font("Arial", Font.BOLD, 25));
        numero.setSize(200, 50);
        numero.setLocation(50, 350);
        numero.setHorizontalAlignment(SwingConstants.CENTER);
        numero.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        numero.setVisible(true);
        formulaire.add(numero);

        tNumero = new JTextField();
        tNumero.setFont(new Font("Arial", Font.BOLD, 25));
        tNumero.setSize(150, 50);
        tNumero.setLocation(250, 350);
        tNumero.setHorizontalAlignment(SwingConstants.LEFT);
        tNumero.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tNumero.setVisible(true);
        formulaire.add(tNumero);

        nomRue = new JLabel("Nom de Rue");
        nomRue.setFont(new Font("Arial", Font.BOLD, 25));
        nomRue.setSize(200, 50);
        nomRue.setLocation(400, 350);
        nomRue.setHorizontalAlignment(SwingConstants.CENTER);
        nomRue.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        nomRue.setVisible(true);
        formulaire.add(nomRue);

        tNomRue = new JTextField();
        tNomRue.setFont(new Font("Arial", Font.BOLD, 25));
        tNomRue.setSize(screenWidth-850, 50);
        tNomRue.setLocation(600, 350);
        tNomRue.setHorizontalAlignment(SwingConstants.LEFT);
        tNomRue.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tNomRue.setVisible(true);
        formulaire.add(tNomRue);

        codePostal = new JLabel("Code Postal");
        codePostal.setFont(new Font("Arial", Font.BOLD, 25));
        codePostal.setSize(200, 50);
        codePostal.setLocation(50, 450);
        codePostal.setHorizontalAlignment(SwingConstants.CENTER);
        codePostal.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        codePostal.setVisible(true);
        formulaire.add(codePostal);

        tCodePostal = new JTextField();
        tCodePostal.setFont(new Font("Arial", Font.BOLD, 25));
        tCodePostal.setSize(150, 50);
        tCodePostal.setLocation(250, 450);
        tCodePostal.setHorizontalAlignment(SwingConstants.LEFT);
        tCodePostal.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tCodePostal.setVisible(true);
        formulaire.add(tCodePostal);

        ville = new JLabel("Ville");
        ville.setFont(new Font("Arial", Font.BOLD, 25));
        ville.setSize(200, 50);
        ville.setLocation(400,450);
        ville.setHorizontalAlignment(SwingConstants.CENTER);
        ville.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        ville.setVisible(true);
        formulaire.add(ville);

        tVille = new JTextField();
        tVille.setFont(new Font("Arial", Font.BOLD, 25));
        tVille.setSize(screenWidth-850, 50);
        tVille.setLocation(600, 450);
        tVille.setHorizontalAlignment(SwingConstants.LEFT);
        tVille.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tVille.setVisible(true);
        formulaire.add(tVille);

        email = new JLabel("email");
        email.setFont(new Font("Arial", Font.BOLD, 25));
        email.setSize(200, 50);
        email.setLocation(50,550);
        email.setHorizontalAlignment(SwingConstants.CENTER);
        email.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        email.setVisible(true);
        formulaire.add(email);

        tEmail = new JTextField();
        tEmail.setFont(new Font("Arial", Font.BOLD, 25));
        tEmail.setSize((screenWidth-450)/2, 50);
        tEmail.setLocation(250, 550);
        tEmail.setHorizontalAlignment(SwingConstants.LEFT);
        tEmail.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        tEmail.setVisible(true);
        formulaire.add(tEmail);

        telephone = new JLabel();

        setVisible(true);
    }

    private void onAccueil() {
        dispose();
        ControllerAccueil.initAcceuil();
    }

    private void onClose() {
        // add your code here if necessary
        dispose();
    }
}
