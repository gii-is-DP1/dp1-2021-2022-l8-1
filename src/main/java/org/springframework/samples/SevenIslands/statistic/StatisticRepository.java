package org.springframework.samples.SevenIslands.statistic;

import java.time.Duration;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StatisticRepository extends CrudRepository<Statistic, String>{

    @Query("SELECT NEW org.springframework.samples.SevenIslands.statistic.PlayerWithStatistics(P, COUNT(S)) FROM Player P JOIN P.statistic S WHERE S.had_won = TRUE GROUP BY P.id ORDER BY COUNT(S) DESC")
	Collection<PlayerWithStatistics> findPlayersOrderedByWins() throws DataAccessException;

	@Query("SELECT NEW org.springframework.samples.SevenIslands.statistic.PlayerWithStatistics(P, SUM(S.points)) FROM Player P JOIN P.statistic S GROUP BY P.id ORDER BY SUM(S.points) DESC")
	Collection<PlayerWithStatistics> findPlayersOrderedByPoints() throws DataAccessException;
    

    //  WINS (SUM, AVG)
    @Query("SELECT COUNT(S) FROM Statistic S WHERE S.player.id = :playerId AND S.had_won = TRUE")
    Integer findWinsCountByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    //  POINTS (SUM, AVG, MAX, MIN)
    @Query("SELECT SUM(S.points) FROM Statistic S WHERE S.player.id = :playerId")
    Integer findPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT AVG(S.points) FROM Statistic S WHERE S.player.id = :playerId GROUP BY S.player")
    Double findAvgPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MAX(S.points) FROM Statistic S WHERE S.player.id = :playerId GROUP BY S.player")
    Integer findMaxPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MIN(S.points) FROM Statistic S WHERE S.player.id = :playerId GROUP BY S.player")
    Integer findMinPointsByPlayerId(@Param("playerId") int playerId) throws DataAccessException;


    //  GAME (TIME, TIME-AVG, TIME-MAX, TIME-MIN)
    @Query("SELECT SUM(G.duration) FROM Game G WHERE G.player.id = :playerId")
    Integer findTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
    
    @Query("SELECT AVG(G.duration) FROM Game G WHERE G.player.id = :playerId GROUP BY G.id")
    Integer findAvgTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MAX(G.duration) FROM Game G WHERE G.player.id = :playerId GROUP BY G.id")
    Integer findMaxTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MIN(G.duration) FROM Game G WHERE G.player.id = :playerId GROUP BY G.id")
    Integer findMinTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

}
