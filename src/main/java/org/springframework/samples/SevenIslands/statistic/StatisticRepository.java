package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.SevenIslands.player.Player;

public interface StatisticRepository extends CrudRepository<Statistic, String>{
    
    @Query("SELECT P FROM Player P ORDER BY P.totalWins DESC")
	Collection<Player> findPlayersOrderedByWins() throws DataAccessException;

	@Query("SELECT P FROM Player P ORDER BY P.totalPointsAllGames DESC")
	Collection<Player> findPlayersOrderedByPoints() throws DataAccessException;
}
