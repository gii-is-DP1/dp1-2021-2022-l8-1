package org.springframework.samples.SevenIslands.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.board.BoardService;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.card.CardService;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertTrue;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatisticServiceTest {

    public static final int TEST_STATISTIC_ID= 10;
    private static final int TEST_PLAYER_ID = 10;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CardService cardService;

    @Autowired
    private IslandService islandService;

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

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
        assertEquals(CARD_TYPE.DOUBLON,card.getCardType());
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

    @Test
    public void testGetStatisticByPlayerId(){
        List<Statistic> statistics = statisticService.getStatisticByPlayerId(1);
        assertTrue(statistics.get(0).hadWon);
        assertEquals(3, statistics.size());
    }

    @Disabled
    @Test
    public void testInsertCardCount(){
        Integer statisticId = statisticService.getStatisticByPlayerId(1).get(0).getId();
        Statistic statistic = statisticService.getStatisticByPlayerId(1).get(0);
        Integer i = statisticService.getStatisticByPlayerId(1).get(0).getCardCount().entrySet().size();
        statisticService.insertCardCount(statisticId, 23);
        statisticService.save(statistic);
        Statistic statistic2 = statisticService.getStatisticByPlayerId(1).get(0);
        Integer i2 = statisticService.getStatisticByPlayerId(1).get(0).getCardCount().entrySet().size();
        assertEquals(i+1, i2);
    }

    @Disabled
    @Test
    public void testInsertinitIslandCount(){
        Statistic statistic = new Statistic();
        statistic.setPlayer(playerService.findPlayerById(TEST_PLAYER_ID).get());
        statistic.setIslandCount(new HashMap<>());
        statisticService.save(statistic);
        Integer i = statistic.getIslandCount().entrySet().size();
        statisticService.insertinitIslandCount(statistic.getId(),1);
        statisticService.save(statistic);
        Integer i2 = statistic.getIslandCount().entrySet().size();
        assertEquals(i+1, i2);
    }

    @Disabled
    @Test
    public void testUpdateIslandCount(){
        Island island = islandService.getByIslandId(1).get();
        Integer i = statisticService.getStatisticByPlayerId(1).get(0).getIslandCount().get(island);
        statisticService.updateIslandCount(statisticService.getStatisticByPlayerId(1).get(0).getId(), island.getId());
        Integer i2 = statisticService.getStatisticByPlayerId(1).get(0).getIslandCount().get(island);
        assertEquals(i+1, i2);
    } 

    @Disabled
    @Test
    public void testUpdateCardCount(){
        Card card = cardService.findCardById(1).get();
        Integer i = statisticService.getStatisticByPlayerId(1).get(0).getCardCount().get(card);
        statisticService.updateCardCount(statisticService.getStatisticByPlayerId(1).get(0).getId(), card.getId());
        Integer i2 = statisticService.getStatisticByPlayerId(1).get(0).getCardCount().get(card);
        assertEquals(i+1, i2);
    }

    @Test
    public void testSetFinalStatistics(){
        Game game = gameService.findGameById(1).get();
        List<Player> players = game.getPlayers();
        Integer i = 1;
        for(Player p: players){
            Statistic s = new Statistic();
            String iString = i.toString();
            p.setEmail(iString+"email1@gmail");
            p.setFirstName(iString+"firstName");
            p.setSurname(iString+"surname");
            playerService.save(p);
            s.setPlayer(p);
            statisticService.save(s);
            List<Statistic> sts = statisticService.getStatisticByPlayerId(p.getId());
            i++;
        }
        LinkedHashMap<Player, Integer> playersByPunctuation = boardService.calcPlayersByPunctuation(players, players);
        statisticService.setFinalStatistics(players, playersByPunctuation, game);
        for(Player p: players){
            Boolean res = p.getStatistic().stream().noneMatch(s-> s.getPoints()==null);
            assertTrue(res);
        }
    }

    @Test
    public void testExistRow(){
        Boolean res = statisticService.existsRow(1, 1);
        assertTrue(res);;
        Boolean res2 = statisticService.existsRow(1, 2);
        assertTrue(!res2);
    }







    
}
