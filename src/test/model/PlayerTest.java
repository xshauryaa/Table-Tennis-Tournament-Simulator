package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player testPlayer;
    
    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Luigi", 29, 93, "Italy");
    }

    @Test
    void testConstructor() {
        assertEquals("Luigi", testPlayer.getName());
        assertEquals(29, testPlayer.getAge());
        assertEquals(93, testPlayer.getOverallAbility());
        assertEquals("Italy", testPlayer.getCountry());
        assertEquals(0, testPlayer.getRankingPoints());
        assertEquals(0, testPlayer.getPointsWon());
        assertEquals(0, testPlayer.getPointsConceded());
        assertEquals(0, testPlayer.getPointsDifference());
        assertEquals(0, testPlayer.getMatchesWon());
        assertEquals(0, testPlayer.getMatchesLost());
        assertEquals(new ArrayList<Match>(), testPlayer.getMatchHistory());
        assertFalse(testPlayer.isEliminated());
    }

    @Test
    void testWinOneMatch() {
        testPlayer.winMatch(2);
        assertEquals(1, testPlayer.getMatchesWon());
        assertEquals(2, testPlayer.getRankingPoints());
        assertEquals(95, testPlayer.getOverallAbility());
    }

    @Test
    void testWinMultipleMatches() {
        testPlayer.winMatch(2);
        assertEquals(1, testPlayer.getMatchesWon());
        assertEquals(2, testPlayer.getRankingPoints());
        assertEquals(95, testPlayer.getOverallAbility());
        testPlayer.winMatch(1);
        assertEquals(2, testPlayer.getMatchesWon());
        assertEquals(4, testPlayer.getRankingPoints());
        assertEquals(96, testPlayer.getOverallAbility());
    }

    @Test
    void testLoseOneMatch() {
        testPlayer.loseMatch(3);
        assertEquals(1, testPlayer.getMatchesLost());
        assertEquals(90, testPlayer.getOverallAbility());
    }

    @Test
    void testLoseMultipleMatches() {
        testPlayer.loseMatch(3);
        assertEquals(1, testPlayer.getMatchesLost());
        assertEquals(90, testPlayer.getOverallAbility());
        testPlayer.loseMatch(2);
        assertEquals(2, testPlayer.getMatchesLost());
        assertEquals(88, testPlayer.getOverallAbility());
    }

    @Test
    void testAddPointsOnce() {
        testPlayer.addPoints(10);
        assertEquals(10, testPlayer.getPointsWon());
    }

    @Test
    void testAddPointsMultiple() {
        testPlayer.addPoints(10);
        assertEquals(10, testPlayer.getPointsWon());
        testPlayer.addPoints(20);
        assertEquals(30, testPlayer.getPointsWon());
    }


    @Test
    void testLosePointsOnce() {
        testPlayer.concedePoints(10);
        assertEquals(10, testPlayer.getPointsConceded());
    }

    @Test
    void testLosePointsMultiple() {
        testPlayer.concedePoints(10);
        assertEquals(10, testPlayer.getPointsConceded());
        testPlayer.concedePoints(20);
        assertEquals(30, testPlayer.getPointsConceded());
    }

    @Test
    void testEliminate() {
        testPlayer.eliminate();
        assertTrue(testPlayer.isEliminated());
    }
}