package persistence;

import org.json.JSONObject;

import model.Tournament;

// Referenced by JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads tournament from JSON data stored in file
public class JsonReader {

    // EFFECTS: constructs a reader to read from source files
    public JsonReader(String source) {
        // stub
    }

    // EFFECTS: reads tournament from file and returns it
    public Tournament read() {
        return null; // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) {
        return ""; // stub
    }

    // EFFECTS: parses tournament from JSON object and returns it
    private Tournament parseTournament(JSONObject jsonObject) {
        return null; // stub
    }

    // MODIFIES: t
    // EFFECTS: parses matches from JSON object and adds them to tournament
    private void addMatches(Tournament t, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: t
    // EFFECTS: parses a single match from JSON object and adds it to tournament
    private void addMatch(Tournament t, JSONObject jsonObject) {
        // stub
    }

}
