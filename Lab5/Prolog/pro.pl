player(lin_dan, singles).
player(lee_chong_wei, singles).
player(pv_sindhu, singles).
player(kento_momota, singles).
player(tai_tzu_ying, singles).
player(kim_astrups, doubles).
player(anders_rasmussen, doubles).
player(gabby_adcock, doubles).
player(chris_adcock, doubles).

singles_player(Player) :- player(Player, singles).
doubles_player(Player) :- player(Player, doubles).

country(lin_dan, china).
country(lee_chong_wei, malaysia).
country(pv_sindhu, india).
country(kento_momota, japan).
country(tai_tzu_ying, taiwan).
country(kim_astrups, denmark).
country(anders_rasmussen, denmark).
country(gabby_adcock, england).
country(chris_adcock, england).

country_player(Player, Country) :- country(Player, Country)

same_country(Player1, Player2) :- country(Player1, Country), country(Player2, Country), Player1 \= Player2.

same_discipline(Player1, Player2) :- player(Player1, Discipline), player(Player2, Discipline), Player1 \= Player2.


same_team(Player1, Player2) :-
    doubles_player(Player1),
    doubles_player(Player2),
    same_country(Player1, Player2).
