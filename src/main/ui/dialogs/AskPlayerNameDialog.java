package ui.dialogs;

import javax.swing.*;

import model.Player;
import ui.TableTennisTournamentSimulatorApp;

/**
 * Represents the dialog box that takes name input
 * from the user to show statistics of required player
 */ 
public class AskPlayerNameDialog extends JDialog {
    
    // EFFECTS: creates a dialog that asks user for name of player
    public AskPlayerNameDialog(TableTennisTournamentSimulatorApp owner) {
        // stub
    }

    // EFFECTS: returns the "Enter" button that accepts the user input
    private JButton getEnterButton(TableTennisTournamentSimulatorApp owner, JTextField nameField) {
        return null; // stub
    }

    // EFFECTS: looks for player with given name in the tournament and returns it
    private Player playerLookup(String name) {
        return null; // stub
    }

    // MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        // stub
    }
    
    // MODIFIES: this
    // EFFECTS: closes the dialog
    private void dialogClose() {
        // stub
    }
}
