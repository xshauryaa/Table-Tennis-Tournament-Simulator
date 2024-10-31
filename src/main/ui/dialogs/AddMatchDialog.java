package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Represents the modal dialog box that takes input
 * from the user to create match
 */ 
public class AddMatchDialog extends JDialog {
    private JTextField player1Name;
    private JTextField player2Name;
    private JTextField ovr1;
    private JTextField ovr2;
    private JButton submitBtn;
    private String p1Name;
    private String p2Name;
    private int p1Ovr;
    private int p2Ovr;
    
    // EFFECTS: creates a dialog that asks user for match details
    //          to create a match
    public AddMatchDialog(JFrame owner) {
        super(owner, "Add Match Details", true);
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
        
        submitBtn = new JButton("Submit");
        submitBtn.setSize(57, 24);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1Name = player1Name.getText();
                p1Ovr = Integer.parseInt(ovr1.getText());
                p2Name = player2Name.getText();
                p2Ovr = Integer.parseInt(ovr2.getText());

                dispose();
            }
        });

        buttonPanel.add(submitBtn);
        main.add(player1Panel);
        main.add(player2Panel);
        add(main, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // REQUIRES: playerNum == 1 || playerNum == 2
    // EFFECTS: creates a panel for each player and returns it
    private JPanel createPlayerPanel(String title, int playerNum) {
        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setLayout(new GridLayout(5, 1, 6, 6));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerPanel.add(titleLabel);
        JTextField nameField;
        JTextField ovrField;
        if (playerNum == 1) {
            nameField = player1Name;
            ovrField = ovr1;
        } else {
            nameField = player2Name;
            ovrField = ovr2;
        }
        nameField = new JTextField(10);
        nameField.setBackground(Color.DARK_GRAY);
        nameField.setForeground(Color.WHITE);
        JLabel label = new JLabel("Name");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        playerPanel.add(label);
        playerPanel.add(nameField);

        ovrField = new JTextField(10);
        ovrField.setBackground(Color.DARK_GRAY);
        ovrField.setForeground(Color.WHITE);
        label = new JLabel("OVR (0-100)");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        playerPanel.add(label);
        playerPanel.add(ovrField);

        return playerPanel;
    }

    public String getP1Name() {
        return p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public int getP1Ovr() {
        return p1Ovr;
    }

    public int getP2Ovr() {
        return p2Ovr;
    }
}
