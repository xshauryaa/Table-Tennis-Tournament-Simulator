# Table Tennis Tournament Simulator
My personal project for CPSC 210 in the term 2024W1 is going to be a Table Tennis Tournament Simulator Application. This will allow users to enter a list of *any* number of **matches** with players of their choice, arranging them into an *opening bracket* for the **tournament**. Depending on the amount of matches entered, a tournament bracket will be designed. For example, 1 match will mean only a final, 2 will mean semi-finals and then a final, and so on. In case of multiple matches, the top 8 players (from a ranking board based on their statistics after the opening bracket) will then proceed to the *quarter-finals*, and the tournament bracket will then be simulated further until a player wins the *tournament final*.

## Who will use it?
The application can be used the following classes of users:
- **sports enthusiasts** who would like to see how a tournament between their favourites would be like.
- **pre-teens/teenagers** who are interested in the sport.
- people who are **interested in sports-based video games**.

## Why this project is of interest to me:
I have long been a follower of table tennis. Be it world championships or the Olympic Games. Back in 2019, I used to play table tennis at a competitive level as well, and was about to go for my first ever tournament when unfortunately the pandemic struck. This application is something that definitely touches my interest in the sport. Apart from that, it is also the kind of application I'd love to make as I have grown up playing a lot of sport-simulation games, and I love the genre.

## User Stories
- As a user, I want to be able to create a match with two players of my choice.
- As a user, I want to be able to add a match into a tournament.
- As a user, I want to be able to view each match and the overall tournament bracket.
- As a user, I want to be able to select a match and view the scores of each set played in that match.
- As a user, I want to be able to view the statistics of a player in the current tournament.
- As a user, I want to be able to see the rankings of the players after each round.
- As a user, I want to be able to select a player and view their match history in the tournament.
- As a user, I want to have the option to save the progress of my tournament at any given time.
- As a user, I want to have the option to quit one tournament and start/resume another without losing any progress.
- As a user, I want to have the option to reload my last tournament progress.
- As a user, I want to be able to view what stage I left a tournament at before I reload any of them.
- As a user, I want to see which matches have an average player OVR above a certain number

# Instructions for End User
- You can create a new tournament by clicking the "Start New Tournament" button on the opening menu
- You can add a new match to your tournament related to the user story "adding multiple matches to a tournament" by clicking the "Add Match" button
- You can filter matches while creating them related to the user story "seeing which matches have an average player OVR higher than certain numbers" by using the dropdown option besides the "Begin Tournament" button
- You can locate my visual component by looking at the left hand side of my application and across multiple buttons with their icons
- You can save the state of my application by clicking on the "Save Tournament" button on the side menu of the game
- You can reload the state of my application by clicking the "Load Tournament" buttons on the opening menu
- You can view statistics of a specific player by clicking on the "View Player Statistics" button on the side menu
- You can view details of a specific match by clicking on the "View Match Details" button on the side menu
- You can view the ranking table by clicking on the "View Ranking Table" button on the side menu
- You can quit the tournament stage by clicking on the "Quit Tournament" button on the side menu and choose whether or not you'd like to save your progress

# Phase 4: Task 3
Given the chance to continue on this project, there's quite a few refactoring changes I'd introduce. 
One major refactoring change that stands out would be creating an abstract class that extends JPanel that would be called "RoundSimulationPanel". The PlayOpeningMatchesPanel, PlayQuarterFinalMatchesPanel, and PlaySemiFinalMatchesPanel classes would extend this class, since they all have a similar structure of a title, button, and match display. This would make for a cleaner hierarchy and more cohesive design. It will be much more simpler and sorted to just override the methods according to what's needed by which particular round, and have the main design limited to one class instead of multiple replications.