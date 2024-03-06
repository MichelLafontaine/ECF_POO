package com.michel.vues;

import com.michel.controllers.ControllerAffichage;
import com.michel.exceptions.ControllerException;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VueAfficher extends JFrame{

    private Container affichage;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel title;
    private JLabel reverso;
    private JButton exit;
    private JLabel trait;
    private JButton accueil;
    private JTable tableAffichage;
    private String[] colonne;
    private String[][] listeSociete;
    DefaultTableModel tableModel;

    /**
     * contructeur Vue affichage
     * @param choix
     * @throws MetierException
     * @throws DaoException
     * @throws ControllerException
     */
    public VueAfficher(String choix) throws MetierException, DaoException, ControllerException {

        setTitle("Vue Affichage");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        affichage = getContentPane();

        title = new JLabel("AFFICHAGE DES " + choix.toUpperCase() + "S");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(screenWidth - 250, 50);
        title.setLocation(50, 150);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        affichage.add(title);

        reverso = new JLabel("REVERSO");
        reverso.setFont(new Font("Arial", Font.BOLD, 25));
        reverso.setSize(300, 50);
        reverso.setLocation(50, 50);
        reverso.setHorizontalAlignment(SwingConstants.CENTER);
        reverso.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        reverso.setVisible(true);
        affichage.add(reverso);

        exit = new JButton("Quitter");
        exit.setFont(new Font("Arial", Font.PLAIN, 15));
        exit.setSize(150,50);
        exit.setLocation(screenWidth - 200, 50);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });
        exit.setVisible(true);
        affichage.add(exit);

        trait = new JLabel();
        trait.setSize(screenWidth - 250, 2);
        trait.setLocation(50,200);
        trait.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        trait.setVisible(true);
        affichage.add(trait);

        setVisible(true);

        accueil = new JButton();
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
                    if (daoException.getCritere() == 2){
                        System.exit(1);
                    }
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Erreur, le logiciel va fermer");
                    System.exit(1);
                }
            }
        });
        accueil.setVisible(true);
        affichage.add(accueil);

        colonne =ControllerAffichage.nomColonne(choix);
        listeSociete = ControllerAffichage.findAll(choix);
        tableModel = new DefaultTableModel(listeSociete, colonne);
        tableAffichage = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAffichage);
        scrollPane.setSize(screenWidth - 500, 300);
        scrollPane.setVisible(true);
        affichage.add(scrollPane);



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
     * retour Accueil
     * @throws MetierException
     * @throws DaoException
     */
    private void onAccueil() throws MetierException, DaoException {
        dispose();
        ControllerAffichage.accueil();
    }
}
