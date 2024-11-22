package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Match;
import model.Tournament;
import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;

// Represents the panel where user simulates the final match
public class PlayFinalMatchPanel extends JPanel {
    private TableTennisTournamentSimulatorApp owner;
    private Tournament tournament;
    
    // EFFECTS: draws the panel where the final match is played
    public PlayFinalMatchPanel(TableTennisTournamentSimulatorApp owner, SideMenuPanel smp) {
        super();
        this.owner = owner;
        this.tournament = this.owner.getTournament();
        setSize(StyleGuide.PANEL_WIDTH, StyleGuide.PANEL_HEIGHT);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setBackground(Color.BLACK);
        setLayout(null);
        addTitleLabel();
        addSimulateButton(smp);
        try {
            addMatchDisplay();
        } catch (NullPointerException e) {
            // do nothing
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a label for "Final Match" and adds it to the screen
    private void addTitleLabel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBounds(0, 20, StyleGuide.PANEL_WIDTH, 120);
        JLabel l = new JLabel();
        l.setText("Grand Final");
        l.setForeground(Color.decode("#FFD700"));
        l.setFont(StyleGuide.BOLD_FONT_75);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.TOP);
        titlePanel.add(l);
        add(titlePanel);
    }

    // MODIFIES: this
    // EFFECTS: makes a JButton to simulate the final match and adds it to the screen
    private void addSimulateButton(SideMenuPanel smp) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBounds(0, 130, StyleGuide.PANEL_WIDTH, 150);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton simulateBtn = new JButton("Simulate Final Match");
        simulateBtn.setSize(250, 100);
        simulateBtn.setBackground(Color.BLACK);
        simulateBtn.setForeground(Color.decode("#FFD700"));
        simulateBtn.setIcon(new ImageIcon("./data/assets/PlayNextRoundIconGolden.png"));
        simulateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tournament.playFinalMatch();
                owner.showChampion();
            }
        });
        buttonPanel.add(simulateBtn);
        add(buttonPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel for displaying the final match
    private void addMatchDisplay() throws NullPointerException {
        Match finalMatch;
        if (tournament.getFinalMatch() == null) {
            throw new NullPointerException();
        } else {
            finalMatch = tournament.getFinalMatch();
        }
        JPanel matchCard = new JPanel();
        matchCard.setBackground(Color.decode("#FFD700"));
        matchCard.setSize(400, 200);
        matchCard.setLayout(new GridLayout(1, 3, 12, 12));
        matchCard.setBounds(140, 300, 420, 250);
        matchCard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel player1Panel = getPlayerPanel(finalMatch, 1);
        player1Panel.setBounds(0, 0, 140, 250);
        JPanel player2Panel = getPlayerPanel(finalMatch, 2);
        player2Panel.setBounds(280, 0, 140, 250);
        JPanel vsPanel = getVsPanel();
        matchCard.add(player1Panel);
        matchCard.add(vsPanel);
        matchCard.add(player2Panel);
        add(matchCard);
    }

    // EFFECTS: creates a panel that shows the JLabel "vs." and returns it
    private JPanel getVsPanel() {
        JPanel vsPanel = new JPanel();
        vsPanel.setBackground(Color.decode("#FFD700"));
        JLabel vsLabel = new JLabel("vs.");
        vsLabel.setFont(StyleGuide.BOLD_FONT_30);
        vsPanel.add(vsLabel);
        vsLabel.setBounds(140, 0, 140, 250);
        return vsPanel;
    }

    // REQUIRES: playerNum == 1 || playerNum == 2
    // EFFECTS: creates a panel with a player image and player name and returns it
    private JPanel getPlayerPanel(Match finalMatch, int playerNum) throws NullPointerException {
        JLabel playerNameLabel;
        JPanel playerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        playerPanel.setSize(140, 250);
        playerPanel.setBackground(Color.decode("#FFD700"));
        JLabel playerImageLabel = new JLabel();
        playerImageLabel.setIcon(new ImageIcon("./data/assets/FinalPlayerImage.png"));
        if (playerNum == 1) {
            playerNameLabel = new JLabel(finalMatch.getPlayer1().getName());
            playerNameLabel.setFont(StyleGuide.BOLD_FONT_30);
        } else {
            playerNameLabel = new JLabel(finalMatch.getPlayer2().getName());
            playerNameLabel.setFont(StyleGuide.BOLD_FONT_30);
        }
        playerImageLabel.setHorizontalAlignment(JLabel.CENTER);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerPanel.add(playerImageLabel, BorderLayout.CENTER);
        playerPanel.add(playerNameLabel, BorderLayout.CENTER);
        return playerPanel;
    }

    // MODIFIES: this
    // EFFECTS: updates the match display
    public void update() {
        tournament = owner.getTournament();
        try {
            addMatchDisplay();
        } catch (NullPointerException e) {
            // do nothing
        }
        revalidate();
        repaint();
    }
}
