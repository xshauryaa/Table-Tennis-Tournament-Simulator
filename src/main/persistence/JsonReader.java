package persistence;

import model.Player;
import model.Match;
import model.RankingTable;
import model.Tournament;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import org.json.*;

// Referenced by JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads tournament from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source files
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads tournament from file and returns it
    public Tournament read() throws IOException{
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTournament(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses tournament from JSON object and returns it
    private Tournament parseTournament(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Tournament t = new Tournament(name);
        addOpeningMatches(t, jsonObject);
        addQFMatches(t, jsonObject);
        addSFMatches(t, jsonObject);
        if (jsonObject.get("final").equals("not set")) {
            // pass
        } else {
            JSONObject finalMatch = (JSONObject) jsonObject.get("final");
            t.setFinalMatch(parseMatch(finalMatch));
        }
        t.initiateTournament();
        t.getRankingTable().updateRankings();
        if (jsonObject.get("champion").equals("not set")) {
            // pass
        } else {
            JSONObject champion = (JSONObject) jsonObject.get("champion");
            t.setChampion(parsePlayer(champion));
        }
        return t;
    }

    // MODIFIES: t
    // EFFECTS: parses opening matches from JSON object and adds them to tournament
    private void addOpeningMatches(Tournament t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("opening bracket");
        for (Object json : jsonArray) {
            JSONObject nextMatch = (JSONObject) json;
            t.addMatch(parseMatch(nextMatch));
        }
    }

    // MODIFIES: t
    // EFFECTS: parses quarter final matches from JSON object and adds them to tournament
    private void addQFMatches(Tournament t, JSONObject jsonObject) {
        ArrayList<Match> qf = new ArrayList<Match>();
        JSONArray jsonArray = jsonObject.getJSONArray("quarter finals");
        for (Object json : jsonArray) {
            JSONObject nextMatch = (JSONObject) json;
            qf.add(parseMatch(nextMatch));
        }
        t.setQuarterFinals(qf);
    }

    // MODIFIES: t
    // EFFECTS: parses semi final matches from JSON object and adds them to tournament
    private void addSFMatches(Tournament t, JSONObject jsonObject) {
        ArrayList<Match> sf = new ArrayList<Match>();
        JSONArray jsonArray = jsonObject.getJSONArray("semi finals");
        for (Object json : jsonArray) {
            JSONObject nextMatch = (JSONObject) json;
            sf.add(parseMatch(nextMatch));
        }
        t.setSemiFinals(sf);
    }

    // MODIFIES: t
    // EFFECTS: parses a single match from JSON object and returns it
    private Match parseMatch(JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        JSONObject nextPlayer = (JSONObject) jsonObject.get("player 1");
        Player p1 = parsePlayer(nextPlayer);
        nextPlayer = (JSONObject) jsonObject.get("player 2");
        Player p2 = parsePlayer(nextPlayer);
        JSONObject set1Score = (JSONObject) jsonObject.get("set 1 score");
        HashMap<String, Integer> set1 = parseSet(p1.getName(), p2.getName(), set1Score);
        JSONObject set2Score = (JSONObject) jsonObject.get("set 2 score");
        HashMap<String, Integer> set2 = parseSet(p1.getName(), p2.getName(), set2Score);
        JSONObject set3Score = (JSONObject) jsonObject.get("set 3 score");
        HashMap<String, Integer> set3 = parseSet(p1.getName(), p2.getName(), set3Score);
        Player winner = null;
        if (jsonObject.get("winner").equals("not set")) {
            // pass
        } else {
            JSONObject winnerPlayer = (JSONObject) jsonObject.get("winner");
            winner = parsePlayer(winnerPlayer);
        }
        Match m = new Match(id, p1, p2, set1, set2, set3, winner);
        return m;
    }

    private HashMap<String, Integer> parseSet(String name1, String name2, JSONObject set) {
        int score1 = set.getInt(name1);
        int score2 = set.getInt(name2);
        HashMap<String, Integer> setScore = new HashMap<String, Integer>();
        setScore.put(name1, score1);
        setScore.put(name2, score2);
        return setScore;
    }

    // EFFECTS: parses a single player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int overallAbility = jsonObject.getInt("overall ability");
        int matchesWon = jsonObject.getInt("matches won");
        int matchesLost = jsonObject.getInt("matches lost");
        int pointsWon = jsonObject.getInt("points won");
        int pointsConceded = jsonObject.getInt("points conceded");
        boolean isEliminated = jsonObject.getBoolean("eliminated");
        ArrayList<String> history = parseHistory(jsonObject);
        Player player = new Player(name, overallAbility, matchesWon, matchesLost, pointsWon, pointsConceded, isEliminated, history);
        return player;
    }

    // EFFECTS: parses the match history from JSON array and returns it
    private ArrayList<String> parseHistory(JSONObject json) {
        ArrayList<String> history = new ArrayList<String>();
        JSONArray jsonArray = json.getJSONArray("match history");
        for (Object jsonObject : jsonArray) {
            String nextMatch = jsonObject.toString();
            history.add(nextMatch);
        }
        return history;
    }
}
