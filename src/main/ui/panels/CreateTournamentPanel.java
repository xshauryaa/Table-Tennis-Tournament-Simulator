package ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import model.Match;
import model.Tournament;
import ui.TableTennisTournamentSimulatorApp;
import ui.StyleGuide;
import ui.dialogs.AddMatchDialog;

// Represents the panel where user creates tournament
public class CreateTournamentPanel extends JPanel {
    private TableTennisTournamentSimulatorApp owner;
    private Tournament tournament;
    private JPanel matchDisplayPanel;
    private ArrayList<Match> matchesToDisplay;

    private static final Dimension BUTTON_DIM = new Dimension(200, 80);
    private static final int INTERVAL = 10;
    
    // EFFECTS: draws the panel where tournament is created for the application 
    public CreateTournamentPanel(TableTennisTournamentSimulatorApp owner) {
        super();
        this.owner = owner;
        this.tournament = owner.getTournament();
        matchesToDisplay = tournament.getOpeningRoundMatches();
        setSize(StyleGuide.SPECIAL_PANEL_WIDTH, StyleGuide.PANEL_HEIGHT);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setBackground(Color.WHITE);
        setLayout(null);
        createTitlePanel();
        createOptionsMenu();
        createMatchesDisplayPanel();
        addTimer();
    }

    // EFFECTS: adds the panel with name of the tournament
    private void createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBounds(0, 0, 900, 120);
        JLabel l = new JLabel();
        l.setText(tournament.getName());
        l.setForeground(Color.BLACK);
        l.setFont(StyleGuide.BOLD_FONT_75);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        titlePanel.add(l);
        add(titlePanel);
    }

    // EFFECTS: adds the menu of buttons with options
    private void createOptionsMenu() {
        JPanel optionsMenu = new JPanel();
        optionsMenu.setLayout(new GridLayout(1, 3, 24, 24));
        optionsMenu.setBackground(Color.WHITE);
        optionsMenu.setBounds(22, 120, 900, 150);
        optionsMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton addMatchBtn = addMatchButton();
        addMatchBtn.setBounds(0, 0, 200, 80);
        optionsMenu.add(addMatchBtn);
        JButton beginTournamentButton = beginTournamentButton();
        beginTournamentButton.setBounds(250, 0, 200, 80);
        optionsMenu.add(beginTournamentButton);
        // buttonsMenu.add(filterMatchesDropdown());
        add(optionsMenu);
    }

    // EFFECTS: returns a button to add matches
    private JButton addMatchButton() {
        JButton addMatchBtn = new JButton("Add Match");
        addMatchBtn.setPreferredSize(BUTTON_DIM);
        addMatchBtn.setBackground(Color.BLACK);
        addMatchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMatchDialog amd = new AddMatchDialog(owner);
                amd.setVisible(true);
            }
        });
        return addMatchBtn;
    }

    // EFFECTS: returns a button to add matches
    private JButton beginTournamentButton() {
        JButton beginTournamentBtn = new JButton("Begin Tournament");
        beginTournamentBtn.setPreferredSize(BUTTON_DIM);
        beginTournamentBtn.setBackground(Color.BLACK);
        beginTournamentBtn.addActionListener(e -> owner.startTournament());
        return beginTournamentBtn;
    }

    // EFFECTS: adds the panel where matches are displayed
    private void createMatchesDisplayPanel() {
        double numMatches = (double) tournament.getOpeningRoundMatches().size();
        int rows = (int) Math.ceil(numMatches / 4);
        matchDisplayPanel = new JPanel();
        matchDisplayPanel.setBackground(Color.WHITE);
        matchDisplayPanel.setBounds(22, 270, 900, (rows * 90) + 30);
        matchDisplayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        matchDisplayPanel.setLayout(new GridLayout(rows, 4, 12, 12));
        for (Match m : matchesToDisplay) {
            JPanel matchCard = StyleGuide.drawMatchCard(m);
            matchDisplayPanel.add(matchCard);
        }
        add(matchDisplayPanel);
    }

    // EFFECTS: initializes a timer that updates game each
	//          few milliseconds
    private void addTimer() {
		Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                matchesToDisplay = tournament.getOpeningRoundMatches();
                createMatchesDisplayPanel();
                matchDisplayPanel.revalidate();
                matchDisplayPanel.repaint();
            }
        });
		t.start();
    }
}
