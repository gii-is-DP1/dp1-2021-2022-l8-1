INSERT INTO users(username,password,enabled) VALUES ('ISMP15','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'ISMP15','admin');

INSERT INTO users(username,password,enabled) VALUES ('test1','test1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'test1','player');

INSERT INTO users(username,password,enabled) VALUES ('test2','test2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'test2','player');

INSERT INTO users(username,password,enabled) VALUES ('test3','test3',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'test3','player');

INSERT INTO users(username,password,enabled) VALUES ('test4','test4',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'test4','player');

INSERT INTO users(username,password,enabled) VALUES ('test5','test5',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'test5','player');

INSERT INTO users(username,password,enabled) VALUES ('test6','test6',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'test6','player');

INSERT INTO users(username,password,enabled) VALUES ('test7','test7',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (11,'test7','player');

INSERT INTO users(username,password,enabled) VALUES ('test8','test8',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'test8','player');

INSERT INTO users(username,password,enabled) VALUES ('test9','test9',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (13,'test9','player');

INSERT INTO users(username,password,enabled) VALUES ('test10','test10',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (14,'test10','player');

INSERT INTO users(username,password,enabled) VALUES ('test11','test11',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (15,'test11','player');
-- DECKS
INSERT INTO deck(name) VALUES ('Mazo');
-- INSERT INTO deck(id) VALUES (2);
-- INSERT INTO deck(id) VALUES (3);
-- INSERT INTO deck(id) VALUES (4);
-- INSERT INTO deck(id) VALUES (5);
-- INSERT INTO deck(id) VALUES (6);

-- CARDS
INSERT INTO cards(id,card_type) VALUES (1, 'DOUBLON');
INSERT INTO cards(id,card_type) VALUES (2, 'GOLDEN_CUP');
INSERT INTO cards(id,card_type) VALUES (3, 'RUBY');
INSERT INTO cards(id,card_type) VALUES (4, 'DIAMOND');
INSERT INTO cards(id,card_type) VALUES (5, 'DOUBLON');
INSERT INTO cards(id,card_type) VALUES (6, 'GOLDEN_CUP');
INSERT INTO cards(id,card_type) VALUES (7, 'RUBY');
INSERT INTO cards(id,card_type) VALUES (8, 'DIAMOND');
INSERT INTO cards(id,card_type) VALUES (9, 'DOUBLON');
INSERT INTO cards(id,card_type) VALUES (10, 'GOLDEN_CUP');

-- DECKS-CARDS
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (1, 1);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (1, 2);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (2, 1);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (2, 2);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (3, 1);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (3, 2);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (4, 3);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (4, 4);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (5, 5);
-- INSERT INTO decks_cards(deck_id, card_id) VALUES (5, 1);

-- PLAYERS
INSERT INTO players(id, first_name, surname, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
    VALUES (1,'Paco', 'Alonso', 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 6, 'BARREL_OF_RUM', 79, 56, 'test1');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games, username) 
    VALUES (2, 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 2, 1220, 220.0, 720, 540, 121, 24.2, 3, 'BARREL_OF_RUM', 59, 26, 'test2');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
    VALUES (3, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test3');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (4, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test4');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (5, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test5');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (6, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test6');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (7, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test7');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (8, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test8');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (9, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test9');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (10, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test10');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
VALUES (11, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test11');
-- GAMES
INSERT INTO games(name,code, number_of_turn, actual_player, player_id, deck_id, privacity,has_started,start_time) 
VALUES ('Prueba0', 'ABCD124', 7, 5, 1, 1,'PUBLIC', false, '2021-11-18 23:00:00');

 INSERT INTO games(name,code, number_of_turn, actual_player,player_id, deck_id, privacity,has_started,start_time) 
 VALUES ('Prueba1', 'ABCD123', 4, 2,1, 1,'PUBLIC', false, '2021-11-18 23:00:00');

 INSERT INTO games(name,code, number_of_turn, actual_player,player_id, deck_id, privacity,has_started,start_time) 
 VALUES ('Prueba2', 'ABCD122', 2, 1,1, 1,'PRIVATE', false, '2021-11-18 23:00:00');

 INSERT INTO games(name,code, number_of_turn, actual_player, player_id, deck_id, privacity,has_started, start_time) 
 VALUES ('Prueba3', 'ABCD121', 3, 2, 1, 1,'PUBLIC', true, '2021-11-18 23:00:00');

 INSERT INTO games(name,code, number_of_turn, actual_player,player_id, deck_id, privacity,has_started, start_time) 
 VALUES ('Prueba4', 'ABCD120', 1, 1, 2, 1,'PRIVATE', true, '2021-11-18 23:01:00');
-- TODO: AQUI HAY QUE QUITAR ATRIBUTOS NUEVOS METIDOS

-- BOARDS
INSERT INTO boards(id,background,height,width) VALUES (1,'/resources/images/board.jpg',644,527);

-- ACHIEVEMENTS
INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
    VALUES (1, 'Gold_points', 'Get 300 points.', 'https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png', 300, 'GOLD', 'POINTS');
INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
    VALUES (2, 'Silver_loses', 'Loses 200 times.', 'https://www.pngall.com/wp-content/uploads/2017/03/Silver-Medal-PNG-HD.png', 200, 'SILVER', 'LOSES');
INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
    VALUES (3, 'Gold_loses', 'Loses 300 times.', 'https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png', 300, 'GOLD', 'LOSES');

-- CELLS
INSERT INTO cells(id, x_position, y_position, card) VALUES (1,'0','0','DOUBLON');
INSERT INTO cells(id, x_position, y_position, card) VALUES (2,'0','0','CROWN');

-- ISLANDS
INSERT INTO islands(id,island_num) VALUES (1,1);
INSERT INTO islands(id,island_num) VALUES (2,2);
INSERT INTO islands(id,island_num) VALUES (3,3);
INSERT INTO islands(id,island_num) VALUES (4,4);
INSERT INTO islands(id,island_num) VALUES (5,5);
INSERT INTO islands(id,island_num, card_id) VALUES (6,6, 1);

-- FORUMS
INSERT INTO forums(id, name, description) VALUES(1, 'Players', 'Forum about players');
INSERT INTO forums(id, name, description) VALUES(2, 'Games', 'Forum about games');

-- TOPICS
INSERT INTO topics(id, name, description) VALUES(1, 'Rival players', 'do you want to be my rival?');

-- GENERALS
INSERT INTO generals(id, total_games, total_duration_all_games) VALUES (1, 200, 300);

-- ADMINS
INSERT INTO admins(first_name, surname, email, username) VALUES ('Ismael', 'Perez', 'ismperort@alum.us.es', 'ISMP15');

-- COMMENTS
INSERT INTO comments(id, message) VALUES (1, 'I agree');

-- PLAYERS-ACHIEVEMENTS
INSERT INTO players_achievements(player_id, achievement_id) VALUES (1, 1); --ESTA TABLA ME DA LOS LOGROS QUE TENGO COMPLETADOS
INSERT INTO players_achievements(player_id, achievement_id) VALUES (1, 2);
INSERT INTO players_achievements(player_id, achievement_id) VALUES (2, 1);
INSERT INTO players_achievements(player_id, achievement_id) VALUES (3, 2);

-- -- PLAYERS-CARDS
-- INSERT INTO players_cards(player_id, card_id) VALUES (1, 1);
-- INSERT INTO players_cards(player_id, card_id) VALUES (1, 2);
-- INSERT INTO players_cards(player_id, card_id) VALUES (1, 3);

-- PLAYERS-FORUMS
INSERT INTO players_forums(player_id, forum_id) VALUES (1, 1);
INSERT INTO players_forums(player_id, forum_id) VALUES (1, 2);

-- FORUMS-TOPICS
INSERT INTO forums_topics(forum_id, topic_id) VALUES (1, 1);

-- TOPICS-COMMENTS
INSERT INTO topics_comments(topic_id, comment_id) VALUES (1, 1);

-- GAMES-PLAYERS
INSERT INTO games_players(game_id, player_id) VALUES (1, 1);
INSERT INTO games_players(game_id, player_id) VALUES (1, 2);
INSERT INTO games_players(game_id, player_id) VALUES (2, 2);
INSERT INTO games_players(game_id, player_id) VALUES (2, 3);
INSERT INTO games_players(game_id, player_id) VALUES (2, 1);
INSERT INTO games_players(game_id, player_id) VALUES (3, 1);
INSERT INTO games_players(game_id, player_id) VALUES (3, 3);
INSERT INTO games_players(game_id, player_id) VALUES (4, 1);
INSERT INTO games_players(game_id, player_id) VALUES (4, 2);
INSERT INTO games_players(game_id, player_id) VALUES (5, 1);
INSERT INTO games_players(game_id, player_id) VALUES (5, 2);



-- -- PLAYERS-INVITATIONS
-- INSERT INTO players_invitations(invitation_id, invited_id) VALUES (1, 2);
-- INSERT INTO players_invitations(invitation_id, invited_id) VALUES (3, 2);
-- INSERT INTO players_invitations(invitation_id, invited_id) VALUES (2, 3);

-- -- PLAYERS-REQUESTS
-- INSERT INTO players_requests(friend_request_id, requested_id) VALUES (1, 2);
-- INSERT INTO players_requests(friend_request_id, requested_id) VALUES (2, 3);

-- -- PLAYERS-FRIENDS
-- INSERT INTO players_friends(friend_id, friend_identifier) VALUES (1, 2);
-- INSERT INTO players_friends(friend_id, friend_identifier) VALUES (1, 3);
-- INSERT INTO players_friends(friend_id, friend_identifier) VALUES (2, 1);
-- INSERT INTO players_friends(friend_id, friend_identifier) VALUES (2, 3);
-- INSERT INTO players_friends(friend_id, friend_identifier) VALUES (3, 1);
-- INSERT INTO players_friends(friend_id, friend_identifier) VALUES (3, 2);