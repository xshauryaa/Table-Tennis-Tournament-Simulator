package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.GridLayout;

import javax.swing.*;

import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;

// Represents the welcome menu panel of the application
public class OpeningMenuPanel extends JPanel {
    private TableTennisTournamentSimulatorApp owner;

    private static final int BUTTON_WIDTH = 255;
    private static final int BUTTON_HEIGHT = 255;

    // EFFECTS: draws the opening menu for the application 
    public OpeningMenuPanel(TableTennisTournamentSimulatorApp owner) {
        super();
        this.owner = owner;
        setSize(StyleGuide.SPECIAL_PANEL_WIDTH, StyleGuide.PANEL_HEIGHT);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setLayout(new GridLayout(2, 1, 36, 36));
        addWelcomeLabel();
        addButtonsPanel();
    }

    // EFFECTS: creates and adds a panel containing the action buttons
    private void addButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 24, 24));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setSize(StyleGuide.SPECIAL_PANEL_WIDTH, 400);

        ImageIcon startNewImage = new ImageIcon("./data/assets/StartTournament.png");
        startNewImage.setImage(startNewImage.getImage().getScaledInstance(BUTTON_WIDTH, 
                                                                            BUTTON_HEIGHT, 
                                                                            Image.SCALE_DEFAULT));
        ImageIcon loadImage = new ImageIcon("./data/assets/LoadTournament.png");
        loadImage.setImage(loadImage.getImage().getScaledInstance(BUTTON_WIDTH, 
                                                                    BUTTON_HEIGHT, 
                                                                    Image.SCALE_DEFAULT));

        JButton startNewBtn = getButtonForMenu(startNewImage, "start");
        JLabel startLabel = makeLabel("Start New Tournament", Color.BLACK, 30);
        JButton loadBtn = getButtonForMenu(loadImage, "load");
        JLabel loadLabel = makeLabel("Load Tournament", Color.BLACK, 30);

        buttonsPanel.add(getPanelWithButtonLabel(startNewBtn, startLabel));
        buttonsPanel.add(getPanelWithButtonLabel(loadBtn, loadLabel));
        add(buttonsPanel);
    }

    // EFFECTS: makes a label for "Welcome!" and adds it to the opening menu
    private void addWelcomeLabel() {
        JLabel l = new JLabel();
        l.setText("Welcome!");
        l.setForeground(Color.BLACK);
        l.setFont(StyleGuide.BOLD_FONT_100);
        l.setHorizontalAlignment(JLabel.CENTER);
        add(l);
    }

    // EFFECTS: returns a JLabel made with given color, size, and text in preset font
    private JLabel makeLabel(String text, Color c, int size) {
        JLabel l = new JLabel();
        l.setText(text);
        l.setForeground(c);
        l.setFont(new Font("Arial", Font.BOLD, size));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        return l;
    }

    // EFFECTS: returns a JButton with specified image icon and function
    private JButton getButtonForMenu(ImageIcon image, String function) {
        JButton btn = new JButton();
        btn.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        btn.setIcon(image);
        if (function.equals("start")) {
            // lambda functionality for action listener by: Java GUI: Full Course ☕ (FREE)
            // https://www.youtube.com/watch?v=Kmgo00avvEw&t=2s
            btn.addActionListener(e -> owner.makeNewTournament());
        } else if (function.equals("load")) {
            btn.addActionListener(e -> owner.loadTournament());
        }
        btn.setVisible(true);
        return btn;
    }

    // EFFECTS: returns a JPanel with a button and it's text below it
    private JPanel getPanelWithButtonLabel(JButton btn, JLabel l) {
        JPanel p = new JPanel();
        p.setSize(BUTTON_WIDTH + 50, 500);
        p.setBackground(Color.WHITE);
        p.add(btn, BorderLayout.CENTER);
        p.add(l, BorderLayout.CENTER);
        return p;
    }
}
