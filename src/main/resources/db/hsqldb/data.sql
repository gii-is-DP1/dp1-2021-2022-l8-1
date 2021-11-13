-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO game(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points) 
VALUES ('Prueba', 'ABCD124', '5,8,2', 3, 7, 5 ,34,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','3,4,7');

INSERT INTO game(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points) 
VALUES ('Prueba', 'ABCD123', '1,8,3', 3, 4, 2 ,27,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','2,4,9');

INSERT INTO game(name,code,name_of_players, number_of_players, number_of_turn, actual_player,remains_cards, deck, points) 
VALUES ('Prueba', 'ABCD122', '4,1,3', 3, 2, 1 ,29,'DOUBLON, GOLDEN_CUP,RUBY, CROWN,STIR, SWORD,DIAMOND','3,2,7');
-- AQUI HAY QUE AÑADIR ATRIBUTOS NUEVOS METIDOS

/*INSERT INTO person(first_Name,surname,password,user_Name,email) VALUES ('Pablo', 'Rivera','@Pablo1','pabrivjim','pabrivjim@alum.us.es');*/

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

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games) 
    VALUES (1, 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 6, 'BARREL_OF_RUM', 79, 56);

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games) 
    VALUES (2, 'https://st2.depositphotos.com/1009634/7235/v/600/depositphotos_72350117-stock-illustration-no-user-profile-picture-hand.jpg', 2, 1220, 220.0, 720, 540, 121, 24.2, 3, 'BARREL_OF_RUM', 59, 26);

INSERT INTO players(id, profile_photo, total_games, total_time_games, avg_time_games, max_time_game, min_time_game, total_points_all_games, avg_total_points, favorite_island, favorite_treasure, max_points_of_games, min_points_of_games) 
    VALUES (3, 'https://www.tuexperto.com/wp-content/uploads/2015/07/perfil_01.jpg', 5, 3600, 720.0, 800, 610, 321, 64.2, 2, 'STIR', 81, 47);

INSERT INTO boards(id) VALUES(1);

INSERT INTO forums(id, name, description) VALUES(1, 'Players', 'Forum about players');
INSERT INTO forums(id, name, description) VALUES(2, 'Games', 'Forum about games');

INSERT INTO topics(id, name, description) VALUES(1, 'Rival players', 'do you want to be my rival?');
INSERT INTO generals(id, total_games, total_duration_all_games) VALUES (1, 200, 300);

INSERT INTO deck(id) VALUES (1);

INSERT INTO admins(id, first_name, surname, password, user_name, email) VALUES (1, 'Ismael', 'Perez', '123123123', 'ismperort', 'ismperort@alum.us.es');

INSERT INTO comments(id, message) VALUES (1, 'I agree');


INSERT INTO players_achievements(player_id, achievement_id) VALUES (1, 1);
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
INSERT INTO games_players(game_id, player_id) VALUES (2, 1);
INSERT INTO games_players(game_id, player_id) VALUES (2, 2);

