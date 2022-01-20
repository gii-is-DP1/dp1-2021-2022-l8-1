package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StatisticRepository extends CrudRepository<Statistic, String>{

    @Query("SELECT NEW org.springframework.samples.SevenIslands.statistic.PlayerWithStatistics(P, COUNT(S)) FROM Player P JOIN P.statistic S WHERE S.hadWon = TRUE GROUP BY P.id ORDER BY COUNT(S) DESC")
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
    @Query("SELECT COUNT(S) FROM Statistic S WHERE S.player.id = :playerId AND S.hadWon = TRUE")
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
    @Query("SELECT SUM(G.duration) FROM Game G INNER JOIN G.players p WHERE p.id =:playerId")
    Integer findTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
    
    @Query("SELECT AVG(G.duration) FROM Game G INNER JOIN G.players p WHERE p.id =:playerId")
    Double findAvgTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MAX(G.duration) FROM Game G INNER JOIN G.players p WHERE p.id =:playerId")
    Integer findMaxTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT MIN(G.duration) FROM Game G INNER JOIN G.players p WHERE p.id =:playerId")
    Integer findMinTimePlayedByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT S FROM Statistic S WHERE S.player.id = :playerId")
    List<Statistic> findByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    //INSERT CARD IF THE TABLE HAS NOT THE ROW
    @Modifying
    @Query(value = "INSERT INTO STATISTIC_CARDS_COUNT VALUES (:id,1,:cardId)", nativeQuery = true)
    void insertCardCount(@Param("id") Integer id, @Param("cardId") Integer cardId);

    //INSERT INSLAND INIT
    @Modifying
    @Query(value = "INSERT INTO Statistic_Islands_Count VALUES (:id,:add,:islandId)", nativeQuery = true)
    void initIslandCount(@Param("id") Integer id,@Param("add") Integer add ,@Param("islandId") Integer islandId);
    
    //ISLAND COUNT AND UPDATE
    @Query(value = "SELECT island_count FROM Statistic_Islands_Count WHERE statistic_id = :id and island_id = :islandId", nativeQuery = true)
    Integer getCountingIslandById(@Param("id") Integer id, @Param("islandId") Integer islandId) throws DataAccessException;
  
    @Modifying
    @Query(value = "UPDATE statistic_islands_count set island_count = :sum where statistic_id = :id and island_id = :islandId", nativeQuery = true)
    void updateIslandCount(@Param("id") Integer id,@Param("islandId") Integer islandId,@Param("sum") Integer sum);
    
    
    //CARDS COUNTS AND UPDATE
    @Query(value = "SELECT card_count FROM Statistic_Cards_Count WHERE statistic_id = :id and card_id = :cardId", nativeQuery = true)
    Integer getCountingCardById(@Param("id") Integer id, @Param("cardId") Integer cardId) throws DataAccessException;

    @Modifying
    @Query(value = "UPDATE Statistic_Cards_Count set card_count = :sum where statistic_id = :id and card_id = :cardId", nativeQuery = true)
    void updateCardCount(@Param("id") Integer id,@Param("cardId") Integer card_id,@Param("sum") Integer sum);

    @Query(value = "SELECT Count(*) FROM Statistic_Cards_Count C WHERE C.statistic_id = :stats_id AND C.card_id = :cardId", nativeQuery = true)
    Integer findExistRow(@Param("stats_id") Integer stats_id, @Param("cardId") Integer cardId);  

}
