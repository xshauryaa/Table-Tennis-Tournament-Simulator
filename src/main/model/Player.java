package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents a player in the table tennis tournament, with statistics
 * such as points won/lost, matches won/lost, overall ability, and 
 * information such as name, age, country of origin, and match history.
 */
public class Player implements Writable {
    private String name;
    private int overallAbility;
    private int matchesWon;
    private int matchesLost;
    private int pointsWon;
    private int pointsConceded;
    private boolean isEliminated;
    private ArrayList<Match> matchHistory;

    // REQUIRES: overallAbility > 0
    // EFFECTS: Creates a new Player object with given name
    //          and overall ability, and sets the matches 
    //          and points win-loss records to 0-0 each.
    public Player(String name, int overallAbility) {
        this.name = name;
        this.overallAbility = overallAbility;
        matchesWon = 0;
        matchesLost = 0;
        pointsWon = 0;
        pointsConceded = 0;
        isEliminated = false;
        matchHistory = new ArrayList<Match>();
    }

    // REQUIRES: overallAbility > 0 && matchesWon > 0 && matchesLost > 0 && pointsWon > 0 && pointsConceded > 0
    // EFFECTS: Creates a new Player object with given name,
    //          overall ability, matches won, matches lost, 
    //          points won, points conceded, elimination status
    //          and match history
    public Player(String name, int overallAbility, int matchesWon, int matchesLost, int pointsWon, int pointsConceded, boolean eliminated, ArrayList<Match> history) {
        this.name = name;
        this.overallAbility = overallAbility;
        this.matchesWon = matchesWon;
        this.matchesLost = matchesLost;
        this.pointsWon = pointsWon;
        this.pointsConceded = pointsConceded;
        this.isEliminated = eliminated;
        this.matchHistory = history;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", OVR: " + this.overallAbility;
    }

    public String getName() {
        return this.name; // stub
    }

    public int getPointsWon() {
        return this.pointsWon; // stub
    }

    public int getPointsConceded() {
        return this.pointsConceded; // stub
    }

    public int getPointsDifference() {
        return this.pointsWon - this.pointsConceded; // stub
    }

    public int getMatchesWon() {
        return this.matchesWon; // stub
    }

    public int getMatchesLost() {
        return this.matchesLost; // stub
    }

    public int getOverallAbility() {
        return this.overallAbility; // stub
    }

    public boolean isEliminated() {
        return this.isEliminated; // stub
    }

    public ArrayList<Match> getMatchHistory() {
        return this.matchHistory; // stub
    }

    // MODIFIES: this
    // EFFECTS: increments the number of matches won, 
    //          and increases overall ability by given number.
    public void winMatch(int inc) {
        this.matchesWon++;
        this.overallAbility += inc;
    }

    // MODIFIES: this
    // EFFECTS: increments the number of matches lost and
    //          decreases overall ability by given number.
    public void loseMatch(int dec) {
        this.matchesLost++;
        this.overallAbility -= dec;
    }

    // REQUIRES: points > 0
    // MODIFIES: this
    // EFFECTS: increases the number of points won by 
    //          given points
    public void addPoints(int points) {
        this.pointsWon += points;
    }

    // REQUIRES: points > 0
    // MODIFIES: this
    // EFFECTS: decreases the number of points conceeded 
    //          by given points
    public void concedePoints(int points) {
        this.pointsConceded += points;
    }

    // MODIFIES: this
    // EFFECTS: gives player eliminated from tournament
    //          status
    public void eliminate() {
        this.isEliminated = true;
    }

    // REQUIRES: this player must be in given match
    // MODIFIES: this
    // EFFECTS: Adds given match to player's match history
    public void addMatchToHistory(Match match) {
        this.matchHistory.add(match);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("overall ability", overallAbility);
        json.put("matches won", matchesWon);
        json.put("matches lost", matchesLost);
        json.put("points won", pointsWon);
        json.put("points conceded", pointsConceded);
        json.put("eliminated", isEliminated);
        json.put("match history", matchHistoryToJson());
        return json;
    }

    // EFFECTS: returns matches in this player's match history as a JSON array
    private JSONArray matchHistoryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Match m : matchHistory) {
            jsonArray.put(m.toJson());
        }
        return jsonArray;
    } 
    
}
