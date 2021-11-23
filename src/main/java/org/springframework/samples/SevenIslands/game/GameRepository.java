package org.springframework.samples.SevenIslands.game;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface GameRepository extends CrudRepository<Game, Integer>{


  @Query("SELECT P from Game P WHERE P.privacity = 'PUBLIC'")
  Collection<Game> findAllPublic() throws DataAccessException;

  @Query("SELECT P from Game P WHERE P.privacity = 'PUBLIC' AND P.has_started = True")
  Collection<Game> findAllPublicPlaying() throws DataAccessException;

  @Query("SELECT P from Game P WHERE P.has_started = True")
  Collection<Game> findAllPlaying() throws DataAccessException;

  @Query(value = "SELECT * FROM GAMES WHERE PLAYER_ID LIKE ?1", nativeQuery = true)
  Collection<Game> findGamesByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

  @Query("SELECT g FROM Game g INNER JOIN g.players p WHERE p.id =:id")
  List<Game> findGamesWhereIPlayedByPlayerId(@Param("id") int id);

  @Query(value = "SELECT PLAYER_ID FROM GAMES_PLAYERS WHERE GAME_ID LIKE ?1", nativeQuery = true)
  Collection<Integer> findIdPlayersByGameId(int id);

  @Query("SELECT P from Game P WHERE P.code = :code")
  Collection<Game> findGamesByRoomCode(String code) throws DataAccessException;

}