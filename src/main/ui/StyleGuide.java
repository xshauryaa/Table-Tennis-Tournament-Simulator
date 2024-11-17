package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Match;

public class StyleGuide {
    public static final int SPECIAL_PANEL_WIDTH = 945;
    public static final int PANEL_WIDTH = 700;
    public static final int PANEL_HEIGHT = 700;
    public static final int SIDE_MENU_WIDTH = 245;

    public static final Font BOLD_FONT_100 = new Font("Inter", Font.BOLD, 100);
    public static final Font BOLD_FONT_75 = new Font("Inter", Font.BOLD, 75);
    public static final Font BOLD_FONT_60 = new Font("Inter", Font.BOLD, 60);
    public static final Font BOLD_FONT_30 = new Font("Inter", Font.BOLD, 30);
    public static final Font BOLD_FONT_18 = new Font("Inter", Font.BOLD, 18);
    public static final Font PLAIN_FONT_12 = new Font("Inter", Font.PLAIN, 12);

    public static final Dimension MATCH_CARD_DIMENSION = new Dimension(150, 75);

    public static JPanel drawMatchCard(Match m) {
        JPanel matchCard = new JPanel(new GridLayout(2, 1, 12, 12));
        matchCard.setSize(MATCH_CARD_DIMENSION);
        matchCard.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        if (!m.isPlayed()) { matchCard.setBackground(Color.BLACK); }
        else { matchCard.setBackground(Color.GREEN); }
        matchIdPanel(m, matchCard);
        playersPanel(m, matchCard);
        return matchCard;
    }

    private static void playersPanel(Match m, JPanel matchCard) {
        JLabel mainLabel = new JLabel(m.getPlayer1().getName() + " vs. " + m.getPlayer2().getName());
        mainLabel.setSize(new Dimension(40, 40));
        mainLabel.setForeground(Color.WHITE);
        mainLabel.setFont(StyleGuide.PLAIN_FONT_12);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        mainLabel.setVerticalAlignment(JLabel.CENTER);
        matchCard.add(mainLabel, BorderLayout.CENTER);
    }

    private static void matchIdPanel(Match m, JPanel matchCard) {
        JLabel matchID = new JLabel("Match " + m.getId());
        matchID.setForeground(Color.WHITE);
        matchID.setFont(StyleGuide.PLAIN_FONT_12);
        matchID.setHorizontalAlignment(JLabel.CENTER);
        matchID.setVerticalAlignment(JLabel.CENTER);
        matchCard.add(matchID, BorderLayout.NORTH);
    }
}
