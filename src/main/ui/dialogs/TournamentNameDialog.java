package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Tournament;
import ui.TableTennisTournamentSimulatorApp;

/**
 * Represents the modal dialog box that takes input
 * from the user to set tournament with given name
 */ 
public class TournamentNameDialog extends JDialog {

    // EFFECTS: creates a dialog that asks user for name of tournament
    public TournamentNameDialog(TableTennisTournamentSimulatorApp owner) {
        super(owner, "Enter Tournament Name", false);
        setSize(468, 228);
        setLayout(new BorderLayout());
        JPanel main = new JPanel();
        main.setBackground(Color.BLACK);
        main.setLayout(new GridLayout(2, 1, 24, 24));
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

    private JButton getEnterButton(TableTennisTournamentSimulatorApp owner, JTextField nameField) {
        JButton enterBtn = new JButton("Enter");
        enterBtn.setBackground(Color.GRAY);
        enterBtn.setSize(56, 24);
        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tournament t = new Tournament(nameField.getText());
                owner.setTournament(t);
                nameField.setEditable(false);
                enterBtn.setEnabled(false);
                dialogClose();
                owner.startTournament();
            }
        });
        return enterBtn;
    }

    // MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
    
    // EFFECTS: closes the dialog
    private void dialogClose() {
        setVisible(false);
    }
}
