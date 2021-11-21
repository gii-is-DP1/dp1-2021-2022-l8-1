-- -- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- -- -- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- -- -- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('user1','user1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'user1','player');

INSERT INTO users(username,password,enabled) VALUES ('ISMP15','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'ISMP15','admin');

INSERT INTO users(username,password,enabled) VALUES ('test1','test1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'test1','player');
INSERT INTO users(username,password,enabled) VALUES ('test2','test2',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'test2','player');
INSERT INTO users(username,password,enabled) VALUES ('test3','test3',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'test3','player');

INSERT INTO players(id, first_name, surname, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
    VALUES (1,'Paco', 'Alonso', 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 6, 'BARREL_OF_RUM', 79, 56, 'test1');

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games, username) 
    VALUES (2, 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 2, 1220, 220.0, 720, 540, 121, 24.2, 3, 'BARREL_OF_RUM', 59, 26, 'test2');

 INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games,username) 
     VALUES (3, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47,'test3');

-- INSERT INTO players(id, username, first_name, surname, email, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games) 
  --   VALUES (3, 'user1','Manolo', 'Quintana', 'manolito@gmail.com', 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47);



INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points, player_id, privacity,has_started) 
VALUES ('Prueba0', 'ABCD124', '5,8,2', 3, 7, 5 ,34,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','3,4,7', 1, 'PUBLIC', false);

 INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points,player_id, privacity,has_started) 
 VALUES ('Prueba1', 'ABCD123', '1,8,3', 3, 4, 2 ,27,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','2,4,9',1, 'PUBLIC', false);

 INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points,player_id, privacity,has_started) 
 VALUES ('Prueba2', 'ABCD122', '4,1,3', 3, 2, 1 ,29,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','3,2,7',1, 'PRIVATE', false);

 INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points, player_id, privacity,has_started, start_time) 
 VALUES ('Prueba3', 'ABCD121', '1,4,2', 3, 3, 2 ,37,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','2,2,1', 1, 'PUBLIC', true, '2021-11-18 23:00:00');

 INSERT INTO games(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points,player_id, privacity,has_started, start_time) 
 VALUES ('Prueba4', 'ABCD120', '3,2,1', 3, 1, 1 ,49,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','1,3,9', 2, 'PRIVATE', true, '2021-11-18 23:01:00');
--AQUI HAY QUE AÑADIR ATRIBUTOS NUEVOS METIDOS

-- /*INSERT INTO person(first_Name,surname,password,user_Name,email) VALUES ('Pablo', 'Rivera','@Pablo1','pabrivjim','pabrivjim@alum.us.es');*/

INSERT INTO boards(id,background,height,width) VALUES (1,'resources/images/board.jpg',800,800);


INSERT INTO cards(id,card_type) VALUES (1, 'DOUBLON');
INSERT INTO cards(id,card_type) VALUES (2, 'GOLDEN_CUP');
INSERT INTO cards(id,card_type) VALUES (3, 'RUBY');
INSERT INTO cards(id,card_type) VALUES (4, 'DIAMOND');

 INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
     VALUES (1, 'Gold_points', 'Get 300 points.', 'https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png', 300, 'GOLD', 'POINTS');
 INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
     VALUES (2, 'Silver_loses', 'Loses 200 times.', 'https://www.pngall.com/wp-content/uploads/2017/03/Silver-Medal-PNG-HD.png', 200, 'SILVER', 'LOSES');
 INSERT INTO achievements(id, name, description, icon, min_value, achievement_type , parameter)
     VALUES (3, 'Gold_loses', 'Loses 300 times.', 'https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png', 300, 'GOLD', 'LOSES');



 INSERT INTO cells(id, x_position, y_position, card) VALUES (1,'0','0','DOUBLON');

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
INSERT INTO games_players(game_id, player_id) VALUES (4, 1);
INSERT INTO games_players(game_id, player_id) VALUES (4, 2);
INSERT INTO games_players(game_id, player_id) VALUES (5, 1);

INSERT INTO players_friends(friend_id, friend_identifier) VALUES (1, 2);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (1, 3);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (2, 1);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (2, 3);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (3, 1);
INSERT INTO players_friends(friend_id, friend_identifier) VALUES (3, 2);

