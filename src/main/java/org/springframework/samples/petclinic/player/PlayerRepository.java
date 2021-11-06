package org.springframework.samples.petclinic.player;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.achievement.Achievement;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Modifying
    void addAchievement(String name) throws DataAccessException;

    List<Achievement> findAchievements() throws DataAccessException;

}
