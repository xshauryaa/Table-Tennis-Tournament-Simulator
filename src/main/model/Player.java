package model;

import java.util.ArrayList;

/**
 * Represents a player in the table tennis tournament, with statistics
 * such as points won/lost, matches won/lost, overall ability,ranking points, 
 * and information such as name, age, country of origin, and match history.
 */
public class Player {

    // REQUIRES: age > 0 && overallAbility > 0
    // EFFECTS: Creates a new Player object with given name,
    //          age, overall ability, and country of origin,
    //          sets the ranking table points to 0, and sets
    //          the matches and points win-loss records to 0-0 each.
    public Player(String name, int age, int overallAbility, String country) {
        // stub
    }

    public String getName() {
        return ""; // stub
    }

    public int getAge() {
        return 0; // stub
    }

    public String getCountry() {
        return ""; // stub
    }

    public int getPointsWon() {
        return 0; // stub
    }

    public int getPointsConceeded() {
        return 0; // stub
    }

    public int getPointsDifference() {
        return 0; // stub
    }

    public int getMatchesWon() {
        return 0; // stub
    }

    public int getMatchesLost() {
        return 0; // stub
    }

    public int getRankingPoints() {
        return 0; // stub
    }

    public int getOverallAbility() {
        return 0; // stub
    }

    public boolean isEliminated() {
        return false; // stub
    }

    public ArrayList<Match> getMatchHistory() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: increments the number of matches won, 
    //          adds 2 to the ranking points, and
    //          increases overall ability by given number.
    public void winMatch(int inc) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: increments the number of matches lost and
    //          decreases overall ability by given number.
    public void loseMatch(int dec) {
        // stub
    }

    // REQUIRES: points > 0
    // MODIFIES: this
    // EFFECTS: increases the number of points won by 
    //          given points
    public void addPoints(int points) {
        // stub
    }

    // REQUIRES: points > 0
    // MODIFIES: this
    // EFFECTS: decreases the number of points conceeded 
    //          by given points
    public void concedePoints(int points) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: gives player eliminated from tournament
    //          status
    public void eliminate() {
        // stub
    }
}
