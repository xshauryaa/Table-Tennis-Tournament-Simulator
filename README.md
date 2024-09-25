# Table Tennis Tournament Simulator
My personal project for CPSC 210 in the term 2024W1 is going to be a Table Tennis Tournament Simulator Application. This will allow users to enter a list of any number of players, arranging them at random into an opening bracket of one-on-one matches. The top 8 players (based on their statistics after the opening bracket) will then proceed to the quarter-finals, and the tournament bracket will then be simulated further until a player wins the tournament final.

## Match - Non-Trivial Class (X)
The first non-trivial class will be called **Match**. It will serve the following purposes:
- *simulate* a singular match between 2 players with 3-5 sets played.
- determine a winner of the match to see who advances into the next round.
- *contain all the data about a given match* in the tournament such as match ID, winner, match score, etc.

## Tournament - Non-Trivial Class (Y)
The second non-trivial class will be called **Tournament**. It will serve the following purposes:
- *simulate* an entire tournament from the opening bracket to the final match.
- create different rounds and *divide matches* into each of the rounds.
- *track the progress* of the simulated tournament.
- control the flow of the simulation by *advancing players* from one round to another.
- determine a winner of the entire tournament.

## Player - Extra Class
Another class that will be built into the application will be called **Player**. It will serve the following purposes:
- keep record of the *details* of the player such as name, age, overall ability score, etc.
- keep record of their *tournament statistics* such as sets won, current round, points won, etc.
- *alter their overall ability* as they progress through the tournament based on their performances.

## Who will use it?
The application can be used the following classes of users:
- sports enthusiasts who would like to see how a tournament between their favourites would be like.
- pre-teens/teenagers who are interested in the sport.
- people who are interested in sports-based video games.

## Why this project is of interest to me:
I have long been a follower of table tennis. Be it world championships or the Olympic Games. Back in 2019, I used to play table tennis at a competitive level as well, and was about to go for my first ever tournament when unfortunately the pandemic struck. This application is something that definitely touches my interest in the sport. Apart from that, it is also the kind of application I'd love to make as I have grown up playing a lot of sport-simulation games, and I love the genre.
