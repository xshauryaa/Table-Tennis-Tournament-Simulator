package persistence;

import model.Player;
import model.Match;
import model.RankingTable;
import model.Tournament;

import org.junit.jupiter.api.Test;

import java.io.IOException;
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
            checkMatch("O1", emptySetScores1, emptySetScores1, emptySetScores1, matches.get(0));
            checkMatch("O2", emptySetScores2, emptySetScores2, emptySetScores2, matches.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
