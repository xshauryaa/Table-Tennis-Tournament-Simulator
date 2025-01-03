package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TournamentTest {
    Tournament testTournament;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    Player p5;
    Player p6;
    Player p7;
    Player p8;
    Player p9;
    Player p10;
    Player p11;
    Player p12;
    Match m1;
    Match m2;
    Match m3;
    Match m4;
    Match m5;
    Match m6;

    @BeforeEach
    void runBefore() {
        testTournament = new Tournament("World Championship");
        p1 = new Player("A", 92);
        p2 = new Player("B", 91);
        p3 = new Player("C", 92);
        p4 = new Player("D", 91);
        p5 = new Player("E", 92);
        p6 = new Player("F", 91);
        p7 = new Player("G", 92);
        p8 = new Player("H", 91);
        p9 = new Player("I", 91);
        p10 = new Player("J", 91);
        p11 = new Player("K", 92);
        p12 = new Player("L", 91);
        m1 = new Match("1", p1, p2);
        m2 = new Match("2", p3, p4);
        m3 = new Match("3", p5, p6);
        m4 = new Match("4", p7, p8);
        m5 = new Match("5", p9, p10);
        m6 = new Match("6", p11, p12);
    }

    @Test
    void testConstructor() {
        assertEquals("World Championship", testTournament.getName());
        assertEquals(0, testTournament.getListOfPlayers().size());
        assertEquals(null, testTournament.getRankingTable());
        assertEquals(0, testTournament.getOpeningRoundMatches().size());
        assertEquals(0, testTournament.getQuarterFinalMatches().size());
        assertEquals(0, testTournament.getSemiFinalMatches().size());
        assertEquals(null, testTournament.getFinalMatch());
        assertEquals(null, testTournament.getChampion());
    }
    
    @Test
    void testAddMatch() {
        testTournament.addMatch(m1);
        assertEquals(1, testTournament.getOpeningRoundMatches().size());
        assertEquals(m1, testTournament.getOpeningRoundMatches().get(0));
        assertEquals(2, testTournament.getListOfPlayers().size());
        assertEquals(p1, testTournament.getListOfPlayers().get(0));
        assertEquals(p2, testTournament.getListOfPlayers().get(1));
        assertEquals(1, p1.getMatchHistory().size());
        assertEquals(1, p2.getMatchHistory().size());
        assertEquals(m1.toString(), p1.getMatchHistory().get(0));
        assertEquals(m1.toString(), p2.getMatchHistory().get(0));
    }

    @Test
    void AddMultiplesMatches() {
        testTournament.addMatch(m1);
        testTournament.addMatch(m2);
        assertEquals(2, testTournament.getOpeningRoundMatches().size());
        assertEquals(m1, testTournament.getOpeningRoundMatches().get(0));
        assertEquals("O1", m1.getId());
        assertEquals(m2, testTournament.getOpeningRoundMatches().get(1));
        assertEquals("O2", m2.getId());
        assertEquals(4, testTournament.getListOfPlayers().size());
        assertEquals(p1, testTournament.getListOfPlayers().get(0));
        assertEquals(p2, testTournament.getListOfPlayers().get(1));
        assertEquals(p3, testTournament.getListOfPlayers().get(2));
        assertEquals(p4, testTournament.getListOfPlayers().get(3));
        assertEquals(1, p1.getMatchHistory().size());
        assertEquals(1, p2.getMatchHistory().size());
        assertEquals(1, p3.getMatchHistory().size());
        assertEquals(1, p4.getMatchHistory().size());
        assertEquals(m1.toString(), p1.getMatchHistory().get(0));
        assertEquals(m1.toString(), p2.getMatchHistory().get(0));
        assertEquals(m2.toString(), p3.getMatchHistory().get(0));
        assertEquals(m2.toString(), p4.getMatchHistory().get(0));
    }

    @Test
    void testFilterMatches() {
        testTournament.addMatch(m1);
        Match nm1 = new Match("X", new Player("S", 90), new Player("K", 80));
        testTournament.addMatch(nm1);
        Match nm2 = new Match("X", new Player("S", 80), new Player("K", 70));
        testTournament.addMatch(nm2);
        ArrayList<Match> matchesOver90 = testTournament.filterMatchesOnMinOvr(90);
        ArrayList<Match> matchesOver80 = testTournament.filterMatchesOnMinOvr(80);
        ArrayList<Match> matchesOver70 = testTournament.filterMatchesOnMinOvr(70);
        assertEquals(1, matchesOver90.size());
        assertTrue(matchesOver90.contains(m1));
        assertEquals(2, matchesOver80.size());
        assertTrue(matchesOver80.contains(m1));
        assertTrue(matchesOver80.contains(nm1));
        assertEquals(3, matchesOver70.size());
        assertTrue(matchesOver70.contains(m1));
        assertTrue(matchesOver70.contains(nm1));
        assertTrue(matchesOver70.contains(nm2));
    }

    @Test
    void testInitiateTournamentCondition1() {
        testTournament.addMatch(m1);
        testTournament.initiateTournament();
        assertEquals(1, testTournament.getDesignType());
        assertEquals(1, testTournament.getRankingTable().getPlayerRanking("A"));
        assertEquals(2, testTournament.getRankingTable().getPlayerRanking("B"));
    }

    @Test
    void testInitiateTournamentCondition2() {
        testTournament.addMatch(m1);
        testTournament.addMatch(m2);
        testTournament.initiateTournament();
        assertEquals(2, testTournament.getDesignType());
        assertEquals(1, testTournament.getRankingTable().getPlayerRanking("A"));
        assertEquals(2, testTournament.getRankingTable().getPlayerRanking("B"));
        assertEquals(3, testTournament.getRankingTable().getPlayerRanking("C"));
        assertEquals(4, testTournament.getRankingTable().getPlayerRanking("D"));
    }

    @Test
    void testInitiateTournamentCondition3() {
        testTournament.addMatch(m1);
        testTournament.addMatch(m2);
        testTournament.addMatch(m3);
        testTournament.initiateTournament();
        assertEquals(3, testTournament.getDesignType());
        assertEquals(1, testTournament.getRankingTable().getPlayerRanking("A"));
        assertEquals(2, testTournament.getRankingTable().getPlayerRanking("B"));
        assertEquals(3, testTournament.getRankingTable().getPlayerRanking("C"));
        assertEquals(4, testTournament.getRankingTable().getPlayerRanking("D"));
        assertEquals(5, testTournament.getRankingTable().getPlayerRanking("E"));
        assertEquals(6, testTournament.getRankingTable().getPlayerRanking("F"));
    }

    @Test
    void testInitiateTournamentCondition4() {
        testTournament.addMatch(m1);
        testTournament.addMatch(m2);
        testTournament.addMatch(m3);
        testTournament.addMatch(m4);
        testTournament.addMatch(m5);
        testTournament.initiateTournament();
        assertEquals(4, testTournament.getDesignType());
        assertEquals(1, testTournament.getRankingTable().getPlayerRanking("A"));
        assertEquals(2, testTournament.getRankingTable().getPlayerRanking("B"));
        assertEquals(3, testTournament.getRankingTable().getPlayerRanking("C"));
        assertEquals(4, testTournament.getRankingTable().getPlayerRanking("D"));
        assertEquals(5, testTournament.getRankingTable().getPlayerRanking("E"));
        assertEquals(6, testTournament.getRankingTable().getPlayerRanking("F"));
        assertEquals(7, testTournament.getRankingTable().getPlayerRanking("G"));
        assertEquals(8, testTournament.getRankingTable().getPlayerRanking("H"));
        assertEquals(9, testTournament.getRankingTable().getPlayerRanking("I"));
        assertEquals(10, testTournament.getRankingTable().getPlayerRanking("J"));
    }

    @Test
    void testPlayOpeningBracket() {
        testTournament.addMatch(m1);
        testTournament.addMatch(m2);
        testTournament.addMatch(m3);
        testTournament.addMatch(m4);
        testTournament.addMatch(m5);
        testTournament.addMatch(m6);
        testTournament.initiateTournament();
        testTournament.playOpeningBracket();
        assertTrue(m1.getWinner() != null);
        assertTrue(m2.getWinner() != null);
        assertTrue(m3.getWinner() != null);
        assertTrue(m4.getWinner() != null);
        assertTrue(m5.getWinner() != null);
        assertTrue(m6.getWinner() != null);
    }
    @Test
    void testMakeQF() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p3);
        players.add(p4);
        players.add(p6);
        players.add(p8);
        players.add(p9);
        players.add(p10);
        players.add(p11);
        testTournament.makeQuarterFinals(players);
        assertEquals(4, testTournament.getQuarterFinalMatches().size());
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(0).getPlayer1()));
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(0).getPlayer2()));
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(1).getPlayer1()));
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(1).getPlayer2()));
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(2).getPlayer1()));
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(2).getPlayer2()));
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(3).getPlayer1()));
        assertTrue(players.contains(testTournament.getQuarterFinalMatches().get(3).getPlayer2()));
    }

    @Test
    void testMakeSF() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p6);
        players.add(p8);
        players.add(p11);
        testTournament.makeSemiFinals(players);
        assertEquals(2, testTournament.getSemiFinalMatches().size());
        assertTrue(players.contains(testTournament.getSemiFinalMatches().get(0).getPlayer1()));
        assertTrue(players.contains(testTournament.getSemiFinalMatches().get(0).getPlayer2()));
        assertTrue(players.contains(testTournament.getSemiFinalMatches().get(1).getPlayer1()));
        assertTrue(players.contains(testTournament.getSemiFinalMatches().get(1).getPlayer2()));
    }

    @Test
    void testPlayQF() {
        testTournament.addMatch(m1);
        testTournament.addMatch(m2);
        testTournament.addMatch(m3);
        testTournament.addMatch(m4);
        testTournament.addMatch(m5);
        testTournament.addMatch(m6);
        testTournament.initiateTournament();
        testTournament.playOpeningBracket();
        testTournament.getRankingTable().updateRankings();
        ArrayList<Player> players = testTournament.getRankingTable().getTopPlayers(8);
        testTournament.makeQuarterFinals(players);
        testTournament.playQuarterFinals();
        assertTrue(testTournament.getQuarterFinalMatches().get(0).getWinner() != null);
        assertTrue(testTournament.getQuarterFinalMatches().get(1).getWinner() != null);
        assertTrue(testTournament.getQuarterFinalMatches().get(2).getWinner() != null);
        assertTrue(testTournament.getQuarterFinalMatches().get(3).getWinner() != null);
        assertEquals(2, testTournament.getSemiFinalMatches().size());
        for (int i = 0; i < 4; i++) {
            Player winner = testTournament.getQuarterFinalMatches().get(i).getWinner();
            Player loser = null;
            if (testTournament.getQuarterFinalMatches().get(i).getPlayer1().equals(winner)) {
                loser = testTournament.getQuarterFinalMatches().get(i).getPlayer2();
            } else {
                loser = testTournament.getQuarterFinalMatches().get(i).getPlayer1();
            }
            assertTrue(loser.isEliminated());
        }
        for (Player p : testTournament.getListOfPlayers()) {
            if (!players.contains(p)) {
                assertTrue(p.isEliminated());
            }
        }
    }

    @Test
    void testPlaySF() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p6);
        players.add(p8);
        players.add(p11);
        testTournament.makeSemiFinals(players);
        testTournament.playSemiFinals();
        assertTrue(testTournament.getSemiFinalMatches().get(0).getWinner() != null);
        assertTrue(testTournament.getSemiFinalMatches().get(1).getWinner() != null);
        assertTrue(testTournament.getFinalMatch().getPlayer1() != null);
        assertTrue(testTournament.getFinalMatch().getPlayer2() != null);
        for (int i = 0; i < 2; i++) {
            Player winner = testTournament.getSemiFinalMatches().get(i).getWinner();
            Player loser = null;
            if (testTournament.getSemiFinalMatches().get(i).getPlayer1().equals(winner)) {
                loser = testTournament.getSemiFinalMatches().get(i).getPlayer2();
            } else {
                loser = testTournament.getSemiFinalMatches().get(i).getPlayer1();
            }
            assertTrue(loser.isEliminated());
        }
    }

    @Test
    void testPlaySFMultipleTimes() {
        for (int i = 0; i < 3; i++) {
            testPlaySF();
        }
    }

    @Test
    void testPlayFinalMatch() {
        testTournament.setFinalMatch(new Match("F", p10, p1));
        testTournament.playFinalMatch();
        assertTrue(testTournament.getChampion() != null);
        assertTrue(testTournament.getFinalMatch().getWinner() != null);
        assertEquals(testTournament.getChampion(), testTournament.getFinalMatch().getWinner());
    }

    @Test
    void testPlayFinalMatchMultipleTimes() {
        for (int i = 0; i < 5; i++) {
            testPlayFinalMatch();
        }
    }
}
