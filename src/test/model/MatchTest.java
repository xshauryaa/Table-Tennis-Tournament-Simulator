package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchTest {
    Match testMatch1;
    Player p1;
    Player p2;
    Match testMatch2;
    Player p3;
    Player p4;

    @BeforeEach
    void runBefore() {
        p1 = new Player("Luigi", 93);
        p2 = new Player("Rohan", 88);
        testMatch1 = new Match("123", p1, p2);
        p3 = new Player("Alex", 90);
        p4 = new Player("Marvin", 86);
        testMatch2 = new Match("456", p3, p4);
    }

    @Test
    void testConstructor() {
        assertEquals("123", testMatch1.getId());
        assertEquals(p1, testMatch1.getPlayer1());
        assertEquals(p2, testMatch1.getPlayer2());
        assertFalse(testMatch1.isPlayed());
        assertEquals(0, testMatch1.getSetScore(1).get(p1.getName()));
        assertEquals(0, testMatch1.getSetScore(1).get(p2.getName()));
        assertEquals(0, testMatch1.getSetScore(2).get(p1.getName()));
        assertEquals(0, testMatch1.getSetScore(2).get(p2.getName()));
        assertEquals(0, testMatch1.getSetScore(3).get(p1.getName()));
        assertEquals(0, testMatch1.getSetScore(3).get(p2.getName()));
    }

    @Test
    void testAlternateConstructor() {
        HashMap<String, Integer> set1 = new HashMap<String, Integer>();
        set1.put(p1.getName(), 11);
        set1.put(p2.getName(), 8);
        HashMap<String, Integer> set2 = new HashMap<String, Integer>();
        set2.put(p1.getName(), 6);
        set2.put(p2.getName(), 11);
        HashMap<String, Integer> set3 = new HashMap<String, Integer>();
        set3.put(p1.getName(), 10);
        set3.put(p2.getName(), 11);
        Match testMatchAlt = new Match ("x", p1, p2, true, set1, set2, set3, p2);
        assertEquals("x", testMatchAlt.getId());
        assertEquals(p1, testMatchAlt.getPlayer1());
        assertEquals(p2, testMatchAlt.getPlayer2());
        assertTrue(testMatchAlt.isPlayed());
        assertEquals(11, testMatchAlt.getSetScore(1).get(p1.getName()));
        assertEquals(8, testMatchAlt.getSetScore(1).get(p2.getName()));
        assertEquals(6, testMatchAlt.getSetScore(2).get(p1.getName()));
        assertEquals(11, testMatchAlt.getSetScore(2).get(p2.getName()));
        assertEquals(10, testMatchAlt.getSetScore(3).get(p1.getName()));
        assertEquals(11, testMatchAlt.getSetScore(3).get(p2.getName()));
        assertEquals(p2, testMatchAlt.getWinner());
    }

    @Test
    void testSetId() {
        testMatch1.setId("204");
        assertEquals("204", testMatch1.getId());
    }

    @Test
    void testToString() {
        assertEquals("123: Luigi vs. Rohan", testMatch1.toString());
        assertEquals("456: Alex vs. Marvin", testMatch2.toString());
    }

    @Test
    void testPlayPoint() {
        int[] set = new int[2];
        testMatch1.playPoint(set);
        assertTrue(set[0] == 1 || set[1] == 1);
        assertFalse(set[0] == 1 && set[1] == 1);
    }

    @Test
    void testPlayOneSet() {
        assertEquals(0, p1.getPointsWon());
        assertEquals(0, p1.getPointsConceded());
        assertEquals(0, p2.getPointsWon());
        assertEquals(0, p2.getPointsConceded());

        testMatch1.playSet(1);

        // Testing whether points won by each player are in the range of 0 - 11.
        assertTrue(0 <= testMatch1.getSetScore(1).get(p1.getName()) && testMatch1.getSetScore(1).get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(1).get(p2.getName()) && testMatch1.getSetScore(1).get(p2.getName()) <= 11);

        // Testing whether the points have been added in Won & Conceeded tallies for each player.
        assertEquals(p1.getPointsWon(), testMatch1.getSetScore(1).get(p1.getName()));
        assertEquals(p1.getPointsConceded(), testMatch1.getSetScore(1).get(p2.getName()));
        assertEquals(p2.getPointsWon(), testMatch1.getSetScore(1).get(p2.getName()));
        assertEquals(p2.getPointsConceded(), testMatch1.getSetScore(1).get(p1.getName()));
    }

    @Test
    void testPlayMultipleSets() {
        assertEquals(0, p1.getPointsWon());
        assertEquals(0, p1.getPointsConceded());
        assertEquals(0, p2.getPointsWon());
        assertEquals(0, p2.getPointsConceded());

        testMatch1.playSet(1);
        testMatch1.playSet(2);

        // Testing whether points won by each player are in the range of 0 - 11.
        assertTrue(0 <= testMatch1.getSetScore(1).get(p1.getName()) && testMatch1.getSetScore(1).get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(1).get(p2.getName()) && testMatch1.getSetScore(1).get(p2.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(2).get(p1.getName()) && testMatch1.getSetScore(2).get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(2).get(p2.getName()) && testMatch1.getSetScore(2).get(p2.getName()) <= 11);

         // Testing whether the points have been added in Won & Conceeded tallies for each player.
        assertEquals(p1.getPointsWon(), 
                     testMatch1.getSetScore(1).get(p1.getName()) + testMatch1.getSetScore(2).get(p1.getName()));
        assertEquals(p1.getPointsConceded(), 
                     testMatch1.getSetScore(1).get(p2.getName()) + testMatch1.getSetScore(2).get(p2.getName()));
        assertEquals(p2.getPointsWon(), 
                     testMatch1.getSetScore(1).get(p2.getName()) + testMatch1.getSetScore(2).get(p2.getName()));
        assertEquals(p2.getPointsConceded(), 
                     testMatch1.getSetScore(1).get(p1.getName()) + testMatch1.getSetScore(2).get(p1.getName()));
    }

    @Test
    void testPlayOneMatch() {
        testMatch1.playMatch();
        assertTrue(p1.equals(testMatch1.getWinner()) || p2.equals(testMatch1.getWinner()));

        // Testing whether all 3 sets are played or not, and all points won are in range 0-11.
        assertTrue(0 <= testMatch1.getSetScore(1).get(p1.getName()) && testMatch1.getSetScore(1).get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(1).get(p2.getName()) && testMatch1.getSetScore(1).get(p2.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(2).get(p1.getName()) && testMatch1.getSetScore(2).get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(2).get(p2.getName()) && testMatch1.getSetScore(2).get(p2.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(3).get(p1.getName()) && testMatch1.getSetScore(3).get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSetScore(3).get(p2.getName()) && testMatch1.getSetScore(3).get(p2.getName()) <= 11);
    }

    @Test
    void testPlayMultipleMatches() {
        for (int i=0; i<50; i++) {
            testPlayOneMatch();
        }
    }
}
