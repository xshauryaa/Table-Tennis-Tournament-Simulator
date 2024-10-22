package persistence;

import model.Player;
import model.Match;
import model.RankingTable;
import model.Tournament;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Tournament t = new Tournament("TableTennisWorldChampionship");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTournament() {
        try {
            Tournament t = new Tournament("TableTennisWorldChampionship");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTournament.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            t = reader.read();
            assertEquals("TableTennisWorldChampionship", t.getName());
            assertEquals(0, t.getOpeningRoundMatches().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterTournamentUnplayedMatches() {
        try {
            Tournament t = new Tournament("TableTennisWorldChampionship");
            Player p1 = new Player("A", 92);
            Player p2 = new Player("B", 91);
            Player p3 = new Player("C", 92);
            Player p4 = new Player("D", 91);
            t.addMatch(new Match(p1, p2));
            t.addMatch(new Match(p3, p4));
            JsonWriter writer = new JsonWriter("./data/testWriterTournamentUnplayedMatches.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTournamentUnplayedMatches.json");
            t = reader.read();
            assertEquals("TableTennisWorldChampionship", t.getName());
            List<Match> matches = t.getOpeningRoundMatches();
            assertEquals(2, matches.size());
            HashMap<String, Integer> emptySetScores1 = new HashMap<String, Integer>();
            emptySetScores1.put(p1.getName(), 0);
            emptySetScores1.put(p2.getName(), 0);
            HashMap<String, Integer> emptySetScores2 = new HashMap<String, Integer>();
            emptySetScores2.put(p3.getName(), 0);
            emptySetScores2.put(p4.getName(), 0);
            checkMatch(p1, p2, null, emptySetScores1, emptySetScores1, emptySetScores1, matches.get(0));
            checkMatch(p3, p4, null, emptySetScores2, emptySetScores2, emptySetScores2, matches.get(1));
            Player writtenP1 = matches.get(0).getPlayer1();
            Player writtenP2 = matches.get(0).getPlayer2();
            Player writtenP3 = matches.get(1).getPlayer1();
            Player writtenP4 = matches.get(1).getPlayer2();
            checkPlayer("A", 92, 0, 0, 0, 0, 0, false, new ArrayList<Match>(), writtenP1);
            checkPlayer("B", 91, 0, 0, 0, 0, 0, false, new ArrayList<Match>(), writtenP2);
            checkPlayer("C", 92, 0, 0, 0, 0, 0, false, new ArrayList<Match>(), writtenP3);
            checkPlayer("D", 91, 0, 0, 0, 0, 0, false, new ArrayList<Match>(), writtenP4);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterTournamentPlayedMatches() {
        try {
            Tournament t = new Tournament("TableTennisWorldChampionship");
            Player p1 = new Player("A", 92);
            Player p2 = new Player("B", 91);
            Player p3 = new Player("C", 92);
            Player p4 = new Player("D", 91);
            Match m1 = new Match(p1, p2);
            t.addMatch(m1);
            Match m2 = new Match(p3, p4);
            t.addMatch(m2);
            t.initiateTournament();
            t.playOpeningBracket();
            t.getRankingTable().updateRankings();
            JsonWriter writer = new JsonWriter("./data/testWriterTournamentPlayedMatches.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTournamentPlayedMatches.json");
            Tournament t2 = reader.read();
            assertEquals("TableTennisWorldChampionship", t2.getName());
            List<Match> matches = t2.getOpeningRoundMatches();
            assertEquals(2, matches.size());
            checkMatch(p1, p2, m1.getWinner(), m1.getSetScore(1), m1.getSetScore(2), m1.getSetScore(3), matches.get(0));
            checkMatch(p3, p4, m2.getWinner(), m2.getSetScore(1), m2.getSetScore(2), m2.getSetScore(3), matches.get(1));
            Player writtenP1 = matches.get(0).getPlayer1();
            Player writtenP2 = matches.get(0).getPlayer2();
            Player writtenP3 = matches.get(1).getPlayer1();
            Player writtenP4 = matches.get(1).getPlayer2();
            int pw1 = m1.getSetScore(1).get("A") + m1.getSetScore(2).get("A") + m1.getSetScore(3).get("A");
            int pw2 = m1.getSetScore(1).get("B") + m1.getSetScore(2).get("B") + m1.getSetScore(3).get("B");
            int pw3 = m2.getSetScore(1).get("A") + m2.getSetScore(2).get("A") + m2.getSetScore(3).get("A");
            int pw4 = m2.getSetScore(1).get("B") + m2.getSetScore(2).get("B") + m2.getSetScore(3).get("B");
            ArrayList<Match> listWithM1 = new ArrayList<Match>();
            ArrayList<Match> listWithM2 = new ArrayList<Match>();
            listWithM1.add(m1);
            listWithM2.add(m2);
            if (m1.getWinner().equals(p1)) {
                checkPlayer("A", 92, 1, 0, pw1, pw2, pw1 - pw2, false, listWithM1, writtenP1);
                checkPlayer("B", 91, 0, 1, pw2, pw1, pw2 - pw1, false, listWithM1, writtenP2);
            } else {
                checkPlayer("A", 92, 0, 1, pw1, pw2, pw1 - pw2, false, listWithM1, writtenP1);
                checkPlayer("B", 91, 1, 0, pw2, pw1, pw2 - pw1, false, listWithM1, writtenP2);
            }
            if (m2.getWinner().equals(p3)) {
                checkPlayer("C", 92, 1, 0, pw3, pw4, pw3 - pw4, false, listWithM2, writtenP3);
                checkPlayer("D", 91, 0, 1, pw4, pw3, pw4 - pw3, false, listWithM2, writtenP4);
            } else {
                checkPlayer("C", 92, 0, 1, pw3, pw4, pw3 - pw4, false, listWithM2, writtenP3);
                checkPlayer("D", 91, 1, 0, pw4, pw3, pw4 - pw3, false, listWithM2, writtenP4);
            }
            String rank1Player = t2.getRankingTable().getPlayerAtRank(1);
            String rank2Player = t2.getRankingTable().getPlayerAtRank(2);
            String rank3Player = t2.getRankingTable().getPlayerAtRank(3);
            String rank4Player = t2.getRankingTable().getPlayerAtRank(4);
            assertEquals(rank1Player, t.getRankingTable().getPlayerAtRank(1));
            assertEquals(rank2Player, t.getRankingTable().getPlayerAtRank(2));
            assertEquals(rank3Player, t.getRankingTable().getPlayerAtRank(3));
            assertEquals(rank4Player, t.getRankingTable().getPlayerAtRank(4));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}