package com.michel.vues;

import com.michel.controllers.ControllerAccueil;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class VueAccueil extends JFrame {

    private Container accueil;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel title;
    private JLabel reverso;
    private JButton exit;
    private JLabel trait;
    private JButton client;
    private JButton prospect;
    private JButton creer;
    private JButton modifier;
    private JButton afficher;
    private JButton supprimer;
    private JButton reinitialiser;
    private JButton valider;
    private JComboBox<String> jComboBoxSociete;
    private String societes[];
    private String choix;
    private String option;
    private String societe;

    public VueAccueil () {

        setTitle("Page d'accueil");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        accueil = getContentPane();
        accueil.setLayout(null);

        title = new JLabel("ACCUEIL");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(screenWidth - 250, 50);
        title.setLocation(50, 150);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        accueil.add(title);

        reverso = new JLabel("REVERSO");
        reverso.setFont(new Font("Arial", Font.BOLD, 25));
        reverso.setSize(300, 50);
        reverso.setLocation(50, 50);
        reverso.setHorizontalAlignment(SwingConstants.CENTER);
        accueil.add(reverso);

        exit = new JButton("Quitter");
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setSize(150,50);
        exit.setLocation(screenWidth - 200, 50);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });
        accueil.add(exit);

        trait = new JLabel();
        trait.setSize(screenWidth - 250, 2);
        trait.setLocation(50,200);
        trait.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        accueil.add(trait);

        client = new JButton("Client");
        client.setFont(new Font("Arial", Font.PLAIN, 15));
        client.setSize(250, 50);
        client.setLocation((screenWidth - 250)/3-75, 250);
        client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prospect.setVisible(false);
                client.setVisible(false);
                creer.setText("Créer un nouveau Client");
                creer.setVisible(true);
                modifier.setText("Modifier un Client");
                modifier.setVisible(true);
                afficher.setText("Afficher les Clients");
                afficher.setVisible(true);
                supprimer.setText("Supprimer un Client");
                supprimer.setVisible(true);
                reinitialiser.setVisible(true);
                choix = "client";
            }
        });
        accueil.add(client);

        prospect = new JButton("Prospect");
        prospect.setFont(new Font("Arial", Font.PLAIN, 15));
        prospect.setSize(250,50);
        prospect.setLocation(((screenWidth - 250)*2)/3 - 75, 250);
        prospect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prospect.setVisible(false);
                client.setVisible(false);
                creer.setText("Créer un nouveau Prospect");
                creer.setVisible(true);
                modifier.setText("Modifier un Prospect");
                modifier.setVisible(true);
                afficher.setText("Afficher les Prospects");
                afficher.setVisible(true);
                supprimer.setText("Supprimer un Prospect");
                supprimer.setVisible(true);
                reinitialiser.setVisible(true);
                choix = "prospect";
            }
        });
        accueil.add(prospect);

        creer = new JButton("Créer");
        creer.setFont(new Font("Arial", Font.PLAIN, 15));
        creer.setSize(250, 50);
        creer.setLocation((screenWidth - 250) /5 - 75, 350);
        creer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                modifier.setVisible(false);
                supprimer.setVisible(false);
                afficher.setVisible(false);
                option = "creer";
            }
        });
        creer.setVisible(false);
        accueil.add(creer);

        modifier = new JButton("Modifier");
        modifier.setFont(new Font("Arial", Font.PLAIN, 15));
        modifier.setSize(250, 50);
        modifier.setLocation((screenWidth - 250)*2/5 - 75, 350);
        modifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                creer.setVisible(false);
                supprimer.setVisible(false);
                afficher.setVisible(false);
                option = "modifier";
                try {
                    choixSociete(choix);
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur BDD" + daoException.getMessage());
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Erreur, le logiciel va fermer");
                    System.exit(1);
                }
            }
        });
        modifier.setVisible(false);
        accueil.add(modifier);

        afficher = new JButton("Afficher");
        afficher.setFont(new Font("Arial", Font.PLAIN, 15));
        afficher.setSize(250, 50);
        afficher.setLocation((screenWidth - 250)*4/5 - 75, 350);
        afficher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                modifier.setVisible(false);
                supprimer.setVisible(false);
                creer.setVisible(false);
                option = "afficher";
            }
        });
        afficher.setVisible(false);
        accueil.add(afficher);

        supprimer = new JButton("Afficher");
        supprimer.setFont(new Font("Arial", Font.PLAIN, 15));
        supprimer.setSize(250, 50);
        supprimer.setLocation((screenWidth - 250)*3/5 - 75, 350);
        supprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                modifier.setVisible(false);
                creer.setVisible(false);
                afficher.setVisible(false);
                option = "supprimer";
                try {
                    choixSociete(choix);
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur BDD" + daoException.getMessage());
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Erreur, le logiciel va fermer");
                    System.exit(1);
                }
            }
        });
        supprimer.setVisible(false);
        accueil.add(supprimer);

        reinitialiser = new JButton("Réinitialiser");
        reinitialiser.setFont(new Font("Arial", Font.PLAIN, 15));
        reinitialiser.setSize(screenWidth - 250, 50);
        reinitialiser.setLocation(50, screenHeight - 100);
        reinitialiser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                prospect.setVisible(true);
                client.setVisible(true);
                creer.setVisible(false);
                modifier.setVisible(false);
                afficher.setVisible(false);
                supprimer.setVisible(false);
                reinitialiser.setVisible(false);
                valider.setVisible(false);
                jComboBoxSociete.setVisible(false);
                choix = null;
                option = null;
                societe = null;
            }
        });
        reinitialiser.setVisible(false);
        accueil.add(reinitialiser);

        valider = new JButton("Valider");
        valider.setFont(new Font("Arial", Font.PLAIN, 15));
        valider.setSize(screenWidth - 250, 50);
        valider.setLocation(50, screenHeight - 170);
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    switch (option) {
                        case "creer":
                            ControllerAccueil.creer(choix);
                            break;
                        case "modifier":
                            ControllerAccueil.modifier(choix, societe);
                            break;
                        case "supprimer":
                            ControllerAccueil.supprimer(choix, societe);
                            break;
                        case "afficher":
                            ControllerAccueil.afficher(choix);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Pb Logiciel, le logiciel va fermer");
                            System.exit(1);
                            break;
                    }
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
        valider.setVisible(false);
        accueil.add(valider);

        setVisible(true);
    }

    private void onClose() {
        // add your code here if necessary
        dispose();
    }

    private void choixSociete (String choix) throws DaoException {
        ArrayList arrayList = ControllerAccueil.listeSociete(choix);
        societes = (String[]) arrayList.toArray(new String[arrayList.size()]);

        jComboBoxSociete = new JComboBox(societes);
        jComboBoxSociete.setFont(new Font("Arial", Font.PLAIN, 15));
        jComboBoxSociete.setSize(500, 50);
        jComboBoxSociete.setLocation((screenWidth-250)/2-200, screenHeight/2);
        accueil.add(jComboBoxSociete);
        jComboBoxSociete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                societe = (String) jComboBoxSociete.getSelectedItem();
            }
        });
    }
}
