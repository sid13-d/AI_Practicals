player(messi, forward).
player(ronaldo, forward).
player(neymar, forward).
player(ramos, defender).
player(ederson, goalkeeper).


attacker(Player) :- player(Player, forward).
defender(Player) :- player(Player, defender).
midfielder(Player) :- player(Player, midfielder).
goalkeeper(Player) :- player(Player, goalkeeper).

club(messi, barcelona).
club(ronaldo, manchester_united).
club(neymar, paris_saint_germain).
club(ramos, paris_saint_germain).
club(ederson, man_city).


same_club(Player1, Player2) :- club(Player1, Club), club(Player2, Club), Player1 \= Player2.

same_position(Player1, Player2) :- player(Player1, Position), player(Player2, Position), Player1 \= Player2.

