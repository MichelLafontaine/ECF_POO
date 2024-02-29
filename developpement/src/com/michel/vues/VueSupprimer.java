package com.michel.vues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueSupprimer extends JFrame{

    private Container c;
    private Dimension screenSize;
    private int screenWidth;
    private int screenHeight;
    private JLabel title;
    private JLabel reverso;
    private JButton exit;
    private JLabel trait;
    private JButton accueil;

    public VueSupprimer (){

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

        setVisible(true);
    }

    private void onClose() {
        // add your code here if necessary
        dispose();
    }
}
