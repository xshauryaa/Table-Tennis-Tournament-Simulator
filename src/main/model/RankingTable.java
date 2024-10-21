package model;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import persistence.Writable;

import java.util.HashMap;
import java.util.Arrays;

/**
 * Represents a ranking table for a tournament that ranks
 * all the players based on their statistics.
 */
public class RankingTable implements Writable {
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

    @Override
    public String toString() {
        String rankString = "";
        for (int i = 1; i <= this.rankingTable.size(); i++) {
            for (Map.Entry<String, Integer> entry : this.rankingTable.entrySet()) {
                if (entry.getValue() == i) {
                    rankString = rankString + entry.getValue() + ". " + entry.getKey() + "\n";
                }
            }
        }
        return rankString;
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
            }
        }
        return name;
    }

    // REQUIRES: numTopPlayers <= number of players
    public ArrayList<Player> getTopPlayers(int numTopPlayers) {
        ArrayList<Player> topNPlayers = new ArrayList<Player>();
        for (Map.Entry<String, Integer> entry : this.rankingTable.entrySet()) {
            if (entry.getValue() <= numTopPlayers) {
                // topNPlayers.add(entry.getKey(), entry.getValue());
                for (Player p : this.players) {
                    if (p.getName().equals(entry.getKey())) {
                        topNPlayers.add(p);
                    }
                }
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
        Player nextHighestRankedPlayer = new Player("X", 60);
        int maxWins = -1;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        for (int i = 0; i < players.size(); i++) {
            json.put("Rank " + i, playerLookup(getPlayerAtRank(i)).toJson());
        }
        return json;
    }

    // EFFECTS: returns player with given name, returns null if player not found
    private Player playerLookup(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
}
