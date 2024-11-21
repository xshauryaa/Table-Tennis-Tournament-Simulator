package ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.*;

import model.Match;
import model.Player;
import model.Tournament;
import ui.dialogs.TournamentNameDialog;
import ui.panels.CreateTournamentPanel;
import ui.panels.OpeningMenuPanel;
import ui.panels.PlayFinalMatchPanel;
import ui.panels.PlayOpeningMatchesPanel;
import ui.panels.PlayQuarterFinalMatchesPanel;
import ui.panels.PlaySemiFinalMatchesPanel;
import ui.panels.SideMenuPanel;

// Referenced by B02-SpaceInvadersBase
// https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// Represents the main window of the GUI of the app
public class TableTennisTournamentSimulatorApp extends JFrame {
    private Tournament tournament;
    private JPanel currentPanel;

    private OpeningMenuPanel om;
    private CreateTournamentPanel ctp;
    private PlayOpeningMatchesPanel omp;
    private PlayQuarterFinalMatchesPanel qfmp;
    private PlaySemiFinalMatchesPanel sfmp;
    private PlayFinalMatchPanel fmp;
    private SideMenuPanel smp;

    private static final int WIDTH = 1500;
    private static final int HEIGHT = 723;
    private static final int PANEL_IMAGE_WIDTH = 551;
    private static final int PANEL_IMAGE_HEIGHT = 685;
    
    // EFFECTS: sets up window in which Table Tennis Tournament Simulator App will be played
    public TableTennisTournamentSimulatorApp() {
        super("Table Tennis Tournament Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("./data/assets/GameIcon.png");
        setIconImage(icon.getImage());
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        JPanel picturePanel = getSidePicturePanel();
        add(picturePanel, BorderLayout.WEST);

        om = new OpeningMenuPanel(this);
        om.setBounds(PANEL_IMAGE_WIDTH, 0, 945, 700);
        add(om);

        smp = new SideMenuPanel(this);
        add(smp, BorderLayout.EAST);
        smp.setVisible(false);

        setVisible(true);
        centreOnScreen();
    }

    // EFFECTS: creates a panel of the display picture and returns it
    private JPanel getSidePicturePanel() {
        JLabel display = new JLabel();
        ImageIcon displayImage = new ImageIcon("./data/assets/PanelPicture.png");
        displayImage.setImage(displayImage.getImage().getScaledInstance(PANEL_IMAGE_WIDTH, 
                                                                        PANEL_IMAGE_HEIGHT, 
                                                                        Image.SCALE_DEFAULT));
        display.setIcon(displayImage);
        JPanel picturePanel = new JPanel();
        picturePanel.setSize(PANEL_IMAGE_WIDTH, PANEL_IMAGE_HEIGHT);
        picturePanel.add(display);
        picturePanel.setBounds(0, 0, PANEL_IMAGE_WIDTH, PANEL_IMAGE_HEIGHT);
        return picturePanel;
    }

	// MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // EFFECTS: initializes and sets all the panels required to play the game
    private void initializePanels() {
        // Opening Match Panel
        omp = new PlayOpeningMatchesPanel(this, smp);
        omp.setBounds(PANEL_IMAGE_WIDTH, 0, 700, 700);
        add(omp);
        omp.setVisible(false);

        // Quarter-Final Match Panel
        qfmp = new PlayQuarterFinalMatchesPanel(this, smp);
        qfmp.setBounds(PANEL_IMAGE_WIDTH, 0, 700, 700);
        add(qfmp);
        qfmp.setVisible(false);

        // Semi-Final Match Panel
        sfmp = new PlaySemiFinalMatchesPanel(this, smp);
        sfmp.setBounds(PANEL_IMAGE_WIDTH, 0, 700, 700);
        add(sfmp);
        sfmp.setVisible(false);

        // Final Match panel
        fmp = new PlayFinalMatchPanel(this, smp);
        fmp.setBounds(PANEL_IMAGE_WIDTH, 0, 700, 700);
        add(fmp);
        fmp.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: changes the current panel to the panel of the next round to be played
    public void goToNextRound() {
        int design = tournament.getDesignType();
        currentPanel.setVisible(false);
        if (currentPanel == omp && design == 2) {
            tournament.setStatus("F");
            Player finalist1 = tournament.getOpeningRoundMatches().get(0).getWinner();
            Player finalist2 = tournament.getOpeningRoundMatches().get(1).getWinner();
            tournament.setFinalMatch(new Match("F", finalist1, finalist2));
            playFinalMatch();
        } else if (currentPanel == omp && design == 3) {
            tournament.setStatus("SF");
            getTop4();
            playSFMatches();
        } else if (currentPanel == omp && design == 4) {
            tournament.setStatus("QF");
            getTop8();
            playQFMatches();
        } else if (currentPanel == qfmp) {
            tournament.setStatus("SF");
            playSFMatches();
        } else if (currentPanel == sfmp) {
            tournament.setStatus("F");
            playFinalMatch();
        }
    }

    // MODIFIES: this
    // EFFECTS: makes the quarter finals with the top 8 players and eliminates everyone else
    private void getTop8() {
        ArrayList<Player> top8 = tournament.getRankingTable().getTopPlayers(8);
        for (Player p : tournament.getListOfPlayers()) {
            if (!top8.contains(p)) {
                p.eliminate();
            }
        }
        tournament.makeQuarterFinals(top8);
    }

    // MODIFIES: this
    // EFFECTS: makes the semi finals with the top 4 players and eliminates everyone else
    private void getTop4() {
        ArrayList<Player> top4 = tournament.getRankingTable().getTopPlayers(4);
        for (Player p : tournament.getListOfPlayers()) {
            if (!top4.contains(p)) {
                p.eliminate();
            }
        }
        tournament.makeSemiFinals(top4);
    }

    // MODIFIES: this
    // EFFECTS: renders a JPanel that shows the champion of the tournament
    public void showChampion() {
        // stub // TODO
    }

    /**
     * ========================================================
     * TOURNAMENT SIMULATION LOGISTICS BELOW THIS LINE
     */

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament t) {
        this.tournament = t;
    }

    // MODIFIES: this
    // EFFECTS: makes a new tournament
    public void makeNewTournament() {
        TournamentNameDialog tnd = new TournamentNameDialog(this);
        tnd.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where tournament will be created
    public void createTournament() {
        om.setVisible(false);
        ctp = new CreateTournamentPanel(this);
        ctp.setBounds(PANEL_IMAGE_WIDTH, 0, 945, 700);
        add(ctp);
        ctp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: starts a new tournament
    public void startTournament() {
        initializePanels();
        smp.setVisible(true);
        ctp.setVisible(false);
        tournament.initiateTournament();
        if (tournament.getDesignType() == 1) {
            Match finalMatch = tournament.getOpeningRoundMatches().get(0);
            tournament.setFinalMatch(finalMatch);
            fmp.update();
            playFinalMatch();
        } else {
            playOpeningMatches();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads existing tournament from tournament.json file
    public void loadTournament() {
       // stub  // TODO
    }

    // MODIFIES: this
    // EFFECTS: saves existing tournament to tournament.json file
    public void saveTournament() {
        // stub  // TODO
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where opening matches will be simulated
    private void playOpeningMatches() {
        currentPanel = omp;
        omp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where quarter-final matches will be simulated
    private void playQFMatches() {
        qfmp.update();
        currentPanel = qfmp;
        qfmp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where semi-final matches will be simulated
    private void playSFMatches() {
        sfmp.update();
        currentPanel = sfmp;
        sfmp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where final match will be simulated
    private void playFinalMatch() {
        fmp.update();
        currentPanel = fmp;
        fmp.setVisible(true);
    }

    // EFFECTS: updates all the panels to the current state of the tournament
    // private void updatePanels() {
    //     qfmp.update();
    //     sfmp.update();
    //     fmp.update();
    // }
}
