package org.springframework.samples.SevenIslands.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.statistic.Statistic;
import org.springframework.samples.SevenIslands.user.Authorities;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserBuilder;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatisticServiceTest {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private PlayerService playerService;

    @Test
    public void testGetTwentyBestPlayersByWins(){
        List<PlayerWithStatistics> listPlayers = statisticService.getTwentyBestPlayersByWins().stream().collect(Collectors.toList());
        List<Integer> countWins = listPlayers.stream().map(x-> statisticService.getWinsCountByPlayerId(x.getId())).collect(Collectors.toList());
        List<Integer> countWinsCopy = new ArrayList<>(countWins);
        Collections.reverse(countWins);
        List<Integer> countWinsSorted = countWinsCopy.stream().sorted().collect(Collectors.toList()); 
        assertTrue(listPlayers.size()<=20);
        assertEquals(countWinsSorted, countWins);
    }

    @Test
    public void testGetTwentyBestPlayersByPoints(){
        List<PlayerWithStatistics> listPlayers = statisticService.getTwentyBestPlayersByPoints().stream().collect(Collectors.toList());
        List<Integer> points = listPlayers.stream().map(x-> statisticService.getPointsByPlayerId(x.getId())).filter(x-> x!=null).collect(Collectors.toList());
        List<Integer> pointsCopy = new ArrayList<>(points);
        Collections.reverse(points);
        List<Integer> pointsSorted = pointsCopy.stream().sorted().collect(Collectors.toList()); 
        assertTrue(listPlayers.size()<=20);
        assertEquals(pointsSorted, points);

    }

    @Test
    public void testGetFavoriteIslandByPlayerId(){
        Island island = statisticService.getFavoriteIslandByPlayerId(1);
        Integer favIslandIdForPlayer1 = 5;
        assertEquals(island.getIslandNum(), favIslandIdForPlayer1);
    }

    @Test
    public void testGetFavoriteCardByPlayerId(){
        Card card = statisticService.getFavoriteCardByPlayerId(1);
        assertEquals(card.getCardType(), CARD_TYPE.DOUBLON);
    }

    @Test
    public void testGetAvgWinsByPlayerId(){
        Double avg = statisticService.getAvgWinsByPlayerId(1);
        Double shouldBeAverage = (double) 1/ 5;
        assertEquals(avg, shouldBeAverage);
    }

    @Test
    public void testAvgPointsByPlayerId(){
        Double avg = statisticService.getAvgPointsByPlayerId(1);
        Double shouldBeAverage = (double) (70+40+50)/3;
        assertEquals(avg, shouldBeAverage);
    }

    @Test
    public void testGetMaxPointsByPlayerId(){
        Integer max = statisticService.getMaxPointsByPlayerId(1);
        Integer shouldBeMax = 70;
        assertEquals(max, shouldBeMax);
    }

    @Test
    public void testGetMinPointsByPlayerId(){
        Integer min = statisticService.getMinPointsByPlayerId(1);
        Integer shouldBeMin = 40;
        assertEquals(min, shouldBeMin);
    }

    @Test
    public void testGetTimePlayedByPlayerId(){
        Integer timePlayed = statisticService.getTimePlayedByPlayerId(1);
        Integer shouldBeTimePlayed = 230+100+20+300+300;
        assertEquals(timePlayed, shouldBeTimePlayed);
    }

    @Test
    public void testGetAvgTimePlayedByPlayerId(){
        Double avgPlayed = statisticService.getAvgTimePlayedByPlayerId(1);
        Double shouldBeAvgPlayed = (double) (230+100+20+300+300)/5;
        assertEquals(avgPlayed, shouldBeAvgPlayed);
    }

    @Test
    public void testGetMaxTimePlayedByPlayerId(){
        Integer max = statisticService.getMaxTimePlayedByPlayerId(1);
        Integer shouldBeMax = 300;
        assertEquals(max, shouldBeMax);
    }

    @Test
    public void testGetMinTimePlayedByPlayerId(){
        Integer min = statisticService.getMinTimePlayedByPlayerId(1);
        Integer shouldBeMin = 20;
        assertEquals(min, shouldBeMin);
    }





    
}
