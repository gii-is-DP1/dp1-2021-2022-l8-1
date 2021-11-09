package org.springframework.samples.petclinic.achievement;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AchievementRepository extends CrudRepository<Achievement, Integer>{

    @Query("SELECT A FROM Achievement A JOIN Players_Achievements PA ON A.id = PA.achievement_Id JOIN Player P ON P.id = PA.player_Id WHERE P.id = :playerId")
	Collection<Achievement> findByPlayerId(int playerId) throws DataAccessException;
}