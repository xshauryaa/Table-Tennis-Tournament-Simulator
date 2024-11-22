package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Match;
import model.Player;
import model.Tournament;
import ui.TableTennisTournamentSimulatorApp;

/**
 * Represents the dialog box that takes name input
 * from the user to show statistics of required player
 */ 
public class AskPlayerNameDialog extends JDialog {
    private Tournament tournament;
    
    // EFFECTS: creates a dialog that asks user for name of player
    public AskPlayerNameDialog(TableTennisTournamentSimulatorApp owner) {
        super(owner, "Enter Player Name", false);
        tournament = owner.getTournament();
        setSize(468, 228);
        setLayout(new BorderLayout());
        JPanel main = new JPanel();
        main.setBackground(Color.BLACK);
        main.setLayout(new GridLayout(2, 1, 24, 24));
        main.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        JTextField nameField = new JTextField(15);
        nameField.setBackground(Color.GRAY);
        nameField.setForeground(Color.WHITE);
        nameField.setBounds(10, 20, 448, 100);
        JButton enterBtn = getEnterButton(owner, nameField);
        enterBtn.setBounds(206, 170, 56, 24);
        main.add(nameField);
        main.add(enterBtn);
        add(main);
        centreOnScreen();
    }

    // EFFECTS: returns the "Enter" button that accepts the user input
    private JButton getEnterButton(TableTennisTournamentSimulatorApp owner, JTextField nameField) {
        JButton enterBtn = new JButton("Enter");
        enterBtn.setBackground(Color.GRAY);
        enterBtn.setSize(56, 24);
        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player p = playerLookup(nameField.getText());
                PlayerStatisticsDialog psd = new PlayerStatisticsDialog(owner, p);
                dialogClose();
                psd.setVisible(true);
            }
        });
        return enterBtn;
    }

    // EFFECTS: looks for player with given name in the tournament and returns it
    private Player playerLookup(String name) {
        for (Player player : tournament.getListOfPlayers()) {
            if (player.getName().equals(name)) {
                Player p = player;
                return p;
            }
        }
        return null;
    }

    // MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
    
    // MODIFIES: this
    // EFFECTS: closes the dialog
    private void dialogClose() {
        setVisible(false);
    }
}
