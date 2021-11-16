package org.springframework.samples.SevenIslands.game;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface GameRepository extends CrudRepository<Game, Integer>{

@Query("SELECT A from Game A INNER JOIN A.players P WHERE P.id = :playerId")
Collection<Game> findGamesByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

@Modifying
    @Query(value = "insert into games_players (game_id,player_id) VALUES (:game_id,:player_id)", nativeQuery = true)
    @Transactional
    void insertGP(@Param("game_id") int game_id, @Param("player_id") int player_id);
    
// @Query("SELECT g.numberOfPlayers FROM Game g WHERE g.id = :id")
// int findTotalPlayers(int id) throws DataAccessException;

// @Query("SELECT g.number_of_turn FROM Game g WHERE g.id = :id")
// int findTurns(int id) throws DataAccessException;

// @Query("SELECT g.start_time FROM Game g WHERE g.id = :id")
// int findTurns(int id) throws DataAccessException;



}