package org.springframework.samples.SevenIslands.general;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GeneralRepository extends CrudRepository<General, Integer>{

@Query("SELECT g.totalDurationAllGames FROM General g")
int totalDurationAllGames() throws DataAccessException;

@Query("SELECT g.totalGames FROM General g")
int totalGames() throws DataAccessException;

}
