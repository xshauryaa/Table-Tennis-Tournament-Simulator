package persistence;

import model.Player;
import model.Match;
import model.Tournament;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Tournament t = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTournament() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTournament.json");
        try {
            Tournament t = reader.read();
            assertEquals("TableTennisWorldChampionship", t.getName());
            assertEquals(0, t.getOpeningRoundMatches().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTournament() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTournament.json");
        try {
            Tournament t = reader.read();
            Player p1 = new Player("A", 92);
            Player p2 = new Player("B", 91);
            Player p3 = new Player("C", 92);
            Player p4 = new Player("D", 91);
            HashMap<String, Integer> emptySetScores1 = new HashMap<String, Integer>();
            emptySetScores1.put(p1.getName(), 0);
            emptySetScores1.put(p2.getName(), 0);
            HashMap<String, Integer> emptySetScores2 = new HashMap<String, Integer>();
            emptySetScores2.put(p3.getName(), 0);
            emptySetScores2.put(p4.getName(), 0);
            assertEquals("TableTennisWorldChampionship", t.getName());
            List<Match> matches = t.getOpeningRoundMatches();
            assertEquals(2, matches.size());
            checkMatch("O1", false, emptySetScores1, emptySetScores1, emptySetScores1, matches.get(0));
            checkMatch("O2", false, emptySetScores2, emptySetScores2, emptySetScores2, matches.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderCompleteTournament() {
        JsonReader reader = new JsonReader("./data/testReaderCompleteTournament.json");
        try {
            Tournament t = reader.read();
            assertEquals("TTWC", t.getName());
            assertEquals(4, t.getDesignType());
            assertEquals("F", t.getStatus());
            checkPlayer("A", 93, 2, 1, 84, 81, true, t.getListOfPlayers().get(0));
            checkPlayer("B", 80, 0, 1, 23, 33, true, t.getListOfPlayers().get(1));
            checkPlayer("C", 92, 1, 1, 57, 55, true, t.getListOfPlayers().get(2));
            checkPlayer("D", 86, 0, 1, 27, 31, true, t.getListOfPlayers().get(3));
            checkPlayer("E", 98, 3, 1, 108, 100, true, t.getListOfPlayers().get(4));
            checkPlayer("F", 85, 0, 1, 22, 27, true, t.getListOfPlayers().get(5));
            checkPlayer("G", 87, 0, 2, 54, 51, true, t.getListOfPlayers().get(6));
            checkPlayer("H", 89, 2, 1, 79, 86, true, t.getListOfPlayers().get(7));
            checkPlayer("I", 90, 1, 1, 51, 51, true, t.getListOfPlayers().get(8));
            checkPlayer("J", 87, 0, 1, 24, 27, true, t.getListOfPlayers().get(9));
            checkPlayer("K", 82, 1, 1, 52, 60, true, t.getListOfPlayers().get(10));
            checkPlayer("L", 111, 3, 1, 123, 102, false, t.getListOfPlayers().get(11));
            ArrayList<HashMap<String, Integer>> sets = setCreator("A", "B", 11, 10, 11, 8, 11, 5);
            checkMatch("O1", true, sets, t.getOpeningRoundMatches().get(0));

            sets = setCreator("C", "D", 11, 8, 11, 8, 9, 11);
            checkMatch("O2", true, sets, t.getOpeningRoundMatches().get(1));

            sets = setCreator("E", "F", 11, 1, 11, 10, 5, 11);
            checkMatch("O3", true, sets, t.getOpeningRoundMatches().get(2));

            sets = setCreator("G", "H", 10, 11, 8, 11, 11, 4);
            checkMatch("O4", true, sets, t.getOpeningRoundMatches().get(3));

            sets = setCreator("I", "J", 5, 11, 11, 5, 11, 8);
            checkMatch("O5", true, sets, t.getOpeningRoundMatches().get(4));

            sets = setCreator("K", "L", 11, 7, 11, 9, 8, 11);
            checkMatch("O6", true, sets, t.getOpeningRoundMatches().get(5));
            
            sets = setCreator("A", "C", 11, 7, 11, 8, 6, 11);
            checkMatch("QF1", true, sets, t.getQuarterFinalMatches().get(0));
            
            sets = setCreator("E", "G", 11, 9, 11, 5, 3, 11);
            checkMatch("QF2", true, sets, t.getQuarterFinalMatches().get(1));
            
            sets = setCreator("H", "I", 5, 11, 11, 8, 11, 5);
            checkMatch("QF3", true, sets, t.getQuarterFinalMatches().get(2));
            
            sets = setCreator("K", "L", 5, 11, 8, 11, 9, 11);
            checkMatch("QF4", true, sets, t.getQuarterFinalMatches().get(3));
            
            sets = setCreator("A", "E", 3, 11, 11, 10, 9, 11);
            checkMatch("SF1", true, sets, t.getSemiFinalMatches().get(0));
            
            sets = setCreator("H", "L", 10, 11, 8, 11, 8, 11);
            checkMatch("SF2", true, sets, t.getSemiFinalMatches().get(1));
            
            sets = setCreator("E", "L", 3, 11, 11, 8, 10, 11);
            checkMatch("F", true, sets, t.getFinalMatch());

            checkPlayer("L", 111, 3, 1, 123, 102, false, t.getChampion());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private ArrayList<HashMap<String, Integer>> setCreator(String name1, String name2, int a, int b, int c, int d, int e, int f) {
        ArrayList<HashMap<String, Integer>> sets = new ArrayList<HashMap<String, Integer>>();
        HashMap<String, Integer> set1 = new HashMap<String, Integer>();
        set1.put(name1, a);
        set1.put(name2, b);
        HashMap<String, Integer> set2 = new HashMap<String, Integer>();
        set2.put(name1, c);
        set2.put(name2, d);
        HashMap<String, Integer> set3 = new HashMap<String, Integer>();
        set3.put(name1, e);
        set3.put(name2, f);
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);
        return sets;
    }
}
