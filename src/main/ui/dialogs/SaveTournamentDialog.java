package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;

/**
 * Represents the dialog box that takes input
 * from the user to set tournament with given name
 */ 
public class SaveTournamentDialog extends JDialog {

    // EFFECTS: creates a dialog that asks user for name of tournament
    public SaveTournamentDialog(TableTennisTournamentSimulatorApp owner) {
        super(owner, "Save Tournament", false);
        setSize(468, 228);
        setLayout(new BorderLayout());
        JPanel main = new JPanel();
        main.setBackground(Color.BLACK);
        main.setLayout(new GridLayout(2, 1, 24, 24));
        main.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        JLabel save = new JLabel("Save current state of tournament?");
        save.setForeground(Color.WHITE);
        save.setFont(StyleGuide.PLAIN_FONT_16);
        main.add(save);
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        buttonsPanel.setBackground(Color.BLACK);
        JButton yesBtn = getYesButton(owner);
        buttonsPanel.add(yesBtn);
        JButton noBtn = getNoButton(owner);
        buttonsPanel.add(noBtn);
        main.add(buttonsPanel);
        add(main);
        centreOnScreen();
    }

    // EFFECTS: returns the "Yes" button that accepts the user input
    private JButton getYesButton(TableTennisTournamentSimulatorApp owner) {
        JButton yesBtn = new JButton("Yes");
        yesBtn.setBackground(Color.GRAY);
        yesBtn.setSize(56, 24);
        yesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.saveTournament();
                owner.quit();
                dialogClose();
            }
        });
        return yesBtn;
    }

    // EFFECTS: returns the "Yes" button that accepts the user input
    private JButton getNoButton(TableTennisTournamentSimulatorApp owner) {
        JButton noBtn = new JButton("No");
        noBtn.setBackground(Color.GRAY);
        noBtn.setSize(56, 24);
        noBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.quit();
                dialogClose();
            }
        });
        return noBtn;
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

