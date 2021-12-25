package org.springframework.samples.SevenIslands.statistic;

import java.time.Duration;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StatisticRepository extends CrudRepository<Statistic, String>{

    @Query("SELECT NEW org.springframework.samples.SevenIslands.statistic.PlayerWithStatistics(P, COUNT(S)) FROM Player P JOIN P.statistic S WHERE S.had_won = TRUE GROUP BY P.id ORDER BY COUNT(S) DESC")
	Collection<PlayerWithStatistics> findPlayersOrderedByWins() throws DataAccessException;

	@Query("SELECT NEW org.springframework.samples.SevenIslands.statistic.PlayerWithStatistics(P, SUM(S.points)) FROM Player P JOIN P.statistic S GROUP BY P.id ORDER BY SUM(S.points) DESC")
	Collection<PlayerWithStatistics> findPlayersOrderedByPoints() throws DataAccessException;
    
    // ISLAND
    @Query(value="SELECT IC.island_id FROM Statistics S JOIN Statistic_Islands_Count IC WHERE S.player_id = :playerId GROUP BY IC.island_Id ORDER BY SUM(IC.island_Count) DESC", nativeQuery=true)
    Collection<Integer> findIslandIdsByPlayerIdOrderedByCount(@Param("playerId") int playerId) throws DataAccessException;

    //CARD
    @Query(value="SELECT CC.card_id FROM Statistics S JOIN Statistic_Cards_Count CC WHERE S.player_id = :playerId GROUP BY CC.card_Id ORDER BY SUM(CC.card_Count) DESC", nativeQuery=true)
    Collection<Integer> findCardIdsByPlayerIdOrderedByCount(@Param("playerId") int playerId) throws DataAccessException;

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
    
    @Query("SELECT AVG(G.duration) FROM Game G WHERE G.player.id = :playerId GROUP BY G.player")
    Double findAvgTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MAX(G.duration) FROM Game G WHERE G.player.id = :playerId")
    Integer findMaxTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MIN(G.duration) FROM Game G WHERE G.player.id = :playerId")
    Integer findMinTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT S FROM Statistic S WHERE S.player.id = :playerId and S.game.id = :gameId")
    Statistic findByGameAndPlayerId(@Param("playerId") int playerId, @Param("gameId") int gameId) throws DataAccessException;

    @Modifying
    @Query(value = "INSERT INTO STATISTIC_CARDS_COUNT VALUES (:id,1,:cardId)", nativeQuery = true)
    void insertCardCount(@Param("id") Integer id, @Param("cardId") Integer cardId);

    @Modifying
    @Query(value = "INSERT INTO Statistic_Islands_Count VALUES (:id,:add,:islandId)", nativeQuery = true)
    void initIslandCount(@Param("id") Integer id,@Param("add") Integer add ,@Param("islandId") Integer islandId);
    
    @Query(value = "SELECT island_count FROM Statistic_Islands_Count WHERE statistic_id = :id and island_id = :islandId", nativeQuery = true)
    Integer getCountingIslandById(@Param("id") Integer id, @Param("islandId") Integer islandId) throws DataAccessException;
  
    @Modifying
    @Query(value = "UPDATE statistic_islands_count set island_count = :sum where statistic_id = :id and island_id = :islandId", nativeQuery = true)
    void updateIslandCount(@Param("id") Integer id,@Param("islandId") Integer islandId,@Param("sum") Integer sum);



 
    //     void deactivateUsersNotLoggedInSince(@Param("date") LocalDate date);
    


}
