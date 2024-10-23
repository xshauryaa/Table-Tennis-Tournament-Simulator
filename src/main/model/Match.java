package model;

import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;

import persistence.Writable;

import static java.lang.Math.abs;

/**
 * Represents a match in the tournament, consisting of 2 players,
 * 3 sets, and a winner.
 */
public class Match implements Writable {
    private String id;
    private Player player1;
    private Player player2;
    private HashMap<String, Integer> set1Score;
    private HashMap<String, Integer> set2Score;
    private HashMap<String, Integer> set3Score;
    private Player winner;

    // EFFECTS: creates a match with two given players, with no sets 
    //          won, and scores start at 0-0 for all sets.
    public Match(String id, Player player1, Player player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        set1Score = new HashMap<String, Integer>();
        set2Score = new HashMap<String, Integer>();
        set3Score = new HashMap<String, Integer>();
        set1Score.put(player1.getName(), 0);
        set1Score.put(player2.getName(), 0);
        set2Score.put(player1.getName(), 0);
        set2Score.put(player2.getName(), 0);
        set3Score.put(player1.getName(), 0);
        set3Score.put(player2.getName(), 0);
        this.winner = null;
    }

    // EFFECTS: creates a match with two given players, set scores for all
    //          3 sets, and the given winner.
    public Match(String id, Player player1, Player player2, HashMap<String, Integer> set1, HashMap<String, Integer> set2, HashMap<String, Integer> set3, Player winner) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.set1Score = set1;
        this.set2Score = set2;
        this.set3Score = set3;
        this.winner = winner;
    }


    @Override
    public String toString() {
        return this.id + ": " + this.player1.getName() + " vs. " + this.player2.getName();
    }

    public String getId() {
        return this.id;
    }

    // EFFECTS: sets the id of the match to given id
    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    // REQUIRES: 1 <= num <= 3
    public HashMap<String, Integer> getSetScore(int setNum) {
        if (setNum == 1) {
            return this.set1Score;
        } else if (setNum == 2) {
            return this.set2Score;
        } else {
            return this.set3Score;
        }
    }

    // EFFECTS: sets the score of set with given number to given scores for each player
    public void updateSetScore(int setNum, int p1Score, int p2Score) {
        this.getSetScore(setNum).replace(this.player1.getName(), 0, p1Score);
        this.getSetScore(setNum).replace(this.player2.getName(), 0, p2Score);
    }

    // REQUIRES: playMatch() must have been run prior
    public Player getWinner() {
        return this.winner;
    }

    // MODIFIES: this
    // EFFECTS: plays the 3 sets of the match, adds each player's points to
    //          their tournament tally, and sets the scores for all 3 sets.
    public void playMatch() {
        int p1SetsWon = 0;
        int p2SetsWon = 0;
        int overallChange = 0;
        for (int i = 1; i <= 3; i++) {
            playSet(i);
            int score1 = getSetScore(i).get(this.player1.getName());
            int score2 = getSetScore(i).get(this.player2.getName());
            if (score1 > score2) {
                p1SetsWon++;
            } else {
                p2SetsWon++;
            }
            overallChange += score1 - score2;
        }
        if (p1SetsWon > p2SetsWon) {
            this.winner = this.player1;
            this.player1.winMatch(overallChange);
            this.player2.loseMatch(overallChange);
        } else {
            this.winner = this.player2;
            // abs - because in the case of p2 winning, overallChange will be negative.
            this.player2.winMatch(abs(overallChange));
            this.player1.loseMatch(abs(overallChange));
        }
    }

    // REQUIRES: 1 <= num <= 3
    // MODIFIES: this
    // EFFECTS: plays the set of the given number, adds each player's points
    //          to their tournament tally, and sets the scores for that set.
    protected void playSet(int setNum) {
        int player1Score;
        int player2Score;
        int[] thisSetScores = new int[2];
        for (int i = 1; i <= 21; i++) {
            if (thisSetScores[0] == 11 || thisSetScores[1] == 11) {
                break;
            }
            playPoint(thisSetScores);
        }
        player1Score = thisSetScores[0];
        player2Score = thisSetScores[1];
        if (setNum == 1) {
            updateSetScore(1, player1Score, player2Score);
        } else if (setNum == 2) {
            updateSetScore(2, player1Score, player2Score);
        } else {
            updateSetScore(3, player1Score, player2Score);
        }
        this.player1.addPoints(player1Score);
        this.player1.concedePoints(player2Score);
        this.player2.addPoints(player2Score);
        this.player2.concedePoints(player1Score);
    }

    // MODIFIES: setScore
    // EFFECTS: adds one point to either player's tally at random
    protected void playPoint(int[] setScore) {
        Random random = new Random();
        int chance = random.nextInt(2);
        if (chance == 0) {
            setScore[0]++;
        } else {
            setScore[1]++;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("player 1", player1.toJson());
        json.put("player 2", player2.toJson());
        json.put("set 1 score", setScoreToJson(set1Score));
        json.put("set 2 score", setScoreToJson(set2Score));
        json.put("set 3 score", setScoreToJson(set3Score));
        if (this.winner != null) {
            json.put("winner", winner.toJson());
        } else {
            json.put("winner", "not set");
        }
        return json;
    }

    // EFFECTS: converts a given set into a JSON object
    private JSONObject setScoreToJson(HashMap<String, Integer> set) {
        JSONObject json = new JSONObject();
        json.put(player1.getName(), set.get(player1.getName()));
        json.put(player2.getName(), set.get(player2.getName()));
        return json;
    }
}