package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a ranking table for a tournament that ranks
 * all the players based on their statistics.
 */
public class RankingTable {

    // REQUIRES: listOfPlayers.size() > 0
    // EFFECTS: Creates a ranking table of all given players, 
    //          that is initially listed alphabetically.
    public RankingTable(ArrayList<Player> listOfPlayers) {
        // stub
    }

    public HashMap<String, Integer> getRankings() {
        return null; // stub
    }

    public int getPlayerRanking(String playerName) {
        return 0; // stub
    }

    public String getPlayerAtRank(int rank) {
        return ""; // stub
    }

    public HashMap<String, Integer> getTopPlayers(int numTopPlayers) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: updates the ranking table according to the players'
    //          latest statistics since the last call to this method.
    public void updateRankings() {
        // stub
    }
}
