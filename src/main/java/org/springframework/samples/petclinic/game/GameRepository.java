package org.springframework.samples.petclinic.game;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface GameRepository extends CrudRepository<Game, Integer>{

    @Query("SELECT A from Game A INNER JOIN A.players P WHERE P.id = :playerId")
    Collection<Game> findGameByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
    
// @Query("SELECT g.numberOfPlayers FROM Game g WHERE g.id = :id")
// int findTotalPlayers(int id) throws DataAccessException;

// @Query("SELECT g.number_of_turn FROM Game g WHERE g.id = :id")
// int findTurns(int id) throws DataAccessException;

// @Query("SELECT g.start_time FROM Game g WHERE g.id = :id")
// int findTurns(int id) throws DataAccessException;



}