package model;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Represents a ranking table for a tournament that ranks
 * all the players based on their statistics.
 */
public class RankingTable {
    private HashMap<String, Integer> rankingTable;
    private ArrayList<Player> players;

    // REQUIRES: listOfPlayers.size() > 0
    // EFFECTS: Creates a ranking table of all given players, 
    //          that is initially listed alphabetically.
    public RankingTable(ArrayList<Player> listOfPlayers) {
        this.rankingTable = new HashMap<String, Integer>();
        this.players = listOfPlayers;
        String[] playerNames = new String[this.players.size()];
        for (int i = 0; i < this.players.size(); i++) {
            playerNames[i] = this.players.get(i).getName();
        }
        Arrays.sort(playerNames);
        for (int i = 0; i < playerNames.length; i++) {
            this.rankingTable.put(playerNames[i], i + 1);
        }
    }

    public HashMap<String, Integer> getRankings() {
        return this.rankingTable;
    }

    // REQUIRES: player with given name must be in the current tournament
    public int getPlayerRanking(String playerName) {
        return this.rankingTable.get(playerName);
    }

    // REQUIRES: rank <= number of players
    public String getPlayerAtRank(int rank) {
        String name = "";
        for (Map.Entry<String, Integer> entry : this.rankingTable.entrySet()) {
            if (entry.getValue() == rank) {
                name = entry.getKey();
                break;
            }
        }
        return name;
    }

    // REQUIRES: numTopPlayers <= number of players
    public HashMap<String, Integer> getTopPlayers(int numTopPlayers) {
        HashMap<String, Integer> topNPlayers = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : this.rankingTable.entrySet()) {
            if (entry.getValue() <= numTopPlayers) {
                topNPlayers.put(entry.getKey(), entry.getValue());
            }
        }
        return topNPlayers;
    }

    // MODIFIES: this
    // EFFECTS: updates the ranking table according to the players'
    //          latest statistics since the last call to this method.
    public void updateRankings() {
        HashMap<String, Integer> newRankings = new HashMap<String, Integer>();
        for (int rank = 1; rank <= this.players.size(); rank++) {
            Player next = getNextHighestRankedPlayer(newRankings);
            newRankings.put(next.getName(), rank);
        }
        this.rankingTable = newRankings;
    }

    protected Player getNextHighestRankedPlayer(HashMap<String, Integer> newRankings) {
        Player nextHighestRankedPlayer = null;
        int maxWins = 0;
        for (Player p : this.players) {
            if (!(newRankings.keySet().contains(p.getName()))) {
                if (p.getMatchesWon() > maxWins) {
                    nextHighestRankedPlayer = p;
                    maxWins = p.getMatchesWon();
                } else if (p.getMatchesWon() == maxWins) {
                    if (p.getPointsDifference() > nextHighestRankedPlayer.getPointsDifference()) {
                        nextHighestRankedPlayer = p;
                        maxWins = p.getMatchesWon();
                    } else if (p.getPointsDifference() == nextHighestRankedPlayer.getPointsDifference()) {
                        if (p.getPointsWon() > nextHighestRankedPlayer.getPointsWon()) {
                            nextHighestRankedPlayer = p;
                            maxWins = p.getMatchesWon();
                        }
                    }
                }
            }
        }
        return nextHighestRankedPlayer;
    }
}
