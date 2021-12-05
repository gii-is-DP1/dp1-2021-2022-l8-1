package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.SevenIslands.player.Player;

public interface StatisticRepository extends CrudRepository<Statistic, String>{


    @Query("SELECT P FROM Player P ORDER BY P.totalWins DESC")
	Collection<Player> findPlayersOrderedByWins() throws DataAccessException;

	@Query("SELECT P FROM Player P ORDER BY P.totalPointsAllGames DESC")
	Collection<Player> findPlayersOrderedByPoints() throws DataAccessException;


    //  WINS (SUM, AVG)
    @Query("SELECT SUM(S.wins) FROM Statistic S WHERE S.id = :playerId")
    Integer findWinsCountByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT AVG(S.wins) FROM Statistic S WHERE S.id = :playerId")
    Integer findWinsAvgByPlayerId(@Param("playerId") int playerId) throws DataAccessException;


    //  POINTS (SUM, AVG, MAX, MIN)
    @Query("SELECT SUM(S.points) FROM Statistic S WHERE S.id = :playerId")
    Integer findPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT AVG(S.points) FROM Statistic S WHERE S.id = :playerId")
    Double findAvgPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MAX(S.points) FROM Statistic S WHERE S.id = :playerId")
    Integer findMaxPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
    
    @Query("SELECT MIN(S.points) FROM Statistic S WHERE S.id = :playerId")
    Integer findMinPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
  

    //  GAME (COUNT, TIME-AVG, TIME-MAX, TIME-MIN)
    @Query("SELECT COUNT(G) FROM Games G WHERE G.player.statistic.id = :playerId")
    Integer findGamesCountByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT AVG(G) FROM Games G WHERE G.player.statistic.id = playerId GROUP BY G.duration")
    Double findAvgGameTimeByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MAX(G) FROM Games G WHERE G.player.statistic.id = playerId GROUP BY G.duration")
    Integer findMaxGameTimeByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
    
    @Query("SELECT MIN(G) FROM Games G WHERE G.player.statistic.id = playerId GROUP BY G.duration")
    Integer findMinGameTimeByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
    
}
