package persistence;

import model.Player;
import model.Match;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Referenced by JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkMatch(String id, HashMap<String, Integer> set1, HashMap<String, Integer> set2, HashMap<String, Integer> set3, Match match) {
        assertEquals(id, match.getId());
        // assertEquals(player1, match.getPlayer1());
        // assertEquals(player2, match.getPlayer2());
        assertEquals(set1, match.getSetScore(1));
        assertEquals(set2, match.getSetScore(2));
        assertEquals(set3, match.getSetScore(3));
        // assertEquals(winner, match.getWinner());
    }

    protected void checkPlayer(String name, int overallAbility, int matchesWon, int matchesLost, int pointsWon, int pointsConceded, int pointsDifference, boolean eliminated, ArrayList<Match> history, Player p) {
        assertEquals(name, p.getName());
        assertEquals(overallAbility, p.getOverallAbility());
        assertEquals(matchesWon, p.getMatchesWon());
        assertEquals(matchesLost, p.getMatchesLost());
        assertEquals(pointsWon, p.getPointsWon());
        assertEquals(pointsConceded, p.getPointsConceded());
        assertEquals(pointsDifference, p.getPointsDifference());
        assertEquals(eliminated, p.isEliminated());
        assertEquals(history, p.getMatchHistory());
    }
}