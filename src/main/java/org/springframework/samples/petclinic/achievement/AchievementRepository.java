package org.springframework.samples.petclinic.achievement;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AchievementRepository extends CrudRepository<Achievement, Integer>{

    @Query("SELECT * FROM Achievements a JOIN Players_Achievements pa JOIN Players p WHERE a.id = pa.achievement_Id AND p.id = pa.player_Id")
	Collection<Achievement> findByPlayerId(@Param("playerId")int playerId);
}