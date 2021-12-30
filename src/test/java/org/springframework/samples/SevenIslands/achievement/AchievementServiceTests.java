package org.springframework.samples.SevenIslands.achievement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AchievementServiceTests {
    
    @Autowired
    private AchievementService achievementService;

    @Autowired
    private PlayerService playerService;
    
    @Test
    public void testCountWithInitialData(){
        int count = achievementService.achievementCount();
        assertEquals(count,4);
    }

    @Test
    public void testFindAll(){
        long count = achievementService.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(4, count);
    }

    @Test
    public void testFindAchievementById(){
        Achievement achievement = achievementService.findAchievementById(1).get();
        assertEquals(achievement.getName(), "Gold_points");
        assertEquals(achievement.getDescription(), "Get 300 points.");
    }

    @Test
    public void testFindByPlayerId(){
        Iterable<Achievement> achievements = achievementService.findByPlayerId(1);
        assertEquals(achievements.spliterator().getExactSizeIfKnown(), 2);
    }

    @Test
    public void shouldInserteAchievement(){
        Achievement achievement = new Achievement();
        achievement.setName("Test Achievement");
        achievement.setDescription("An achievement for testing");
        achievement.setIcon("https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png");
        achievement.setMinValue(20);
        achievement.setAchievementType(ACHIEVEMENT_TYPE.GOLD);
        achievement.setParameter(PARAMETER.POINTS);

        achievementService.save(achievement);

        assertThat(achievement.getId().longValue()).isNotEqualTo(0);

        assertEquals(achievementService.findAchievementById(achievement.getId()).get(), achievement);
    }

    @Test
    public void shouldDeleteAchievementWithoutPlayers(){
        Achievement achievement = new Achievement();
        achievement.setName("Test Achievement");
        achievement.setDescription("An achievement for testing");
        achievement.setIcon("https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png");
        achievement.setMinValue(20);
        achievement.setAchievementType(ACHIEVEMENT_TYPE.GOLD);
        achievement.setParameter(PARAMETER.POINTS);

        achievementService.save(achievement);

        int countBefore = achievementService.achievementCount();

        achievementService.delete(achievement);

        int countAfter = achievementService.achievementCount();

        assertNotEquals(countBefore, countAfter);
    }

    @Test
    public void shouldDeleteAchievementWithPlayers(){
        Achievement achievement = new Achievement();
        achievement.setName("Test Achievement");
        achievement.setDescription("An achievement for testing");
        achievement.setIcon("https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png");
        achievement.setMinValue(20);
        achievement.setAchievementType(ACHIEVEMENT_TYPE.GOLD);
        achievement.setParameter(PARAMETER.POINTS);

        Player player = playerService.findPlayerById(1).get();

        List<Player> players = new ArrayList<>();
        players.add(player);

        achievement.setPlayers(players);

        achievementService.save(achievement);

        int countBefore = achievementService.achievementCount();

        achievementService.delete(achievement);

        int countAfter = achievementService.achievementCount();

        assertNotEquals(countBefore, countAfter);
    }



    /*
    @Test
    public void testFindByAdminId(){
        Iterable<Achievement> achievements = achievementService.findByAdminId(1);
        assertEquals(achievements.spliterator().getExactSizeIfKnown(), 2);
    }
    */

}
