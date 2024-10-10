package model;

import java.util.ArrayList;

/**
 * Represents a tournament with a list of players, an opening 
 * bracket of matches, a quarter finals bracket, a semi finals 
 * bracket, a final match, and a ranking table.
 */
public class Tournament {

    // EFFECTS: Creates a tournament with a name, no initial 
    //          matches, empty bracket for opening round, and 
    //          empty brackets for quarter-, semi-, and
    //          final matches.
    public Tournament(String name) {
        // stub
    }

    public String getName() {
        return ""; // stub
    }

    public ArrayList<Player> getListOfPlayers() {
        return null; // stub
    }

    public RankingTable getRankingTable() {
        return null; // stub
    }
    
    public ArrayList<Match> getOpeningRoundMatches() {
        return null; // stub
    }

    public ArrayList<Match> getQuarterFinalMatches() {
        return null; // stub
    }

    public ArrayList<Match> getSemiFinalMatches() {
        return null; // stub
    }

    public Match getFinalMatch() {
        return null; // stub
    }

    public Player getChampion() {
        return null; // stub
    }

    // REQUIRES: match must not already be in tournament
    // MODIFIES: this
    // EFFECTS: Adds given match to the opening bracket, adds
    //          the players of the match to the list of players,
    //          and adds this match to each player's match history
    public void addMatch(Match match) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initializes the ranking table for the tournament by putting in,
    //          and returns a number referring to the design of the tournament
    //          based on the number of matches added.
    public int initiateTournament() {
        return 0;// stub
    }

    // MODIFIES: this
    // EFFECTS: simulates all matches in the opening bracket
    public void playOpeningBracket() {
        // stub
    }
    
    // MODIFIES: this
    // EFFECTS: arranges the top 8 players given after the opening bracket
    //          into the quarter-finals bracket
    public void makeQuarterFinals(ArrayList<Player> players) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: simulates all 4 matches in the quarter-finals 
    //          bracket and adds the winning players to the 
    //          semi-finals bracket
    public void playQuarterFinals() {
        // stub
    }

    // REQUIRES: players.size() == 4
    // MODIFIES: this
    // EFFECTS: arranges the players given in players
    //          into the semi-finals bracket
    public void makeSemiFinals(ArrayList<Player> players) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: simulates both matches in the semi-finals 
    //          bracket and adds the winning players to the 
    //          final match
    public void playSemiFinals() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: simulates the final match and sets the winner
    //          of that match as the tournament's champion
    public void playFinalMatch() {
        // stub
    }
}
