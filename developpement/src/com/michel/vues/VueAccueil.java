package com.michel.vues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueAccueil extends JFrame {

    private Container c;
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

    public VueAccueil (){

        setTitle("Page d'accueil");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("ACCUEIL");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(screenWidth - 250, 50);
        title.setLocation(50, 150);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        c.add(title);

        reverso = new JLabel("REVERSO");
        reverso.setFont(new Font("Arial", Font.BOLD, 25));
        reverso.setSize(300, 50);
        reverso.setLocation(50, 50);
        reverso.setHorizontalAlignment(SwingConstants.CENTER);
        reverso.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        c.add(reverso);

        exit = new JButton("Quitter");
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setSize(150,50);
        exit.setLocation(screenWidth - 200, 50);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });
        c.add(exit);

        trait = new JLabel();
        trait.setSize(screenWidth - 250, 2);
        trait.setLocation(50,200);
        trait.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        c.add(trait);

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
            }
        });
        c.add(client);

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
            }
        });
        c.add(prospect);

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
            }
        });
        creer.setVisible(false);
        c.add(creer);

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
            }
        });
        modifier.setVisible(false);
        c.add(modifier);

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
            }
        });
        afficher.setVisible(false);
        c.add(afficher);

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
            }
        });
        supprimer.setVisible(false);
        c.add(supprimer);

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
            }
        });
        reinitialiser.setVisible(false);
        c.add(reinitialiser);

        valider = new JButton("Valider");
        valider.setFont(new Font("Arial", Font.PLAIN, 15));
        valider.setSize(screenWidth - 250, 50);
        valider.setLocation(50, screenHeight - 170);
        valider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Message");
            }
        });
        valider.setVisible(false);
        c.add(valider);

        setVisible(true);
    }

    private void onClose() {
        // add your code here if necessary
        dispose();
    }
}
