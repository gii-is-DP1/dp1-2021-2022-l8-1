package org.springframework.samples.SevenIslands.game;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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