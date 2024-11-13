package ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Match;
import model.Tournament;
import ui.TableTennisTournamentSimulatorApp;
import ui.dialogs.AddMatchDialog;

// Represents the panel where user creates tournament
public class CreateTournamentPanel extends JPanel {
    private TableTennisTournamentSimulatorApp owner;
    private Tournament tournament;

    // private static final int MATCH_DISPLAY_WIDTH = 4;
    // private int matchDisplayHeight = t.getOpeningRoundMatches().size() / 4;

    private static final int SPECIAL_PANEL_WIDTH = 945;
    private static final int PANEL_HEIGHT = 700;
    private static final Dimension BUTTON_DIM = new Dimension(250, 100);
    
    // EFFECTS: draws the panel where tournament is created for the application 
    public CreateTournamentPanel(TableTennisTournamentSimulatorApp owner) {
        super();
        this.owner = owner;
        this.tournament = owner.getTournament();
        setSize(SPECIAL_PANEL_WIDTH, PANEL_HEIGHT);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        createTitlePanel();
        createButtonsMenu();
        createMatchesDisplayPanel();
    }

    // EFFECTS: adds the panel with name of the tournament
    private void createTitlePanel() {
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBounds(0, 0, SPECIAL_PANEL_WIDTH, 120);
        JLabel l = new JLabel();
        l.setText(tournament.getName());
        l.setForeground(Color.BLACK);
        l.setFont(new Font("Arial", Font.BOLD, 75));
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.CENTER);
        titlePanel.add(l);
        add(titlePanel);
    }

    // EFFECTS: adds the menu of buttons with options
    private void createButtonsMenu() {
        JPanel buttonsMenu = new JPanel();
        buttonsMenu.setLayout(new GridLayout(1, 3, 24, 24));
        buttonsMenu.setBackground(Color.WHITE);
        buttonsMenu.setBounds(0, 120, SPECIAL_PANEL_WIDTH, 150);
        JButton addMatchBtn = addMatchButton(this);
        buttonsMenu.add(addMatchBtn);
        // buttonsMenu.add(removeMatchButton());
        // buttonsMenu.add(filterMatchesDropdown());
        add(buttonsMenu);
    }

    // EFFECTS: returns a button to add matches
    private JButton addMatchButton(CreateTournamentPanel ctp) {
        JButton addMatchBtn = new JButton("Add Match");
        addMatchBtn.setPreferredSize(BUTTON_DIM);
        addMatchBtn.setBackground(Color.BLACK);
        addMatchBtn.setForeground(Color.WHITE);
        addMatchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMatchDialog amd = new AddMatchDialog(owner, ctp);
                amd.setVisible(true);
            }
        });
        return addMatchBtn;
    }

    // EFFECTS: adds the panel where matches are displayed
    private void createMatchesDisplayPanel() {
        JPanel matchDisplayPanel = new JPanel();
        matchDisplayPanel.setBackground(Color.WHITE);
        matchDisplayPanel.setBounds(0, 270, SPECIAL_PANEL_WIDTH, 700);
        matchDisplayPanel.setLayout(new GridLayout(8, 4, 12, 12));
        for (Match m : tournament.getOpeningRoundMatches()) {
            JPanel matchCard = drawMatchCard(m);
            matchDisplayPanel.add(matchCard);
        }
        // JScrollPane: Referred from StackOverflow
        // https://stackoverflow.com/a/6582086
        JScrollPane scrollFrame = new JScrollPane(matchDisplayPanel);
        matchDisplayPanel.setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension(SPECIAL_PANEL_WIDTH,400));
        add(scrollFrame);
    }
         
    private JPanel drawMatchCard(Match m) {
        JPanel matchCard = new JPanel(new GridBagLayout());
        matchCard.setPreferredSize(new Dimension(150, 75));
        matchCard.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        matchIdPanel(m, matchCard, gbc);
        playersPanel(matchCard, gbc);
        
        return matchCard;
    }

    private void playersPanel(JPanel matchCard, GridBagConstraints gbc) {
        JPanel players = new JPanel(new GridLayout(3, 1, 10, 10));
        ImageIcon defaultPlayerImage = new ImageIcon("./data/assets/DefaultPlayerImage.png");
        defaultPlayerImage.setImage(defaultPlayerImage.getImage().getScaledInstance(45, 45, 
                                                                                    Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(defaultPlayerImage);
        imageLabel.setPreferredSize(new Dimension(45, 45));
        JLabel vsLabel = new JLabel("vs.");
        vsLabel.setForeground(Color.WHITE);
        vsLabel.setFont(new Font("Arial", Font.BOLD, 30));
        players.add(imageLabel);
        players.add(vsLabel);
        players.add(imageLabel);
        matchCard.add(players, gbc);
    }

    private void matchIdPanel(Match m, JPanel matchCard, GridBagConstraints gbc) {
        JPanel matchTitle = new JPanel();
        JLabel  matchID = new JLabel("Match " + m.getId());
        matchID.setForeground(Color.WHITE);
        matchID.setFont(new Font("Arial", Font.PLAIN, 12));
        matchID.setHorizontalAlignment(JLabel.CENTER);
        matchID.setVerticalAlignment(JLabel.CENTER);
        matchTitle.add(matchID);
        matchCard.add(matchTitle, gbc);
    }
}
