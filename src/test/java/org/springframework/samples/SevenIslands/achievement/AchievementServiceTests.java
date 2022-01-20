package org.springframework.samples.SevenIslands.achievement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(4, count);
    }

    @Test
    public void testFindAll(){
        long count = achievementService.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(4, count);
    }

    @Test
    public void testFindAchievementById(){
        Achievement achievement = achievementService.findAchievementById(1).get();
        assertEquals("Gold_points",achievement.getName());
        assertEquals("Get 300 points.",achievement.getDescription());
    }

    @Test
    public void testFindByPlayerId(){
        Iterable<Achievement> achievements = achievementService.findByPlayerId(1);
        assertEquals(2, achievements.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void shouldInsertAchievement(){
        Achievement achievement = new Achievement();
        achievement.setName("Test Achievement");
        achievement.setDescription("An achievement for testing");
        achievement.setIcon("https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png");
        achievement.setMinValue(20);
        achievement.setAchievementType(ACHIEVEMENT_TYPE.GOLD);
        achievement.setParameter(PARAMETER.POINTS);

        achievementService.save(achievement);

        assertThat(achievement.getId().longValue()).isNotZero();

        assertEquals(achievementService.findAchievementById(achievement.getId()).get(), achievement);
    }

    @Test
    public void shouldDeleteAchievementWithoutPlayers(){

        int countBefore = achievementService.achievementCount();

        Achievement achievement = new Achievement();
        achievement.setName("Test Achievement");
        achievement.setDescription("An achievement for testing");
        achievement.setIcon("https://cdn2.iconfinder.com/data/icons/award-and-reward-3/128/Golden-badges-honor-medals-achievement-512.png");
        achievement.setMinValue(20);
        achievement.setAchievementType(ACHIEVEMENT_TYPE.GOLD);
        achievement.setParameter(PARAMETER.POINTS);

        achievementService.save(achievement);

        achievementService.delete(achievement);

        int countAfter = achievementService.achievementCount();

        assertEquals(countBefore, countAfter);
    }

    @Test
    public void shouldDeleteAchievementWithPlayers(){

        int countBefore = achievementService.achievementCount();

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

        achievementService.delete(achievement);

        int countAfter = achievementService.achievementCount();

        assertEquals(countBefore, countAfter);
    }

}
