-- -- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- -- -- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- -- -- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO users(username,password,enabled) VALUES ('ISMP15','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'ISMP15','admin');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
    VALUES (1, 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 6, 'BARREL_OF_RUM', 79, 56, 'admin1');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games, username) 
    VALUES (2, 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 2, 1220, 220.0, 720, 540, 121, 24.2, 3, 'BARREL_OF_RUM', 59, 26, 'owner1');

 INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
     VALUES (3, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'vet1');


INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points, player_id, privacity,game_status) 
VALUES ('Prueba0', 'ABCD124', '5,8,2', 3, 7, 5 ,34,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','3,4,7', 1, 'PUBLIC',false);

 INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points,player_id, privacity,game_status) 
 VALUES ('Prueba1', 'ABCD123', '1,8,3', 3, 4, 2 ,27,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','2,4,9',1, 'PUBLIC',false);

 INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points,player_id, privacity,game_status) 
 VALUES ('Prueba2', 'ABCD122', '4,1,3', 3, 2, 1 ,29,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','3,2,7',1, 'PRIVATE',false);
--AQUI HAY QUE AÑADIR ATRIBUTOS NUEVOS METIDOS

-- /*INSERT INTO person(first_Name,surname,password,user_Name,email) VALUES ('Pablo', 'Rivera','@Pablo1','pabrivjim','pabrivjim@alum.us.es');*/

INSERT INTO cards(id,card_type) VALUES (1, 'DOUBLON');
INSERT INTO cards(id,card_type) VALUES (2, 'GOLDEN_CUP');
INSERT INTO cards(id,card_type) VALUES (3, 'RUBY');
INSERT INTO cards(id,card_type) VALUES (4, 'DIAMOND');

 INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
     VALUES (1, 'Gold_points', 'Get 300 points.', './img/achiev_gold_points.png', 300, 'GOLD', 'POINTS');
 INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
     VALUES (2, 'Silver_loses', 'Loses 200 times.', './img/achiev_silver_loses.png', 200, 'SILVER', 'LOSES');
 INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
     VALUES (3, 'Gold_loses', 'Loses 300 times.', './img/achiev_gold_loses.png', 300, 'GOLD', 'LOSES');



 INSERT INTO cells(position,card) VALUES (1,'DOUBLON');

INSERT INTO islands(id,island_num) VALUES (1,1);
INSERT INTO islands(id,island_num) VALUES (2,2);
INSERT INTO islands(id,island_num) VALUES (3,3);
INSERT INTO islands(id,island_num) VALUES (4,4);
INSERT INTO islands(id,island_num) VALUES (5,5);
INSERT INTO islands(id,island_num, card_id) VALUES (6,6, 1);



INSERT INTO forums(id, name, description) VALUES(1, 'Players', 'Forum about players');
INSERT INTO forums(id, name, description) VALUES(2, 'Games', 'Forum about games');

INSERT INTO topics(id, name, description) VALUES(1, 'Rival players', 'do you want to be my rival?');

INSERT INTO generals(id, total_games, total_duration_all_games) VALUES (1, 200, 300);

INSERT INTO deck(id) VALUES (1);

INSERT INTO admins(first_name, surname, email, username) VALUES ('Ismael', 'Perez', 'ismperort@alum.us.es', 'ISMP15');

INSERT INTO comments(id, message) VALUES (1, 'I agree');


INSERT INTO players_achievements(player_id, achievement_id) VALUES (1, 1); --ESTA TABLA ME DA LOS LOGROS QUE TENGO COMPLETADOS
INSERT INTO players_achievements(player_id, achievement_id) VALUES (1, 2);

INSERT INTO admins_achievements(admin_id, achievement_id) VALUES (1, 1);
INSERT INTO admins_achievements(admin_id, achievement_id) VALUES (1, 2);

INSERT INTO players_invitations(invitation_id, invited_id) VALUES (1, 2);
INSERT INTO players_invitations(invitation_id, invited_id) VALUES (3, 2);
INSERT INTO players_invitations(invitation_id, invited_id) VALUES (2, 3);

INSERT INTO players_requests(friend_request_id, requested_id) VALUES (1, 2);
INSERT INTO players_requests(friend_request_id, requested_id) VALUES (2, 3);

INSERT INTO players_cards(player_id, card_id) VALUES (1, 1);
INSERT INTO players_cards(player_id, card_id) VALUES (1, 2);
INSERT INTO players_cards(player_id, card_id) VALUES (1, 3);


INSERT INTO players_forums(player_id, forum_id) VALUES (1, 1);
INSERT INTO players_forums(player_id, forum_id) VALUES (1, 2);

INSERT INTO decks_cards(deck_id, card_id) VALUES (1, 1);
INSERT INTO decks_cards(deck_id, card_id) VALUES (1, 2);

INSERT INTO forums_topics(forum_id, topic_id) VALUES (1, 1);

INSERT INTO topics_comments(topic_id, comment_id) VALUES (1, 1);

INSERT INTO games_players(game_id, player_id) VALUES (1, 1);
INSERT INTO games_players(game_id, player_id) VALUES (1, 2);
INSERT INTO games_players(game_id, player_id) VALUES (2, 2);
INSERT INTO games_players(game_id, player_id) VALUES (3, 1);
INSERT INTO games_players(game_id, player_id) VALUES (3, 3);

INSERT INTO players_friends(friend_id, friend_identifier) VALUES (1, 2);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (1, 3);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (2, 1);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (2, 3);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (3, 1);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (3, 2);

