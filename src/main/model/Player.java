package model;

import java.util.ArrayList;

/**
 * Represents a player in the table tennis tournament, with statistics
 * such as points won/lost, matches won/lost, overall ability, and 
 * information such as name, age, country of origin, and match history.
 */
public class Player {
    private String name;
    private int overallAbility;
    private int matchesWon;
    private int matchesLost;
    private int pointsWon;
    private int pointsConceeded;
    private boolean isEliminated;
    private ArrayList<Match> matchHistory;

    // REQUIRES: age > 0 && overallAbility > 0
    // EFFECTS: Creates a new Player object with given name,
    //          age, overall ability, and country of origin,
    //          and sets the matches and points win-loss records 
    //          to 0-0 each.
    public Player(String name, int overallAbility) {
        this.name = name;
        this.overallAbility = overallAbility;
        matchesWon = 0;
        matchesLost = 0;
        pointsWon = 0;
        pointsConceeded = 0;
        isEliminated = false;
        matchHistory = new ArrayList<Match>();
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
        return this.pointsConceeded; // stub
    }

    public int getPointsDifference() {
        return this.pointsWon - this.pointsConceeded; // stub
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
        this.pointsConceeded += points;
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
    
}
