package org.springframework.samples.SevenIslands.game;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface GameRepository extends CrudRepository<Game, Integer>{

  @Query("SELECT P from Game P WHERE P.privacity = 'PUBLIC'")
  Collection<Game> findAllPublic() throws DataAccessException;

  @Query("SELECT P from Game P WHERE P.privacity = 'PUBLIC' AND P.hasStarted = True")
  Collection<Game> findAllPublicPlaying() throws DataAccessException;

  @Query("SELECT P from Game P WHERE P.privacity = 'PUBLIC' AND P.hasStarted = False")
  Collection<Game> findAllPublicNotPlaying() throws DataAccessException;

  @Query("SELECT P from Game P WHERE P.hasStarted = True")
  Collection<Game> findAllPlaying() throws DataAccessException;

  @Query(value = "SELECT * FROM GAMES WHERE PLAYER_ID LIKE ?1", nativeQuery = true)
  Collection<Game> findByOwnerId(@Param("playerId") int playerId) throws DataAccessException;

  @Query("SELECT g FROM Game g INNER JOIN g.players p WHERE p.id =:id")
  List<Game> findGamesByPlayerId(@Param("id") int id);

  @Query("SELECT COUNT(g) FROM Game g INNER JOIN g.players p WHERE p.id =:id")
  Integer findGamesCountByPlayerId(@Param("id") int id);

  @Query("SELECT P from Game P WHERE P.code = :code")
  Iterable<Game> findGamesByRoomCode(String code) throws DataAccessException;

}