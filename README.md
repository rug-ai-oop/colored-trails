# Colored Trails
Welcome to the Colored Trails GUI repository! This project is a Java-based graphical user interface 
implementation of the classic Colored Trails game, known for its strategic gameplay where players navigate
through a colorful board to reach their goals.
Our version introduces a unique twist: players can communicate 
their goals to others, allowing for strategic alliances, negotiations, and even the chance to mislead by sending
false signals.
## Game Rules
The goal of the players is to maximize their score, by getting as close as their goal on the board. Both players start
at a predifined patch (the middle of the board in the picture). Players can move horizontally and vertically to a tile
next to their current location, but only if they hand in a chip of the same color as their destination tile. 
Each player receives four chips at the beginning of the game, randomly drawn from one of the colors on the board.
This means that a player may not end up with the chips he needs to reach his goal location. To help players to
reach their goals, players can negotiate over ownership of the chips. Negotiation takes the form of alternating
in making an offer. To make the negotiation even more trustworthy, or misleading, the players can communicate their 
goal to each other, allowing for false signals, and multiple "goal reveals". The initiator always starts by making the
first offer. The responder can then decide to accept
the offer of the initiator, make a new offer, or withdraw from the negotiation. If the
responder accepts, the chips are divided as suggested by the responder and the negotiation ends. Alternatively, if
the responder withdraws, each player keeps his own chips and the negotiation ends as well. If the responder decides
to make a new offer, the players switch roles and the negotiation continues. The negotiation is limited to 40 offers,
i.e. after both players have made 20 offers, the negotiation ends.
![image](https://github.com/rug-ai-oop/colored-trails/assets/130935064/33e34890-30f7-46c4-90a9-fc95f28c7c7b)

## Score Calculation
Each player is scored based on how closely he ends up to his goal location, indicated by a flag on the board.
The scores are listed in the table below.

| SITUATION                                  | CHANGE IN SCORE                                   |
|--------------------------------------------|---------------------------------------------------|
| Ending on your goal location               | +50 points                                        |
| Ending anywhere but your goal location     | -10 points per step towards your goal location    |
| Ending with unused chips                   | +5 points per chip  


## Project Purpose
  ### Theory of Mind (TOM)
   Theory of Mind (TOM) is the ability to reason explicitly about unobservable mental content of others, such as 
   beliefs, goals, and intentions. The idea of TOM extends to orders of TOM, i.e. the depth of thought of 
   the person making use of it. For example, in the sentence ”Paul thinks 
   that Maria likes ice-cream.”, Paul shows first order TOM. On the other 
   hand, in the sentence, ”Paul thinks that Maria thinks that he likes ice-cream.”, Paul shows second order TOM.
  ### Experimental Design
  The purpose of this project is to stgitudy human behavior in negotiation with self-interested parties, that show 
  different orders of TOM. In this scope, the GUI will be used as an interacting bridge between humans and TOM Colored
  Trails Agents designed by Harmen de Weerd, assistant Professor of the Faculty of Science and Engineering, University 
  of Groningen.
  #### Relevant Research Questions
  - "Do humans have a tendency toward lying about their goal location?"
  - "Do humans show a pattern in lying about their goal location?"
  - "Do humans have act differently when negotiating with an agent versus with a human?"
## Acknoledgements
Description was taken and adapted from Harmen de Weerd's website. Prior research conducted on Colored Trails ca be 
found at the same address:  
https://www.harmendeweerd.nl/alternating-offers-negotiation/
