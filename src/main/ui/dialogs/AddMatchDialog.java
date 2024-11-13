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
import ui.StyleGuide;
import ui.TableTennisTournamentSimulatorApp;
import ui.panels.CreateTournamentPanel;

/**
 * Represents the modal dialog box that takes input
 * from the user to create match
 */ 
public class AddMatchDialog extends JDialog {
    private JTextField player1Name = new JTextField(10);
    private JTextField player2Name = new JTextField(10);
    private JTextField ovr1 = new JTextField(10);
    private JTextField ovr2 = new JTextField(10);
    private JButton submitBtn;
    private String p1Name;
    private String p2Name;
    private int p1Ovr;
    private int p2Ovr;
    
    // EFFECTS: creates a dialog that asks user for match details
    //          to create a match
    public AddMatchDialog(TableTennisTournamentSimulatorApp owner, CreateTournamentPanel ctp) {
        super(owner, "Add Match Details", false);
        setSize(468, 228);
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JPanel main = new JPanel();
        main.setBackground(Color.BLACK);
        main.setLayout(new GridLayout(1, 2, 12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);

        JPanel player1Panel = createPlayerPanel("Player 1", 1);
        JPanel player2Panel = createPlayerPanel("Player 2", 2);
        
        initializeSubmitButton(owner, ctp);

        buttonPanel.add(submitBtn);
        main.add(player1Panel);
        main.add(player2Panel);
        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        centreOnScreen();
    }

    // EFFECTS: initializes submit button for the dialog window
    private void initializeSubmitButton(TableTennisTournamentSimulatorApp owner, CreateTournamentPanel ctp) {
        submitBtn = new JButton("Submit");
        submitBtn.setSize(57, 24);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1Name = player1Name.getText();
                p1Ovr = Integer.parseInt(ovr1.getText());
                p2Name = player2Name.getText();
                p2Ovr = Integer.parseInt(ovr2.getText());

                Match m = new Match("X", new Player(p1Name, p1Ovr), new Player(p2Name, p2Ovr));
                owner.getTournament().addMatch(m);

                dialogClose();
                ctp.repaint();
            }
        });
    }

    // REQUIRES: playerNum == 1 || playerNum == 2
    // EFFECTS: creates a panel for each player and returns it
    private JPanel createPlayerPanel(String title, int playerNum) {
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setLayout(new GridLayout(5, 1, 6, 6));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(StyleGuide.BOLD_FONT_18);
        playerPanel.add(titleLabel);
        if (playerNum == 1) {
            setNameField(playerPanel, player1Name);
            setOvrField(playerPanel, ovr1);
        } else {
            setNameField(playerPanel, player2Name);
            setOvrField(playerPanel, ovr2);
        }

        return playerPanel;
    }

    // EFFECTS: sets given field to a new JTextField instance and adds
    //          it to the given panel as the OVR field
    private void setOvrField(JPanel panel, JTextField ovrField) {
        ovrField.setBackground(Color.DARK_GRAY);
        ovrField.setForeground(Color.WHITE);
        JLabel label = new JLabel("OVR (0-100)");
        label.setForeground(Color.WHITE);
        label.setFont(StyleGuide.PLAIN_FONT_12);
        panel.add(label);
        panel.add(ovrField);
    }

    // EFFECTS: sets given field to a new JTextField instance and adds
    //          it to the given panel as the name field
    private void setNameField(JPanel panel, JTextField nameField) {
        nameField.setBackground(Color.DARK_GRAY);
        nameField.setForeground(Color.WHITE);
        JLabel label = new JLabel("Name");
        label.setForeground(Color.WHITE);
        label.setFont(StyleGuide.PLAIN_FONT_12);
        panel.add(label);
        panel.add(nameField);
    }

    // MODIFIES: this
	// EFFECTS: location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
		Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // EFFECTS: closes the dialog
    private void dialogClose() {
        this.setVisible(false);
    }
}
