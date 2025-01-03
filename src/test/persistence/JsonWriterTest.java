package persistence;

import model.Player;
import model.Match;
import model.Tournament;

import org.junit.jupiter.api.Test;

import java.io.IOException;
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

            JsonReader reader = new JsonReader("./data/testWriterEmptyTournament.json");
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
            t.addMatch(new Match("x", p1, p2));
            t.addMatch(new Match("x", p3, p4));
            JsonWriter writer = new JsonWriter("./data/testWriterTournamentUnplayedMatches.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTournamentUnplayedMatches.json");
            Tournament tChecker = reader.read();
            assertEquals("TableTennisWorldChampionship", tChecker.getName());
            List<Match> matches = tChecker.getOpeningRoundMatches();
            assertEquals(2, matches.size());
            HashMap<String, Integer> emptySetScores1 = new HashMap<String, Integer>();
            emptySetScores1.put(p1.getName(), 0);
            emptySetScores1.put(p2.getName(), 0);
            HashMap<String, Integer> emptySetScores2 = new HashMap<String, Integer>();
            emptySetScores2.put(p3.getName(), 0);
            emptySetScores2.put(p4.getName(), 0);
            checkMatch("O1", false, emptySetScores1, emptySetScores1, emptySetScores1, matches.get(0));
            checkMatch("O2", false, emptySetScores2, emptySetScores2, emptySetScores2, matches.get(1));
            Player writtenP1 = matches.get(0).getPlayer1();
            Player writtenP2 = matches.get(0).getPlayer2();
            Player writtenP3 = matches.get(1).getPlayer1();
            Player writtenP4 = matches.get(1).getPlayer2();
            checkPlayer("A", 92, 0, 0, 0, 0, false, writtenP1);
            checkPlayer("B", 91, 0, 0, 0, 0, false, writtenP2);
            checkPlayer("C", 92, 0, 0, 0, 0, false, writtenP3);
            checkPlayer("D", 91, 0, 0, 0, 0, false, writtenP4);
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
            Match m1 = new Match("x", p1, p2);
            t.addMatch(m1);
            Match m2 = new Match("x", p3, p4);
            t.addMatch(m2);
            t.initiateTournament();
            t.playOpeningBracket();
            t.getRankingTable().updateRankings();
            Player finalist1 = t.getOpeningRoundMatches().get(0).getWinner();
            Player finalist2 = t.getOpeningRoundMatches().get(1).getWinner();
            if (t.getOpeningRoundMatches().get(0).getPlayer1().equals(finalist1)) {
                t.getOpeningRoundMatches().get(0).getPlayer2().eliminate();
            } else {
                t.getOpeningRoundMatches().get(0).getPlayer1().eliminate();
            }
            if (t.getOpeningRoundMatches().get(1).getPlayer1().equals(finalist1)) {
                t.getOpeningRoundMatches().get(1).getPlayer2().eliminate();
            } else {
                t.getOpeningRoundMatches().get(1).getPlayer1().eliminate();
            }
            t.setFinalMatch(new Match("F", finalist1, finalist2));
            t.setStatus("F");
            t.playFinalMatch();
            JsonWriter writer = new JsonWriter("./data/testWriterTournamentPlayedMatches.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTournamentPlayedMatches.json");
            Tournament t2 = reader.read();
            assertEquals("TableTennisWorldChampionship", t2.getName());
            List<Match> matches = t2.getOpeningRoundMatches();
            assertEquals(2, matches.size());
            checkMatch("O1", true, m1.getSetScore(1), m1.getSetScore(2), m1.getSetScore(3), matches.get(0));
            checkMatch("O2", true, m2.getSetScore(1), m2.getSetScore(2), m2.getSetScore(3), matches.get(1));
            Player writtenP1 = t2.getListOfPlayers().get(0);
            Player writtenP2 = t2.getListOfPlayers().get(1);
            Player writtenP3 = t2.getListOfPlayers().get(2);
            Player writtenP4 = t2.getListOfPlayers().get(3);
            checkPlayer("A", p1.getOverallAbility(), p1.getMatchesWon(), p1.getMatchesLost(), p1.getPointsWon(), p1.getPointsConceded(), p1.isEliminated(), writtenP1);
            checkPlayer("B", p2.getOverallAbility(), p2.getMatchesWon(), p2.getMatchesLost(), p2.getPointsWon(), p2.getPointsConceded(), p2.isEliminated(), writtenP2);
            checkPlayer("C", p3.getOverallAbility(), p3.getMatchesWon(), p3.getMatchesLost(), p3.getPointsWon(), p3.getPointsConceded(), p3.isEliminated(), writtenP3);
            checkPlayer("D", p4.getOverallAbility(), p4.getMatchesWon(), p4.getMatchesLost(), p4.getPointsWon(), p4.getPointsConceded(), p4.isEliminated(), writtenP4);
            String rank1Player = t2.getRankingTable().getPlayerAtRank(1);
            String rank2Player = t2.getRankingTable().getPlayerAtRank(2);
            String rank3Player = t2.getRankingTable().getPlayerAtRank(3);
            String rank4Player = t2.getRankingTable().getPlayerAtRank(4);
            assertEquals(rank1Player, t.getRankingTable().getPlayerAtRank(1));
            assertEquals(rank2Player, t.getRankingTable().getPlayerAtRank(2));
            assertEquals(rank3Player, t.getRankingTable().getPlayerAtRank(3));
            assertEquals(rank4Player, t.getRankingTable().getPlayerAtRank(4));
            assertEquals("F", t2.getStatus());
            checkMatch("F", true, t.getFinalMatch().getSetScore(1), t.getFinalMatch().getSetScore(2), t.getFinalMatch().getSetScore(3), t2.getFinalMatch());
            Player champ = t.getChampion();
            checkPlayer(champ.getName(), champ.getOverallAbility(), champ.getMatchesWon(), champ.getMatchesLost(), champ.getPointsWon(), champ.getPointsConceded(), champ.isEliminated(), t2.getChampion());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}