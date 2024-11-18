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

// Represents the panel where user simulates quarter final matches
public class PlayQuarterFinalMatchesPanel extends JPanel {
    private TableTennisTournamentSimulatorApp owner;
    private Tournament tournament;
    
    // EFFECTS: draws the panel where opening matches are played
    public PlayQuarterFinalMatchesPanel(TableTennisTournamentSimulatorApp owner, SideMenuPanel smp) {
        super();
        this.owner = owner;
        this.tournament = this.owner.getTournament();
        setSize(StyleGuide.PANEL_WIDTH, StyleGuide.PANEL_HEIGHT);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        addTitleLabel();
        addSimulateButton(smp);
        addMatchDisplay();
    }

    // EFFECTS: makes a label for "Quarter Finals" and adds it to the screen
    private void addTitleLabel() {
        JLabel l = new JLabel();
        l.setText("Quarter Finals");
        l.setForeground(Color.BLACK);
        l.setFont(StyleGuide.BOLD_FONT_60);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.TOP);
        add(l);
    }

    // EFFECTS: makes a JButton to simulate matches and adds it to the screen
    private void addSimulateButton(SideMenuPanel smp) {
        JButton simulateBtn = new JButton("Simulate Round");
        simulateBtn.setSize(250, 100);
        simulateBtn.setBackground(Color.BLACK);
        simulateBtn.setIcon(new ImageIcon("./data/assets/PlayNextRoundIcon.png"));
        simulateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tournament.playOpeningBracket(); 
                addMatchDisplay();
                smp.getNextRoundButton().setEnabled(true);
            }
        });
        simulateBtn.setBounds(225, 100, 250, 100);
        add(simulateBtn);
    }

    // EFFECTS: creates the panel for displaying all the matches in the quarter finals
    private void addMatchDisplay() {
        JPanel matchDisplayPanel = new JPanel();
        matchDisplayPanel.setBackground(Color.WHITE);
        matchDisplayPanel.setBounds(22, 270, 900, 150);
        matchDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        matchDisplayPanel.setLayout(new GridLayout(1, 4, 12, 12));
        for (Match m : tournament.getOpeningRoundMatches()) {
            JPanel matchCard = StyleGuide.drawMatchCard(m);
            matchDisplayPanel.add(matchCard);
        }
        add(matchDisplayPanel);
    }
}
