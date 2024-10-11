package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class RankingTableTest {
    private RankingTable testRT;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @BeforeEach
    void runBefore() {
        p1 = new Player("Luigi", 93);
        p2 = new Player("Rohan", 88);
        p3 = new Player("Alex", 90);
        p4 = new Player("Marvin", 86);
        ArrayList<Player> list = new ArrayList<Player>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        testRT = new RankingTable(list);
    }

    @Test
    void testConstructor() {
        assertEquals(4, testRT.getRankings().size());
        assertEquals(1, testRT.getPlayerRanking("Alex"));
        assertEquals(2, testRT.getPlayerRanking("Luigi"));
        assertEquals(3, testRT.getPlayerRanking("Marvin"));
        assertEquals(4, testRT.getPlayerRanking("Rohan"));
    }

    @Test
    // First criteria for ranking: Higher number of wins
    void testUpdateBasedOnWins() {
        addWinsAndPoints(p3, 4, 0, 120, 100);
        addWinsAndPoints(p2, 3, 1, 97, 91);
        addWinsAndPoints(p4, 2, 2, 122, 122);
        addWinsAndPoints(p1, 1, 3, 104, 112);
        testRT.updateRankings();
        assertEquals(1, testRT.getPlayerRanking("Alex"));
        assertEquals(2, testRT.getPlayerRanking("Rohan"));
        assertEquals(3, testRT.getPlayerRanking("Marvin"));
        assertEquals(4, testRT.getPlayerRanking("Luigi"));
    }

    // Helper
    private void addWinsAndPoints(Player p, int wins, int losses, int pointsWon, int pointsLost) {
        for (int i=0; i<wins; i++) {
            p.winMatch(1);
        }
        for (int i=0; i<losses; i++) {
            p.loseMatch(1);
        }
        p.addPoints(pointsWon);
        p.concedePoints(pointsLost);
    }

    @Test
    // Second criteira: If wins are equal, then higher Points Difference
    void testUpdateBasedOnPDWhenWinsEqual() {
        addWinsAndPoints(p4, 3, 1, 115, 105);
        addWinsAndPoints(p2, 3, 1, 97, 91);
        addWinsAndPoints(p1, 2, 2, 122, 122);
        addWinsAndPoints(p3, 2, 2, 104, 112);
        testRT.updateRankings();
        assertEquals(1, testRT.getPlayerRanking("Marvin"));
        assertEquals(2, testRT.getPlayerRanking("Rohan"));
        assertEquals(3, testRT.getPlayerRanking("Luigi"));
        assertEquals(4, testRT.getPlayerRanking("Alex"));
    }

    @Test
    // Third criteria: If points difference is equal, higher points won
    void testUpdateBasedOnPWWhenPDEqual() {
        addWinsAndPoints(p2, 3, 1, 115, 109);
        addWinsAndPoints(p1, 3, 1, 97, 91);
        addWinsAndPoints(p3, 2, 2, 122, 122);
        addWinsAndPoints(p4, 2, 2, 104, 104);
        testRT.updateRankings();
        assertEquals(1, testRT.getPlayerRanking("Rohan"));
        assertEquals(2, testRT.getPlayerRanking("Luigi"));
        assertEquals(3, testRT.getPlayerRanking("Alex"));
        assertEquals(4, testRT.getPlayerRanking("Marvin"));
    }

    @Test
    void testGetPlayerAtRank() {
        assertEquals("Alex", testRT.getPlayerAtRank(1));
        assertEquals("Rohan", testRT.getPlayerAtRank(4));
    }

    @Test
    void testGetTopPlayers() {
        ArrayList<Player> temp = testRT.getTopPlayers(3);
        assertEquals(3, temp.size());
        assertTrue(temp.contains(p1));
        assertTrue(temp.contains(p3));
        assertTrue(temp.contains(p4));
    }

}
