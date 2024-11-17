package ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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

    private OpeningMenuPanel om;
    private CreateTournamentPanel ctp;
    private PlayOpeningMatchesPanel omp;
    private PlayQuarterFinalMatchesPanel qfmp;
    private PlaySemiFinalMatchesPanel sfmp;
    private PlayFinalMatchPanel fmp;
    private SideMenuPanel smp;
    private ArrayList<JPanel> panelsList = new ArrayList<JPanel>(Arrays.asList(om, ctp));
    
    public static final int OPENING_MATCHES_PANEL = 1;
    public static final int QF_MATCHES_PANEL = 2;
    public static final int SF_MATCHES_PANEL = 3;
    public static final int FINAL_MATCH_PANEL = 4;
    private static final int currentTournamentStage = 0;

    private static final int WIDTH = 1500;
    private static final int HEIGHT = 723;
    private static final int PANEL_IMAGE_WIDTH = 551;
    private static final int PANEL_IMAGE_HEIGHT = 685;
    private static final int INTERVAL = 10;
    
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

        setVisible(true);
        addTimer();
        centreOnScreen();
    }

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

	// EFFECTS: initializes a timer that updates game each
	//          few milliseconds
    private void addTimer() {
		Timer t = new Timer(INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				for (JPanel p : panelsList) {
                    if (p != null) {
                        p.repaint();
                    }
                }
			}
		});
		
		t.start();
    }

	// MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // EFFECTS: initializes and sets all the panels required to play the game
    public void initializePanels() {
        // Create Tournament Panel
        ctp = new CreateTournamentPanel(this);
        ctp.setBounds(PANEL_IMAGE_WIDTH, 0, 945, 700);
        add(ctp);

        // Opening Match Panel

        // Quarter-Final Match Panel

        // Semi-Final Match Panel

        // Final Match panel
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
    // EFFECTS: starts the creation of new tournament
    public void startTournament() {
        ctp.setVisible(false);
        smp = new SideMenuPanel(this);
        add(smp, BorderLayout.EAST);
    }

    // EFFECTS: loads existing tournament for tournament.json file
    public void loadTournament() {
       // stub
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where tournament will be created
    public void createTournament() {
        om.setVisible(false);
        initializePanels();
        ctp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: simulates tournament in case of only 1 match
    private void playCondition1() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: simulates tournament in case of only 2 matches
    private void playCondition2() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: simulates tournament in case of 3-4 matches
    private void playCondition3() {
        // stub
    }

     // MODIFIES: this
    // EFFECTS: simulates tournament in case of 5 or more matches
    private void playCondition4() {
        // stub
    }

    // EFFECTS: navigates to screen where opening matches will be simulated
    private void playOpeningMatches() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where quarter-final matches will be simulated
    private void playQFMatches() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where semi-final matches will be simulated
    private void playSFMatches() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: navigates to screen where final match will be simulated
    private void playFinalMatch() {
        // stub
    }

    // REQUIRES: panel must be in list of panels in this application
    // MODIFIES: this
    // EFFECTS: makes given panel visible, while making all others invisible
    private void showPanel(JPanel panel) {
        for (JPanel p : panelsList) {
            if (p.equals(panel)) {
                p.setVisible(true);
            } else {
                p.setVisible(false);
            }
        }
    }
}
