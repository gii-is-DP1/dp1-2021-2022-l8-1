package org.springframework.samples.petclinic.player;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
public interface PlayerRepository extends CrudRepository<Player, Integer>{


    @Query("SELECT A FROM Player A INNER JOIN A.games P WHERE P.id = :gameId")
	Collection<Player> findByGameId(@Param("gameId") int gameId) throws DataAccessException;



}