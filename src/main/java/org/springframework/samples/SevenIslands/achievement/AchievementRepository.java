package org.springframework.samples.SevenIslands.achievement;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AchievementRepository extends CrudRepository<Achievement, Integer>{

    @Query("SELECT A FROM Achievement A INNER JOIN A.players P WHERE P.id = :playerId")
	Collection<Achievement> getByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

    @Query("SELECT A FROM Achievement A INNER JOIN A.admins AD WHERE AD.id = :adminId")
	Collection<Achievement> findByAdminId(@Param("adminId") int adminId) throws DataAccessException;
}