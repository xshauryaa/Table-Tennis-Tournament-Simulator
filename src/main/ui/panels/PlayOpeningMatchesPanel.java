package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import model.Match;
import model.Tournament;
import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;

// Represents the panel where user simulates opening matches
public class PlayOpeningMatchesPanel extends JPanel {
    private TableTennisTournamentSimulatorApp owner;
    private Tournament tournament;
    private JPanel matchDisplayPanel;
    private ArrayList<Match> matchesToDisplay;
    
    // EFFECTS: draws the panel where opening matches are played
    public PlayOpeningMatchesPanel(TableTennisTournamentSimulatorApp owner, SideMenuPanel smp) {
        super();
        this.owner = owner;
        this.tournament = this.owner.getTournament();
        matchesToDisplay = tournament.getOpeningRoundMatches();
        setSize(StyleGuide.PANEL_WIDTH, StyleGuide.PANEL_HEIGHT);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setBackground(Color.WHITE);
        setLayout(null);
        addTitleLabel();
        addSimulateButton(smp);
        addMatchDisplay();
    }

    // MODIFIES: this
    // EFFECTS: makes a label for "Opening Round" and adds it to the screen
    private void addTitleLabel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBounds(0, 20, StyleGuide.PANEL_WIDTH, 120);
        JLabel l = new JLabel();
        l.setText("Opening Round");
        l.setForeground(Color.BLACK);
        l.setFont(StyleGuide.BOLD_FONT_60);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        titlePanel.add(l);
        add(titlePanel);
    }

    // MODIFIES: this
    // EFFECTS: makes a JButton to simulate matches and adds it to the screen
    private void addSimulateButton(SideMenuPanel smp) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 24, 24));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBounds(0, 100, StyleGuide.PANEL_WIDTH, 150);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton simulateBtn = new JButton("Simulate Round");
        simulateBtn.setSize(250, 100);
        simulateBtn.setBackground(Color.BLACK);
        simulateBtn.setIcon(new ImageIcon("./data/assets/PlayNextRoundIcon.png"));
        simulateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tournament.playOpeningBracket();
                tournament.getRankingTable().updateRankings();
                addCompletionLabel();
                smp.enableNextRoundButton();
            }
        });
        buttonPanel.add(simulateBtn);
        add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel for displaying all the matches in the opening round
    private void addMatchDisplay() {
        double numMatches = (double) matchesToDisplay.size();
        int rows = (int) Math.ceil(numMatches / 4);
        matchDisplayPanel = new JPanel();
        matchDisplayPanel.setBackground(Color.WHITE);
        matchDisplayPanel.setBounds(0, 270, StyleGuide.PANEL_WIDTH, (rows * 90) + 30);
        matchDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        matchDisplayPanel.setLayout(new GridLayout(rows, 4, 12, 12));
        for (Match m : matchesToDisplay) {
            JPanel matchCard = StyleGuide.drawMatchCard(m);
            matchDisplayPanel.add(matchCard);
        }
        add(matchDisplayPanel);
    }

    // MODIFIES: this
    // EFFECTS: makes a label for "Round Complete" and adds it to the screen at the bottom
    private void addCompletionLabel() {
        JPanel completionPanel = new JPanel();
        completionPanel.setBackground(Color.WHITE);
        completionPanel.setBounds(50, 600, 600, 120);
        JLabel l = new JLabel();
        l.setText("Round Complete");
        l.setForeground(Color.BLACK);
        l.setFont(StyleGuide.BOLD_FONT_30);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        completionPanel.add(l);
        add(completionPanel, BorderLayout.SOUTH);
        setComponentZOrder(completionPanel, 1);
    }
}
