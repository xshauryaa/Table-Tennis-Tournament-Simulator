package model;

import static org.junit.jupiter.api.Assertions.*;

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
        p1 = new Player("Luigi", 29, 93, "Italy");
        p2 = new Player("Rohan", 26, 88, "India");
        testMatch1 = new Match(p1, p2);
        p3 = new Player("Alex", 31, 90, "USA");
        p4 = new Player("Marvin", 29, 86, "Canada");
        testMatch2 = new Match(p3, p4);
    }

    @Test
    void testConstructor() {
        assertEquals(p1, testMatch1.getPlayer1());
        assertEquals(p2, testMatch1.getPlayer2());
        assertEquals(0, testMatch1.getSet1Score().get(p1.getName()));
        assertEquals(0, testMatch1.getSet1Score().get(p2.getName()));
        assertEquals(0, testMatch1.getSet2Score().get(p1.getName()));
        assertEquals(0, testMatch1.getSet2Score().get(p2.getName()));
        assertEquals(0, testMatch1.getSet3Score().get(p1.getName()));
        assertEquals(0, testMatch1.getSet3Score().get(p2.getName()));
    }

    @Test
    void testPlayOneSet() {
        assertEquals(0, p1.getPointsWon());
        assertEquals(0, p1.getPointsConceeded());
        assertEquals(0, p2.getPointsWon());
        assertEquals(0, p2.getPointsConceeded());

        testMatch1.playSet(1);

        // Testing whether points won by each player are in the range of 0 - 11.
        assertTrue(0 <= testMatch1.getSet1Score().get(p1.getName()) && testMatch1.getSet1Score().get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet1Score().get(p2.getName()) && testMatch1.getSet1Score().get(p2.getName()) <= 11);

        // Testing whether the points have been added in Won & Conceeded tallies for each player.
        assertEquals(p1.getPointsWon(), testMatch1.getSet1Score().get(p1.getName()));
        assertEquals(p1.getPointsConceeded(), testMatch1.getSet1Score().get(p2.getName()));
        assertEquals(p2.getPointsWon(), testMatch1.getSet1Score().get(p2.getName()));
        assertEquals(p2.getPointsConceeded(), testMatch1.getSet1Score().get(p1.getName()));
    }

    @Test
    void testPlayMultipleSets() {
        assertEquals(0, p1.getPointsWon());
        assertEquals(0, p1.getPointsConceeded());
        assertEquals(0, p2.getPointsWon());
        assertEquals(0, p2.getPointsConceeded());

        testMatch1.playSet(1);
        testMatch1.playSet(2);

        // Testing whether points won by each player are in the range of 0 - 11.
        assertTrue(0 <= testMatch1.getSet1Score().get(p1.getName()) && testMatch1.getSet1Score().get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet1Score().get(p2.getName()) && testMatch1.getSet1Score().get(p2.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet2Score().get(p1.getName()) && testMatch1.getSet2Score().get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet2Score().get(p2.getName()) && testMatch1.getSet2Score().get(p2.getName()) <= 11);

         // Testing whether the points have been added in Won & Conceeded tallies for each player.
        assertEquals(p1.getPointsWon(), 
                     testMatch1.getSet1Score().get(p1.getName()) + testMatch1.getSet2Score().get(p1.getName()));
        assertEquals(p1.getPointsConceeded(), 
                     testMatch1.getSet1Score().get(p2.getName()) + testMatch1.getSet2Score().get(p2.getName()));
        assertEquals(p2.getPointsWon(), 
                     testMatch1.getSet1Score().get(p2.getName()) + testMatch1.getSet2Score().get(p2.getName()));
        assertEquals(p2.getPointsConceeded(), 
                     testMatch1.getSet1Score().get(p1.getName()) + testMatch1.getSet2Score().get(p1.getName()));
    }

    @Test
    void testPlayMatch() {
        testMatch1.playMatch();
        assertTrue(p1.equals(testMatch1.getWinner()) || p2.equals(testMatch1.getWinner()));

        // Testing whether all 3 sets are played or not, and all points won are in range 0-11.
        assertTrue(0 <= testMatch1.getSet1Score().get(p1.getName()) && testMatch1.getSet1Score().get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet1Score().get(p2.getName()) && testMatch1.getSet1Score().get(p2.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet2Score().get(p1.getName()) && testMatch1.getSet2Score().get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet2Score().get(p2.getName()) && testMatch1.getSet2Score().get(p2.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet3Score().get(p1.getName()) && testMatch1.getSet3Score().get(p1.getName()) <= 11);
        assertTrue(0 <= testMatch1.getSet3Score().get(p2.getName()) && testMatch1.getSet3Score().get(p2.getName()) <= 11);

        testMatch2.playMatch();
        assertTrue(p3.equals(testMatch2.getWinner()) || p4.equals(testMatch2.getWinner()));

        // Testing whether all 3 sets are played or not, and all points won are in range 0-11.
        assertTrue(0 <= testMatch2.getSet1Score().get(p3.getName()) && testMatch2.getSet1Score().get(p3.getName()) <= 11);
        assertTrue(0 <= testMatch2.getSet1Score().get(p4.getName()) && testMatch2.getSet1Score().get(p4.getName()) <= 11);
        assertTrue(0 <= testMatch2.getSet2Score().get(p3.getName()) && testMatch2.getSet2Score().get(p3.getName()) <= 11);
        assertTrue(0 <= testMatch2.getSet2Score().get(p4.getName()) && testMatch2.getSet2Score().get(p4.getName()) <= 11);
        assertTrue(0 <= testMatch2.getSet3Score().get(p3.getName()) && testMatch2.getSet3Score().get(p3.getName()) <= 11);
        assertTrue(0 <= testMatch2.getSet3Score().get(p4.getName()) && testMatch2.getSet3Score().get(p4.getName()) <= 11);
    }
}
