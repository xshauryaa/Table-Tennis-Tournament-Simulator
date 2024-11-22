package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Player;
import model.RankingTable;
import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;

// Represents the dialog box that shows the ranking table
public class RankingTableDialog extends JDialog {
    private TableTennisTournamentSimulatorApp owner;
    private int numPlayers;
    private RankingTable rankingTable;
    private JTable tableRender;
    
    // EFFECTS: creates a dialog that shows the ranking table to the user
    public RankingTableDialog(TableTennisTournamentSimulatorApp owner, RankingTable rt) {
        super();
        this.owner = owner;
        this.numPlayers = owner.getTournament().getListOfPlayers().size();
        this.rankingTable = rt;
        setSize(800, 300);
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setBackground(Color.WHITE);
        main.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        
        add(main);

        makeTable(main);

        centreOnScreen();
    }

    // EFFECTS: renders the table for the ranking table
    private void makeTable(JPanel main) {
        // Referenced from: #35.1 Java Swing Tutorial | JTable in Java part 1 | create a table using DefaultTableModel
        // https://www.youtube.com/watch?v=skryksKiIK0
        String[] columnNames = {"Rank", "Player", "Overall Ability", "Matches Won", "Matches Lost", 
                                "Points Won", "Points Lost", "Points Difference"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        model.addRow(columnNames);
        for (int i = 1; i <= numPlayers; i++) {
            String player = rankingTable.getPlayerAtRank(i);
            addPlayer(model, rankingTable.playerLookup(player), i);
        }
        tableRender = new JTable(model);
        tableRender.setBackground(Color.BLACK);
        tableRender.setForeground(Color.WHITE);
        main.add(tableRender);
    }

    // EFFECTS: adds the player and their stats in a row to the ranking table
    private void addPlayer(DefaultTableModel model, Player p, int rank) {
        String playerRank = Integer.toString(rank);
        String name = p.getName();
        String ovr = Integer.toString(p.getOverallAbility());
        String matchesWon = Integer.toString(p.getMatchesWon());
        String matchesLost = Integer.toString(p.getMatchesLost());
        String pointsWon = Integer.toString(p.getPointsWon());
        String pointsLost = Integer.toString(p.getPointsConceded());
        String pointsDifference = Integer.toString(p.getPointsDifference());
        String[] player = {playerRank, name, ovr, matchesWon, matchesLost, pointsWon, pointsLost, pointsDifference};
        model.addRow(player);
    }

    // MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
