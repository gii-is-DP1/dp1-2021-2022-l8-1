package org.springframework.samples.petclinic.player;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
public interface PlayerRepository extends CrudRepository<Player, Integer>{



  @Query("SELECT A FROM Player A INNER JOIN A.games P WHERE P.id = :gameId")
	Collection<Player> findByGameId(@Param("gameId") int gameId) throws DataAccessException;


  @Query("SELECT A FROM Player A INNER JOIN A.invitations P WHERE P.id = :playerId")
	Collection<Player> findInvitationsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

  @Query("SELECT A FROM Player A INNER JOIN A.friend_requests P WHERE P.id = :playerId")
	Collection<Player> findRequestsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

}