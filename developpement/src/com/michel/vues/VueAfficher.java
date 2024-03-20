package com.michel.vues;

import com.michel.controllers.ControllerAffichage;
import com.michel.exceptions.DaoException;
import com.michel.exceptions.MetierException;
import com.michel.metiers.Client;
import com.michel.metiers.Prospect;
import com.michel.utilitaires.ChoixClientProspect;
import com.michel.utilitaires.Utilitaires;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

/**
 * Claase de la vue affichage données client/prospeect
 */
public class VueAfficher extends JFrame{

    private ChoixClientProspect choix;
    private Container affichage;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel titleLabel;
    private JLabel reverso;
    private JButton exit;
    private JSeparator trait;
    private JButton accueil;
    private JTable tableAffichage;
    private String[] colonne;
    private List listeSocietes;
    private String[][] tableauSocietes;
    private DefaultTableModel tableModel;
    private static final String NAME_FONT = "Arial";

    /**
     * contructeur Vue affichage
     * @param choix String client ou prospect
     */
    public VueAfficher(ChoixClientProspect choix) {

        this.choix = choix;
        setTitle("Vue Affichage");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        affichage = getContentPane();

        titleLabel = new JLabel("AFFICHAGE DES " + choix + "S");
        titleLabel.setFont(new Font(NAME_FONT, Font.PLAIN, 30));

        reverso = new JLabel("REVERSO");
        reverso.setFont(new Font(NAME_FONT, Font.BOLD, 25));
        reverso.setVisible(true);

        exit = new JButton("Quitter");
        exit.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        exit.setPreferredSize(new Dimension(250, 50));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });
        exit.setVisible(true);

        trait = new JSeparator();

        accueil = new JButton();
        accueil = new JButton("Accueil");
        accueil.setFont(new Font(NAME_FONT, Font.PLAIN, 15));
        accueil.setPreferredSize(new Dimension(250, 50));
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

        colonne = nomColonne();
        try {
            ControllerAffichage controllerAffichage = new ControllerAffichage(choix);
            listeSocietes = controllerAffichage.findAll();
            tableauSocietes = getTableauSocietes(listeSocietes);
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

        tableModel = new DefaultTableModel(tableauSocietes, colonne);
        tableAffichage = new JTable(tableModel);
        TableColumnModel columnModel = tableAffichage.getColumnModel();
        int tailleColonne = 100;
        for (int i = 0; i < 9; i++){
            columnModel.getColumn(i).setPreferredWidth(tailleColonne);
        }
        columnModel.getColumn(9).setPreferredWidth(tailleColonne*2);

        tableAffichage.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        JScrollPane scrollPane = new JScrollPane(tableAffichage);

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
        affichage.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        affichage.add(labels[1], gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        affichage.add(labels[2], gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        affichage.add(labels[3], gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        affichage.add(labels[4], gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        affichage.add(labels[5], gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        affichage.add(labels[6], gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        affichage.add(labels[7], gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        affichage.add(labels[8], gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        affichage.add(labels[9], gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        affichage.add(labels[10], gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        affichage.add(labels[11], gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        affichage.add(labels[12], gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        affichage.add(labels[13], gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        affichage.add(labels[14], gbc);

        gbc.gridx = gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        affichage.add(reverso, gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        affichage.add(exit, gbc);

        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridheight = gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        affichage.add(accueil, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.BASELINE;
        gbc.insets = new Insets(5, 0, 10, 10);
        affichage.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 5, 0, 5);
        affichage.add(trait, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.gridwidth = 5;
        gbc.gridheight = 7;
        affichage.add(scrollPane, gbc);


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
     * @throws MetierException propagation
     * @throws DaoException propagation
     */
    private void onAccueil() throws MetierException, DaoException {
        dispose();
        ControllerAffichage.accueil();
    }
    /**
     * nomColonne
     * @return String[] entetes de colonne
     */

    private String[] nomColonne() {
        if (choix.equals(ChoixClientProspect.CLIENT)) {
            return new String[]{"Raison Sociale", "N°", "Nom de rue", "CP", "Ville", "Email", "Téléphone", "CA",
                    "Nbre d'employé", "commentaire"};
        }
        if (choix.equals(ChoixClientProspect.PROSPECT)) {
            return new String[]{"Raison Sociale", "N°", "Nom de rue", "CP", "Ville", "Email", "Téléphone",
                    "Date Prospection", "Intérêt", "commentaire"};
        } else {
            return new String[0];
        }
    }

    private String[][] getTableauSocietes(List listeSocietes) {
        String[][] tableauSocietes = new String[listeSocietes.size()][10];
        if (choix.equals(ChoixClientProspect.CLIENT)) {
            for (int i = 0; i < listeSocietes.size(); i++) {
                Client client = (Client) listeSocietes.get(i);
                tableauSocietes[i][0] = client.getRaisonSociale();
                tableauSocietes[i][1] = client.getAdresse().getNumero();
                tableauSocietes[i][2] = client.getAdresse().getNomRue();
                tableauSocietes[i][3] = String.valueOf(client.getAdresse().getCodePostal());
                tableauSocietes[i][4] = client.getAdresse().getVille();
                tableauSocietes[i][5] = client.getEmail();
                tableauSocietes[i][6] = client.getTelephone();
                tableauSocietes[i][7] = String.valueOf(client.getChiffreAffaire());
                tableauSocietes[i][8] = String.valueOf(client.getNbreEmploye());
                tableauSocietes[i][9] = client.getCommentaire();
            }
        } else if (choix.equals(ChoixClientProspect.PROSPECT)) {
            for (int i = 0; i < listeSocietes.size(); i++) {
                Prospect prospect = (Prospect) listeSocietes.get(i);
                tableauSocietes[i][0] = prospect.getRaisonSociale();
                tableauSocietes[i][1] = prospect.getAdresse().getNumero();
                tableauSocietes[i][2] = prospect.getAdresse().getNomRue();
                tableauSocietes[i][3] = String.valueOf(prospect.getAdresse().getCodePostal());
                tableauSocietes[i][4] = prospect.getAdresse().getVille();
                tableauSocietes[i][5] = prospect.getEmail();
                tableauSocietes[i][6] = prospect.getTelephone();
                tableauSocietes[i][7] = prospect.getDateProspect().format(Utilitaires.formatDate());
                String interet = "";
                if (prospect.getInteretProspect() == 1) {
                    interet = "oui";
                }
                if (prospect.getInteretProspect() == 0) {
                    interet = "non";
                }
                tableauSocietes[i][8] = interet;
                tableauSocietes[i][9] = prospect.getCommentaire();
            }
        }
        return tableauSocietes;
    }
}
