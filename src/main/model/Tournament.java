package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents a tournament with a list of players, an opening 
 * bracket of matches, a quarter finals bracket, a semi finals 
 * bracket, a final match, and a ranking table.
 */
public class Tournament implements Writable {
    private String name;
    private ArrayList<Player> listOfPlayers;
    private RankingTable rankingTable;
    private int openingMatchId = 1;
    private ArrayList<Match> openingRoundMatches;
    private ArrayList<Match> quarterFinalMatches;
    private ArrayList<Match> semiFinalMatches;
    private Match finalMatch;
    private Player champion;

    // EFFECTS: Creates a tournament with a name, no initial 
    //          matches, empty bracket for opening round, and 
    //          empty brackets for quarter-, semi-, and
    //          final matches.
    public Tournament(String name) {
        this.name = name;
        this.listOfPlayers = new ArrayList<Player>();
        this.rankingTable = null;
        this.openingRoundMatches = new ArrayList<Match>();
        this.quarterFinalMatches = new ArrayList<Match>();
        this.semiFinalMatches = new ArrayList<Match>();
        this.finalMatch = null;
        this.champion = null;
    }

    public String getName() {
        return this.name; 
    }

    public ArrayList<Player> getListOfPlayers() {
        return this.listOfPlayers;
    }

    public RankingTable getRankingTable() {
        return this.rankingTable;
    }
    
    public ArrayList<Match> getOpeningRoundMatches() {
        return this.openingRoundMatches;
    }

    public ArrayList<Match> getQuarterFinalMatches() {
        return this.quarterFinalMatches;
    }

    public ArrayList<Match> getSemiFinalMatches() {
        return this.semiFinalMatches;
    }

    public Match getFinalMatch() {
        return this.finalMatch;
    }

    public Player getChampion() {
        return this.champion;
    }

    public void setFinalMatch(Match match) {
        this.finalMatch = match;
    }

    // REQUIRES: match must not already be in tournament
    // MODIFIES: this
    // EFFECTS: Adds given match to the opening bracket, adds
    //          the players of the match to the list of players,
    //          and adds this match to each player's match history
    public void addMatch(Match match) {
        match.setId("O" + openingMatchId);
        this.openingRoundMatches.add(match);
        this.listOfPlayers.add(match.getPlayer1());
        this.listOfPlayers.add(match.getPlayer2());
        match.getPlayer1().addMatchToHistory(match);
        match.getPlayer2().addMatchToHistory(match);
        this.openingMatchId++;
    }

    // MODIFIES: this
    // EFFECTS: initializes the ranking table for the tournament by putting in,
    //          and returns a number referring to the design of the tournament
    //          based on the number of matches added.
    public int initiateTournament() {
        this.rankingTable = new RankingTable(this.listOfPlayers);
        if (this.openingRoundMatches.size() == 1) {
            return 1;
        } else if (this.openingRoundMatches.size() == 2) {
            return 2;
        } else if (this.openingRoundMatches.size() < 5) {
            return 3;
        } else {
            return 4;
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates all matches in the opening bracket
    public void playOpeningBracket() {
        for (Match m : this.openingRoundMatches) {
            m.playMatch();
        }
    }

    // REQUIRES: players.size() == 8
    // MODIFIES: this
    // EFFECTS: arranges the top 8 players given after the opening bracket
    //          into the quarter-finals bracket and eliminates all players
    //          who don't qualify for quarter finals
    public void makeQuarterFinals(ArrayList<Player> players) {
        for (int i = 0; i < 4; i++) {
            Match match = new Match("QF" + (i + 1), players.get(i * 2), players.get(i * 2 + 1));
            this.quarterFinalMatches.add(match);
        }
        for (Player p : this.listOfPlayers) {
            if (!players.contains(p)) {
                p.eliminate();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates all 4 matches in the quarter-finals 
    //          bracket and adds the winning players to the 
    //          semi-finals bracket
    public void playQuarterFinals() {
        ArrayList<Player> advancedToSF = new ArrayList<Player>();
        for (Match m : this.quarterFinalMatches) {
            m.getPlayer1().addMatchToHistory(m);
            m.getPlayer2().addMatchToHistory(m);
            m.playMatch();
            advancedToSF.add(m.getWinner());
            if (m.getPlayer1().equals(m.getWinner())) {
                m.getPlayer2().eliminate();
            } else {
                m.getPlayer1().eliminate();
            }
        }
        makeSemiFinals(advancedToSF);
    }

    // REQUIRES: players.size() == 4
    // MODIFIES: this
    // EFFECTS: arranges the players given in players
    //          into the semi-finals bracket
    public void makeSemiFinals(ArrayList<Player> players) {
        for (int i = 0; i < 2; i++) {
            Match match = new Match("SF" + (i + 1), players.get(i * 2), players.get(i * 2 + 1));
            this.semiFinalMatches.add(match);
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates both matches in the semi-finals 
    //          bracket and adds the winning players to the 
    //          final match
    public void playSemiFinals() {
        ArrayList<Player> advancedToFinals = new ArrayList<Player>();
        for (Match m : this.semiFinalMatches) {
            m.getPlayer1().addMatchToHistory(m);
            m.getPlayer2().addMatchToHistory(m);
            m.playMatch();
            advancedToFinals.add(m.getWinner());
            if (m.getPlayer1().equals(m.getWinner())) {
                m.getPlayer2().eliminate();
            } else {
                m.getPlayer1().eliminate();
            }
        }
        this.finalMatch = new Match("F", advancedToFinals.get(0), advancedToFinals.get(1));
    }

    // MODIFIES: this
    // EFFECTS: simulates the final match and sets the winner
    //          of that match as the tournament's champion
    public void playFinalMatch() {
        this.finalMatch.getPlayer1().addMatchToHistory(this.finalMatch);
        this.finalMatch.getPlayer2().addMatchToHistory(this.finalMatch);
        this.finalMatch.playMatch();
        this.champion = this.finalMatch.getWinner();
        if (this.finalMatch.getPlayer1().equals(this.champion)) {
            this.finalMatch.getPlayer2().eliminate();
        } else {
            this.finalMatch.getPlayer1().eliminate();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("players", playersToJson());
        if (this.rankingTable != null) {
            json.put("ranking table", rankingTable.toJson());
        } else {
            json.put("ranking table", "not initiated");
        }
        json.put("opening bracket", matchesToJson(openingRoundMatches));
        json.put("quarter finals", matchesToJson(quarterFinalMatches));
        json.put("semi finals", matchesToJson(semiFinalMatches));
        if (this.finalMatch != null) {
            json.put("final", finalMatch.toJson());
        } else {
            json.put("final", "not set");
        }
        if (this.champion != null) {
            json.put("champion", champion.toJson());
        } else {
            json.put("champion", "not set");
        }
        return json;
    }

    // EFFECTS: returns players in this tournament as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p : listOfPlayers) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns matches in given bracket of this tournament as a JSON array
    private JSONArray matchesToJson(ArrayList<Match> matches) {
        JSONArray jsonArray = new JSONArray();
        for (Match m : matches) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    }
}
