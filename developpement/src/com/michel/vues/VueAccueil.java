package com.michel.vues;

import com.michel.controllers.ControllerAccueil;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.EnumOption;
import com.michel.utilitaires.LoggerReverso;

import javax.print.attribute.standard.MediaSize;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

/**
 * Classe de la vue Accueil
 */
public class VueAccueil extends JFrame {

    private Container accueil;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel titleAccueil;
    private JLabel reverso;
    private JButton exit;
    private JSeparator trait;
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
    private ChoixClientProspect choix;
    private EnumOption option;
    private String societe;
    private ControllerAccueil controllerAccueil;
    private final static String NAME_FONT = "Arial";
    private static final String MESSAGE_EXCEPTION = "Erreur, le logiciel va fermer";

    /**
     * contructeur Vue Accueil
     */
    public VueAccueil() {
        //Initilisation du container
        setTitle("Page d'accueil");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        accueil = getContentPane();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        //Creation et initialisation des éléments
        titleAccueil = new JLabel("ACCUEIL");
        titleAccueil.setFont(new Font(NAME_FONT, Font.PLAIN, 30));

        reverso = new JLabel("REVERSO");
        reverso.setFont(new Font(NAME_FONT, Font.BOLD, 25));


        exit = new JButton("Quitter");
        exit.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        exit.setPreferredSize(new Dimension(250, 50));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });

        trait = new JSeparator();


        client = new JButton("Client");
        client.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        client.setPreferredSize(new Dimension(250, 50));
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
                choix = ChoixClientProspect.CLIENT;
            }
        });

        prospect = new JButton("Prospect");
        prospect.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        prospect.setPreferredSize(new Dimension(250, 50));
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
                choix = ChoixClientProspect.PROSPECT;
            }
        });

        creer = new JButton("Créer");
        creer.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        creer.setPreferredSize(new Dimension(250, 50));
        creer.setVisible(false);
        creer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                modifier.setVisible(false);
                supprimer.setVisible(false);
                afficher.setVisible(false);
                option = EnumOption.CREER;
            }
        });


        modifier = new JButton("Modifier");
        modifier.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        modifier.setPreferredSize(new Dimension(250, 50));
        modifier.setVisible(false);
        modifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                creer.setVisible(false);
                supprimer.setVisible(false);
                afficher.setVisible(false);
                option = EnumOption.MODIFIER;
                try {
                    choixSociete(gbc);
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur BDD : " + daoException.getMessage());
                    if (daoException.getCritere() == 2){
                        System.exit(1);
                    }
                } catch (Exception exception){
                    StringBuilder messageLog = new StringBuilder("Exception Accueil bouton modifier");
                    messageLog.append(exception.getMessage()).append(" ").append(e);
                    LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
                    JOptionPane.showMessageDialog(null, MESSAGE_EXCEPTION);
                    System.exit(1);
                }
            }
        });

        afficher = new JButton("Afficher");
        afficher.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        afficher.setPreferredSize(new Dimension(250, 50));
        afficher.setVisible(false);
        afficher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                modifier.setVisible(false);
                supprimer.setVisible(false);
                creer.setVisible(false);
                option = EnumOption.AFFICHER;
            }
        });

        supprimer = new JButton("Supprimer");
        supprimer.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        supprimer.setPreferredSize(new Dimension(250, 50));
        supprimer.setVisible(false);
        supprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                valider.setVisible(true);
                modifier.setVisible(false);
                creer.setVisible(false);
                afficher.setVisible(false);
                option = EnumOption.SUPPRIMER;
                try {
                    choixSociete(gbc);
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur BDD : " + daoException.getMessage());
                    if (daoException.getCritere() == 2){
                        System.exit(1);
                    }
                } catch (Exception exception){
                    StringBuilder messageLog = new StringBuilder("Exception Accueil bouton supprimer");
                    messageLog.append(exception.getMessage()).append(" ").append(e);
                    LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
                    JOptionPane.showMessageDialog(null, MESSAGE_EXCEPTION);
                    System.exit(1);
                }
            }
        });

        reinitialiser = new JButton("Réinitialiser");
        reinitialiser.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        reinitialiser.setPreferredSize(new Dimension(250, 50));
        reinitialiser.setVisible(false);
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

        valider = new JButton("Valider");
        valider.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        valider.setPreferredSize(new Dimension(250, 50));
        valider.setVisible(false);
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    controllerAccueil = new ControllerAccueil(choix, option, societe);
                    controllerAccueil.validerChoix();
                    dispose();
                } catch (MetierException metierException) {
                    JOptionPane.showMessageDialog(null, "erreur de saisie : "
                            + metierException.getMessage());
                } catch (DaoException daoException){
                    JOptionPane.showMessageDialog(null, "erreur : "
                            + daoException.getMessage());
                    if (daoException.getCritere() == 2){
                        System.exit(1);
                    }
                } catch (ControllerException controllerException) {
                    JOptionPane.showMessageDialog(null, "Le logiciel va fermer, "
                            + controllerException.getMessage());
                }catch (Exception exception){
                    StringBuilder messageLog = new StringBuilder("Exception Accueil bouton valider");
                    messageLog.append(exception.getMessage()).append(" ").append(e);
                    LoggerReverso.LOGGER.log(Level.SEVERE, messageLog.toString());
                    JOptionPane.showMessageDialog(null, MESSAGE_EXCEPTION);
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
        accueil.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        accueil.add(labels[1], gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        accueil.add(labels[2], gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        accueil.add(labels[3], gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        accueil.add(labels[4], gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        accueil.add(labels[5], gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        accueil.add(labels[6], gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        accueil.add(labels[7], gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        accueil.add(labels[8], gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        accueil.add(labels[9], gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        accueil.add(labels[10], gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        accueil.add(labels[11], gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        accueil.add(labels[12], gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        accueil.add(labels[13], gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        accueil.add(labels[14], gbc);

        //Ajout des composants en spécifiant les contraintes de types GridBagContraints
        gbc.gridx = gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        accueil.add(reverso, gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(exit, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(titleAccueil, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 5, 0, 5);
        accueil.add(trait, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(client, gbc);

        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(prospect, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(creer, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(modifier, gbc);

        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(supprimer, gbc);

        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(afficher, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(valider, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(reinitialiser, gbc);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * fermeture logiciel
     */
    private void onClose() {
        // add your code here if necessary
        dispose();
    }

    /**
     * liste déroulante raison sociale
     * @throws DaoException propagation
     */
    private void choixSociete (GridBagConstraints gbc) throws Exception {
        societes = ControllerAccueil.listeSociete(choix);
        jComboBoxSociete = new JComboBox(societes);
        jComboBoxSociete.setFont(new Font(NAME_FONT, Font.PLAIN, 25));

        jComboBoxSociete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                societe = (String) jComboBoxSociete.getSelectedItem();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        accueil.add(jComboBoxSociete, gbc);
    }
}
