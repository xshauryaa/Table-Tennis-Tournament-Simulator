package ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Tournament;
import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;
import ui.dialogs.AskMatchIdDialog;
import ui.dialogs.AskPlayerNameDialog;
import ui.dialogs.RankingTableDialog;
import ui.dialogs.SaveTournamentDialog;

// Represents the side menu that offers options like save, quit, etc.
public class SideMenuPanel extends JPanel {
    private TableTennisTournamentSimulatorApp owner;
    private Tournament tournament;
    private JButton nextRoundButton;
    
    // EFFECTS: draws the side menu that is displayed while tournament
    //          is being simulated
    public SideMenuPanel(TableTennisTournamentSimulatorApp owner) {
        super();
        this.owner = owner;
        this.tournament = owner.getTournament();
        setSize(StyleGuide.SIDE_MENU_WIDTH, StyleGuide.PANEL_HEIGHT);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        setLayout(new BorderLayout());
        addTitleLabel();
        addButtonsPanel();
        addQuitButton();
    }

    // MODIFIES: this
    // EFFECTS: makes a label for "Menu" and adds it to the side menu
    private void addTitleLabel() {
        JLabel l = new JLabel();
        l.setText("Menu");
        l.setForeground(Color.BLACK);
        l.setFont(StyleGuide.BOLD_FONT_75);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setVerticalAlignment(JLabel.TOP);
        add(l, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: makes a panel that contains all buttons for menu options
    private void addButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setSize(StyleGuide.SIDE_MENU_WIDTH, 350);
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new GridLayout(5, 1, 4, 4));
        buttonsPanel.add(viewRankingTableButton());
        buttonsPanel.add(viewMatchDetailsButton());
        buttonsPanel.add(viewPlayerStatsButton());
        buttonsPanel.add(saveButton());
        nextRoundButton = playNextRoundButton();
        buttonsPanel.add(nextRoundButton);
        add(buttonsPanel);
    }

    // EFFECTS: creates a JButton to view ranking table and returns it
    private JButton viewRankingTableButton() {
        JButton btn = new JButton("View Ranking Table");
        btn.setSize(150, 60);
        btn.setBackground(Color.BLACK);
        btn.setIcon(new ImageIcon("./data/assets/ViewRankingsIcon.png"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
                RankingTableDialog rtd = new RankingTableDialog(owner, tournament.getRankingTable());
                rtd.setVisible(true);
            }
        });
        return btn;
    }

    // EFFECTS: creates a JButton to view match details and returns it
    private JButton viewMatchDetailsButton() {
        JButton btn = new JButton("View Match Details");
        btn.setSize(150, 60);
        btn.setBackground(Color.BLACK);
        btn.setIcon(new ImageIcon("./data/assets/ViewMatchDetailsIcon.png"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
                AskMatchIdDialog amid = new AskMatchIdDialog(owner);
                amid.setVisible(true);
            }
        });
        return btn;
    }

    // EFFECTS: creates a JButton to view player statistics and returns it
    private JButton viewPlayerStatsButton() {
        JButton btn = new JButton("View Player Statistics");
        btn.setSize(150, 60);
        btn.setBackground(Color.BLACK);
        btn.setIcon(new ImageIcon("./data/assets/ViewPlayerStatsIcon.png"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
                AskPlayerNameDialog apmd = new AskPlayerNameDialog(owner);
                apmd.setVisible(true);
            }
        });
        return btn;
    }

    // EFFECTS: creates a JButton to save tournament progress and returns it
    private JButton saveButton() {
        JButton btn = new JButton("Save Progress");
        btn.setSize(150, 60);
        btn.setBackground(Color.BLACK);
        btn.setIcon(new ImageIcon("./data/assets/SaveIcon.png"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.saveTournament();
            }
        });
        return btn;
    }

    // EFFECTS: creates a JButton to continue to next round and returns it
    private JButton playNextRoundButton() {
        JButton btn = new JButton("Play Next Round");
        btn.setSize(150, 60);
        btn.setBackground(Color.BLACK);
        btn.setIcon(new ImageIcon("./data/assets/PlayNextRoundIcon.png"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
                owner.goToNextRound();
                btn.setEnabled(false);
            }
        });
        btn.setEnabled(false);
        return btn;
    }

    public JButton getNextRoundButton() {
        return nextRoundButton;
    }

    public void enableNextRoundButton() {
        nextRoundButton.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a JButton to quit application and adds it to side menu
    private void addQuitButton() {
        JButton quitBtn = new JButton("Quit Application");
        quitBtn.setSize(150, 60);
        quitBtn.setIcon(new ImageIcon("./data/assets/QuitIcon.png"));
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveTournamentDialog std = new SaveTournamentDialog(owner);
                std.setVisible(true);
            }
        });
        add(quitBtn, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: updates the fields to their current values
    private void updateFields() {
        tournament = owner.getTournament();
    }
}
