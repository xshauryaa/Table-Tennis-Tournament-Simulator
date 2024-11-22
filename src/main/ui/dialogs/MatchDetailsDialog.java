package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import model.Match;
import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;

// Represents the dialog box that shows the details of given match
public class MatchDetailsDialog extends JDialog {
    private Match match;
    
    // EFFECTS: creates a dialog that shows details of given match
    public MatchDetailsDialog(TableTennisTournamentSimulatorApp owner, Match m) {
        super(owner, "Match Details", false);
        match = m;
        setSize(255, 320);
        setLayout(new BorderLayout());
        JPanel main = new JPanel();
        main.setBackground(Color.BLACK);
        main.setLayout(null);
        main.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        add(main);
        addDetailsPanel();
        addPlayerDisplay();
        addMatchID();
        centreOnScreen();
    }

    // MODIFIES: this
    // EFFECTS: creates a panel with label for "Match <MatchID>" and adds it to the dialog
    private void addMatchID() {
        JPanel matchIdPanel = new JPanel();
        matchIdPanel.setBackground(Color.BLACK);
        matchIdPanel.setBounds(0, 0, 250, 50);
        JLabel l = new JLabel();
        l.setText("Match " + match.getId());
        l.setForeground(Color.WHITE);
        l.setFont(StyleGuide.BOLD_FONT_18);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        matchIdPanel.add(l);
        add(matchIdPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a panel with display for players and adds it to the dialog
    private void addPlayerDisplay() {
        JPanel playerDisplayPanel = new JPanel();
        playerDisplayPanel.setBackground(Color.BLACK);
        playerDisplayPanel.setBounds(0, 50, 250, 100);
        playerDisplayPanel.setLayout(new GridLayout(1, 3, 4, 4));
        JPanel player1Panel = getPlayerPanel(match, 1);
        player1Panel.setBounds(5, 0, 80, 100);
        JPanel player2Panel = getPlayerPanel(match, 2);
        player2Panel.setBounds(165, 0, 80, 100);
        JPanel vsPanel = new JPanel();
        vsPanel.setBackground(Color.BLACK);
        JLabel vsLabel = new JLabel("vs.");
        vsLabel.setFont(StyleGuide.PLAIN_FONT_12);
        vsPanel.add(vsLabel);
        vsPanel.setBounds(85, 0, 80, 100);
        playerDisplayPanel.add(player1Panel);
        playerDisplayPanel.add(vsPanel);
        playerDisplayPanel.add(player2Panel);
        add(playerDisplayPanel);
    }

    // REQUIRES: playerNum == 1 || playerNum == 2
    // EFFECTS: creates a panel with a player image and player name and returns it
    private JPanel getPlayerPanel(Match match, int playerNum) {
        JLabel playerNameLabel;
        JPanel playerPanel = new JPanel(new GridLayout(2, 1, 4, 4));
        playerPanel.setSize(80, 100);
        playerPanel.setBackground(Color.BLACK);
        JLabel playerImageLabel = new JLabel();
        playerImageLabel.setIcon(new ImageIcon("./data/assets/DefaultPlayerImage.png"));
        if (playerNum == 1) {
            playerNameLabel = new JLabel(match.getPlayer1().getName());
            playerNameLabel.setFont(StyleGuide.PLAIN_FONT_12);
        } else {
            playerNameLabel = new JLabel(match.getPlayer2().getName());
            playerNameLabel.setFont(StyleGuide.PLAIN_FONT_12);
        }
        playerNameLabel.setForeground(Color.WHITE);
        playerImageLabel.setHorizontalAlignment(JLabel.CENTER);
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerPanel.add(playerImageLabel, BorderLayout.CENTER);
        playerPanel.add(playerNameLabel, BorderLayout.CENTER);
        return playerPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates a panel with set scores and winner for match and adds it to the dialog
    private void addDetailsPanel() {
        JPanel detailsPanel = new JPanel(new GridLayout(4, 1, 1, 1));
        detailsPanel.setSize(250, 130);
        detailsPanel.setBackground(Color.BLACK);
        detailsPanel.setBounds(0, 150, 250, 130);
        String p1Name = match.getPlayer1().getName();
        String p2Name = match.getPlayer2().getName();
        JLabel set1 = new JLabel("Set 1: " + match.getSetScore(1).get(p1Name) + "-" + match.getSetScore(1).get(p2Name));
        JLabel set2 = new JLabel("Set 2: " + match.getSetScore(2).get(p1Name) + "-" + match.getSetScore(2).get(p2Name));
        JLabel set3 = new JLabel("Set 3: " + match.getSetScore(3).get(p1Name) + "-" + match.getSetScore(3).get(p2Name));
        JLabel winner = null;
        try {
            winner = new JLabel("Winner: " + match.getWinner().getName());
        } catch (NullPointerException e) {
            winner = new JLabel("Match has not yet been played.");
        }
        ArrayList<JLabel> labels = new ArrayList<JLabel>(Arrays.asList(set1, set2, set3, winner));
        for (JLabel label : labels) {
            label.setForeground(Color.WHITE);
            label.setFont(StyleGuide.PLAIN_FONT_12);
            label.setHorizontalAlignment(JLabel.CENTER);
            detailsPanel.add(label);
        }
        add(detailsPanel);
    }

    // MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
