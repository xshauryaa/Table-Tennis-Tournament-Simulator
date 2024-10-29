package ui;

import model.Player;
import model.Match;
import model.Tournament;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

import org.json.JSONException;


// Table Tennis Tournament Simulator Application
public class TournamentSimulator {
    private Tournament tournament;
    private Scanner input = new Scanner(System.in); 
    private String divider = "=================================================";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/tournament.json";

    // EFFECTS: runs the simulator application
    public TournamentSimulator() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        System.out.println("Welcome to the Table Tennis Tournament Simulator!");
        runTournamentSimulator();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // Structure inspired by Teller App, GitHub, created by Paul Carter
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    private void runTournamentSimulator() {
        boolean continueProgram = true;
        while (continueProgram) {
            System.out.println(divider);
            System.out.println("Start new tournament (s)");
            System.out.println("Load tournament (l)");
            System.out.println("Quit application (q)");
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("s")) {
                System.out.println("Enter a name for your tournament (no spaces!): ");
                String tournamentName = input.next();
                tournament = new Tournament(tournamentName);
                createTournament();
                simulateTournament();
            } else if (command.equals("l")) {
                loadTournament();
            } else if (command.equals("q")) {
                System.out.println("Quitting application...\nGoodbye!");
                continueProgram = false;
            } else {
                System.out.println("Invalid input! Please try again!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates tournament based on user input
    private void createTournament() {
        String nextCommand = null;
        while (true) {
            displayTournamentCreationMenu();
            nextCommand = input.next();
            nextCommand = nextCommand.toLowerCase();
            if (nextCommand.equals("a")) {
                consoleAddMatch();
            } else if (nextCommand.equals("v")) {
                consoleViewMatches();
            } else if (nextCommand.equals("b")) {
                break;
            } else {
                System.out.println("Invalid input! Please try again!");
            }
        }
    }

    // EFFECTS: displays menu of possible options while creating tournament to user
    private void displayTournamentCreationMenu() {
        System.out.println(divider);
        System.out.println("Add new match to your tournament (a)");
        System.out.println("View all matches in your tournament so far (v)");
        System.out.println("Begin tournament (b)");
    }

    // MODIFIES: this
    // EFFECTS: creates a match based on user input and adds it to tournament
    private void consoleAddMatch() {
        System.out.println(divider);
        System.out.println("Enter details for Player 1:");
        Player p1 = consoleAddPlayer();
        System.out.println("Enter details for Player 2:");
        Player p2 = consoleAddPlayer();
        Match m = new Match("x", p1, p2);
        tournament.addMatch(m);
        System.out.println("Match has been added!");
    }

    // EFFECTS: creates a player based on user input and returns the same
    private Player consoleAddPlayer() {
        System.out.println("Name: ");
        String name = input.next();
        System.out.println("Overall Ability: ");
        int overallAbility = input.nextInt();
        Player p = new Player(name, overallAbility);
        return p;
    }

    // EFFECTS: enlists all matches added to tournament at the time of calling
    private void consoleViewMatches() {
        System.out.println(divider);
        System.out.println("Current matches in the tournament's opening bracket:");
        for (Match m : tournament.getOpeningRoundMatches()) {
            System.out.println(m);
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates the entire tournament in the console round by round
    private void simulateTournament() {
        tournament.initiateTournament();
        if (tournament.getDesignType() == 1) {
            playCondition1();
        } else if (tournament.getDesignType() == 2) {
            playCondition2();
        } else if (tournament.getDesignType() == 3) {
            playCondition3();
        } else {
            playCondition4();
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates the tournament if user entered only 1 match
    private void playCondition1() {
        System.out.println(divider);
        Match onlyMatch = tournament.getOpeningRoundMatches().get(0);
        tournament.setFinalMatch(onlyMatch);
        tournament.playFinalMatch();
        System.out.println(tournament.getName() + " is complete!");
        System.out.println("The champion is " + tournament.getChampion().getName());
    }

    // MODIFIES: this
    // EFFECTS: simulates the tournament if user entered 2 matches
    private void playCondition2() {
        System.out.println(divider);
        tournament.playOpeningBracket();
        Player finalist1 = tournament.getOpeningRoundMatches().get(0).getWinner();
        Player finalist2 = tournament.getOpeningRoundMatches().get(1).getWinner();
        if (tournament.getOpeningRoundMatches().get(0).getPlayer1().equals(finalist1)) {
            tournament.getOpeningRoundMatches().get(0).getPlayer2().eliminate();
        } else {
            tournament.getOpeningRoundMatches().get(0).getPlayer1().eliminate();
        }
        if (tournament.getOpeningRoundMatches().get(1).getPlayer1().equals(finalist1)) {
            tournament.getOpeningRoundMatches().get(1).getPlayer2().eliminate();
        } else {
            tournament.getOpeningRoundMatches().get(1).getPlayer1().eliminate();
        }
        tournament.setFinalMatch(new Match("F", finalist1, finalist2));
        System.out.println("The opening matches have been played!");
        tournament.setStatus("F");
        if (tournament.getStatus().equals("F")) {
            simulateFinalMatch();
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates the tournament if user entered 3 or 4 matches
    private void playCondition3() {
        System.out.println(divider);
        tournament.playOpeningBracket();
        tournament.getRankingTable().updateRankings();
        System.out.println("The opening matches have been played!");
        System.out.println(divider);
        ArrayList<Player> top4 = tournament.getRankingTable().getTopPlayers(4);
        for (Player p : tournament.getListOfPlayers()) {
            if (!top4.contains(p)) {
                p.eliminate();
            }
        }
        tournament.makeSemiFinals(top4);
        tournament.setStatus("SF");
        if (tournament.getStatus().equals("SF")) {
            simulateSemiFinals();
        }
        if (tournament.getStatus().equals("F")) {
            simulateFinalMatch();
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates the tournament if user entered more than 4 matches
    private void playCondition4() {
        System.out.println(divider);
        tournament.playOpeningBracket();
        tournament.getRankingTable().updateRankings();
        System.out.println("The opening matches have been played!");
        tournament.setStatus("QF");
        if (tournament.getStatus().equals("QF")) {
            simulateQuarterFinals();
        }
        if (tournament.getStatus().equals("SF")) {
            simulateSemiFinals();
        }
        if (tournament.getStatus().equals("F")) {
            simulateFinalMatch();
        }
    }

    // MODIFIES: this
    // EFFECTS: simulates quarter final matches
    private void simulateQuarterFinals() {
        duringTournamentMenu();
        System.out.println(divider);
        ArrayList<Player> top8 = tournament.getRankingTable().getTopPlayers(8);
        tournament.makeQuarterFinals(top8);
        tournament.playQuarterFinals();
        System.out.println("The quarter-final matches have been played!");
        tournament.setStatus("SF");
    }

    // MODIFIES: this
    // EFFECTS: simulates semi final matches
    private void simulateSemiFinals() {
        duringTournamentMenu();
        System.out.println(divider);
        tournament.playSemiFinals();
        System.out.println("The semi-final matches have been played!");
        tournament.setStatus("F");
    }

    // MODIFIES: this
    // EFFECTS: simulates the final match
    private void simulateFinalMatch() {
        duringTournamentMenu();
        System.out.println(divider);
        tournament.playFinalMatch();
        System.out.println(tournament.getName() + " is complete!");
        System.out.println("The champion is " + tournament.getChampion().getName());
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: displays menu of possible options during the simulation for users
    //          processes input
    private void duringTournamentMenu() {
        while (true) {
            System.out.println(divider);
            System.out.println("View ranking table (r)");
            System.out.println("View specific player statistics (p)");
            System.out.println("View specific match details (m)");
            System.out.println("Save current progress of tournament (s)");
            System.out.println("Play next round (n)");
            System.out.println("Quit application (q)");
            String nextCommand = input.next();
            if (nextCommand.equals("r")) {
                System.out.println(tournament.getRankingTable());
            } else if (nextCommand.equals("p")) {
                selectAndViewPlayerStats();
            } else if (nextCommand.equals("m")) {
                selectAndViewMatchDetails();
            } else if (nextCommand.equals("s")) {
                saveTournament();
            } else if (nextCommand.equals("q")) {
                System.out.println("Saving progress...");
                saveTournament();
                System.out.println("Quitting application...");
                System.out.println("Goodbye!");
                System.exit(0);
            } else if (nextCommand.equals("n")) {
                break;
            } else {
                System.out.println("Invalid input! Please try again!");
            }
        }
    }

    // EFFECTS: enlists all players in tournament and processes user input
    private void selectAndViewPlayerStats() {
        System.out.println(divider);
        System.out.println("Players in the current tournament:");
        int i = 1;
        for (Player p : tournament.getListOfPlayers()) {
            System.out.println(i + ". " + p);
            i++;
        }
        System.out.println("Select number of player to view: ");
        int playerNumber = input.nextInt();
        displayPlayerStats(tournament.getListOfPlayers().get(playerNumber - 1));
    }

    // EFFECTS: displays statistics of given player
    private void displayPlayerStats(Player p) {
        System.out.println("Player: " + p.getName());
        System.out.println("Matches won: " + p.getMatchesWon());
        System.out.println("Matches lost: " + p.getMatchesLost());
        System.out.println("Point difference: " + p.getPointsDifference());
        System.out.println("Points won: " + p.getPointsWon());
        System.out.println("Points conceeded: " + p.getPointsConceded());
        String status = null;
        if (p.isEliminated()) {
            status = "Eliminated";
        } else {
            status = "Not eliminated";
        }
        System.out.println("Tournament status: " + status);
        System.out.println("Match history: ");
        for (String m : p.getMatchHistory()) {
            System.out.println(m);
        }
    }

    @SuppressWarnings("methodlength")
    // EFFECTS: enlists all matches in tournament and processes user input
    private void selectAndViewMatchDetails() {
        System.out.println(divider);
        int i = 1;
        System.out.println("Opening Bracket Matches:");
        for (Match m : tournament.getOpeningRoundMatches()) {
            System.out.println(i + ". " + m);
            i++;
        }
        i = 1;
        if (tournament.getQuarterFinalMatches().size() != 0) {
            System.out.println("Quarter Final Matches:");
            for (Match m : tournament.getQuarterFinalMatches()) {
                System.out.println(i + ". " + m);
                i++;
            }
        }
        i = 1;
        if (tournament.getSemiFinalMatches().size() != 0) {
            System.out.println("Semi Final Matches:");
            for (Match m : tournament.getSemiFinalMatches()) {
                System.out.println(i + ". " + m);
                i++;
            }
        }
        i = 1;
        if (tournament.getFinalMatch() != null) {
            System.out.println("Final Match:");
            System.out.println(i + ". " + tournament.getFinalMatch());
        }
        System.out.println("Choose match round (O / QF / SF / F): ");
        String matchRound = input.next();
        System.out.println("Choose match number: ");
        int matchNumber = input.nextInt();
        selectMatch(matchRound, matchNumber);
    }

    // EFFECTS: selects a match based on the number given
    private void selectMatch(String matchRound, int matchNum) {
        if (matchRound.equals("O")) {
            displayMatchDetails(tournament.getOpeningRoundMatches().get(matchNum - 1));
        } else if (matchRound.equals("QF")) {
            displayMatchDetails(tournament.getQuarterFinalMatches().get(matchNum - 1));
        } else if (matchRound.equals("SF")) {
            displayMatchDetails(tournament.getSemiFinalMatches().get(matchNum - 1));
        } else if (matchRound.equals("F")) {
            displayMatchDetails(tournament.getFinalMatch());
        }
    }

    // EFFECTS: displays the details of given match
    private void displayMatchDetails(Match m) {
        System.out.println(m);
        System.out.println("Set 1: " + m.getSetScore(1));
        System.out.println("Set 2: " + m.getSetScore(2));
        System.out.println("Set 3: " + m.getSetScore(3));
        System.out.println("Winner: " + m.getWinner().getName());
    }

    // EFFECTS: saves the progress of current tournament to file
    private void saveTournament() {
        try {
            jsonWriter.open();
            jsonWriter.write(tournament);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads tournament from file
    private void loadTournament() {
        try {
            tournament = jsonReader.read();
            System.out.println("Loaded " + tournament.getName() + " from " + JSON_STORE);
            if (tournament.getDesignType() == 2) {
                playCondition2();
            } else if (tournament.getDesignType() == 3) {
                playCondition3();
            } else {
                playCondition4();
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("No tournament found!");
        }
    }
}
