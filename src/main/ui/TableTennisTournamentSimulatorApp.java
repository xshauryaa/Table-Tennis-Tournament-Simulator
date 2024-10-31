package ui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import ui.dialogs.AddMatchDialog;

// Referenced by B02-SpaceInvadersBase
// https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// Represents the main window of the GUI of the app
public class TableTennisTournamentSimulatorApp extends JFrame {
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

        setVisible(true);
        centreOnScreen();

        AddMatchDialog a = new AddMatchDialog(this);
        a.setVisible(true);
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
		// stub
    }

	// MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
		Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
}
