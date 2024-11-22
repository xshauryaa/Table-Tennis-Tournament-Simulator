package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;

import model.Player;
import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;

// Represents the dialog box that shows the statistics of given player
public class PlayerStatisticsDialog extends JDialog {
    private Player player;
    
    // EFFECTS: creates a dialog that shows statistics of given player
    public PlayerStatisticsDialog(TableTennisTournamentSimulatorApp owner, Player p) {
        super(owner, "Player Statistics", false);
        player = p;
        setSize(255, 550);
        setLayout(new BorderLayout());
        JPanel main = new JPanel();
        main.setBackground(Color.BLACK);
        main.setLayout(null);
        main.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        add(main);
        addMatchHistory();
        addStatsPanel();
        addPlayerPanel();
        centreOnScreen();
    }

    // EFFECTS: creates a panel to show image, name, and ovr of player and adds it to the dialog
    private void addPlayerPanel() {
        JPanel playerPanel = new JPanel();
        playerPanel.setSize(255, 100);
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setLayout(null);
        playerPanel.setBounds(0, 0, 255, 100);
        addImage(playerPanel);
        addNameOvr(playerPanel);
        add(playerPanel);
    }

    // EFFECTS: adds the default player image to given panel
    private void addImage(JPanel panel) {
        JLabel playerImageLabel = new JLabel();
        playerImageLabel.setIcon(new ImageIcon("./data/assets/DefaultPlayerImage.png"));
        playerImageLabel.setBounds(27, 27, 46, 46);
        panel.add(playerImageLabel);
    }

    // EFFECTS: adds the player's name and overall ability to given panel
    private void addNameOvr(JPanel panel) {
        JLabel playerName = new JLabel(player.getName());
        playerName.setForeground(Color.WHITE);
        playerName.setFont(StyleGuide.PLAIN_FONT_12);
        JLabel playerOvr = new JLabel(Integer.toString(player.getOverallAbility()));
        playerOvr.setForeground(Color.WHITE);
        playerOvr.setFont(StyleGuide.PLAIN_FONT_12);
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        detailsPanel.setBackground(Color.BLACK);
        detailsPanel.setSize(155, 100);
        detailsPanel.setBounds(100, 0, 155, 100);
        detailsPanel.add(playerName);
        detailsPanel.add(playerOvr);
        panel.add(detailsPanel);
    }

    // EFFECTS: creates a panel to show all statistics of the player and adds it to the dialog
    private void addStatsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setSize(255, 225);
        statsPanel.setBackground(Color.BLACK);
        statsPanel.setLayout(new GridLayout(6, 1, 1, 1));
        statsPanel.setBounds(0, 100, 255, 250);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(27, 27, 27, 27));
        addMatchStatsLabels(statsPanel);
        addPointStatsLabels(statsPanel);
        addStatusLabel(statsPanel);
        add(statsPanel);
    }

    private void addMatchStatsLabels(JPanel panel) {
        JLabel matchesWon = new JLabel("Matches Won: " + player.getMatchesWon());
        matchesWon.setForeground(Color.WHITE);
        matchesWon.setFont(StyleGuide.PLAIN_FONT_12);
        JLabel matchesLost = new JLabel("Matches Lost: " + player.getMatchesLost());
        matchesLost.setForeground(Color.WHITE);
        matchesLost.setFont(StyleGuide.PLAIN_FONT_12);
        panel.add(matchesWon);
        panel.add(matchesLost);
    }

    private void addPointStatsLabels(JPanel panel) {
        JLabel pointsWon = new JLabel("Points Won: " + player.getPointsWon());
        pointsWon.setForeground(Color.WHITE);
        pointsWon.setFont(StyleGuide.PLAIN_FONT_12);
        JLabel pointsLost = new JLabel("Points Lost: " + player.getPointsConceded());
        pointsLost.setForeground(Color.WHITE);
        pointsLost.setFont(StyleGuide.PLAIN_FONT_12);
        JLabel pointsDiff = new JLabel("Points Difference: " + player.getPointsDifference());
        pointsDiff.setForeground(Color.WHITE);
        pointsDiff.setFont(StyleGuide.PLAIN_FONT_12);
        panel.add(pointsWon);
        panel.add(pointsLost);
        panel.add(pointsDiff);
    }

    private void addStatusLabel(JPanel panel) {
        JLabel status = new JLabel();
        status.setForeground(Color.WHITE);
        status.setFont(StyleGuide.PLAIN_FONT_12);
        if (player.isEliminated()) { status.setText("Eliminated"); }
        else { status.setText("Qualified"); }
        panel.add(status);
    }

    private void addMatchHistory() {
        JPanel history = new JPanel();
        history.setBackground(Color.BLACK);
        history.setBounds(0, 325, 255, 225);
        history.setLayout(new GridLayout(player.getMatchHistory().size() + 1, 1, 1, 1));
        history.setBorder(BorderFactory.createEmptyBorder(2, 27, 27, 27));
        JLabel matchHistory = new JLabel("Match History");
        matchHistory.setForeground(Color.WHITE);
        matchHistory.setFont(StyleGuide.PLAIN_FONT_12);
        history.add(matchHistory);
        for (String match : player.getMatchHistory()) {
            JLabel m = new JLabel(match);
            m.setForeground(Color.WHITE);
            m.setFont(StyleGuide.PLAIN_FONT_12);
            history.add(m);
        }
        add(history);
    }

    // MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
