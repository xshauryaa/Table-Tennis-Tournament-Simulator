package model;

import java.util.HashMap;

/**
 * Represents a match in the tournament, consisting of 2 players,
 * 3 sets, and a winner.
 */
public class Match {

    // EFFECTS: creates a match with two given players, with no sets 
    //          won, and scores start at 0-0 for all sets.
    public Match(Player player1, Player player2) {
        // stub
    }

    public Player getPlayer1() {
        return null; // stub
    }

    public Player getPlayer2() {
        return null; // stub
    }

    public HashMap<String, Integer> getSet1Score() {
        return null; // stub
    }

    public HashMap<String, Integer> getSet2Score() {
        return null; // stub
    }

    public HashMap<String, Integer> getSet3Score() {
        return null; // stub
    }

    // REQUIRES: playMatch() must have been run prior
    public Player getWinner() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: plays the 3 sets of the match, adds each player's points to
    //          their tournament tally, and sets the scores for all 3 sets.
    public void playMatch() {
        // stub
    }

    // REQUIRES: 1 <= num <= 3
    // MODIFIES: this
    // EFFECTS: plays the set of the given number, adds each player's points
    //          to their tournament tally, and sets the scores for that set.
    public void playSet(int num) {
        // stub
    }
}