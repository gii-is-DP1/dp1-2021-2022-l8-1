package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.card.CardService;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticService {

    @Autowired
    public StatisticRepository statisticRepo;

    @Autowired
    public PlayerRepository playerRepo;

    @Autowired
    public GameService gameService;

    @Autowired
    public IslandService islandService;

    @Autowired
    public CardService cardService;

    @Autowired
	public StatisticService(StatisticRepository statisticRepo, PlayerRepository playerRepo, IslandService islandService, CardService cardService, GameService gameService) {
        this.statisticRepo = statisticRepo;
        this.playerRepo = playerRepo;
        this.islandService = islandService;
        this.cardService = cardService;
        this.gameService = gameService;
	}

    @Transactional(readOnly = true)
    public Collection<PlayerWithStatistics> getTwentyBestPlayersByWins() {
        Collection<PlayerWithStatistics> players = statisticRepo.findPlayersOrderedByWins();
        
        Collection<PlayerWithStatistics> twentyPlayers = players;
        for(int i=0; i<20; i++) {
            // index not exists
            if(i >= players.size()) {
                twentyPlayers.add(new PlayerWithStatistics());
            }
        }

        return players;
    }

    @Transactional(readOnly = true)
    public Collection<PlayerWithStatistics> getTwentyBestPlayersByPoints() {
        Collection<PlayerWithStatistics> players = statisticRepo.findPlayersOrderedByPoints();
        
        Collection<PlayerWithStatistics> twentyPlayers = players;
        for(int i=0; i<20; i++) {
            // index not exists
            if(i >= players.size()) {
                twentyPlayers.add(new PlayerWithStatistics());
            }
        }

        return players;
    }

    // ISLAND
    @Transactional(readOnly=true)
    public Island getFavoriteIslandByPlayerId(Integer playerId) {
        Collection<Integer> islandIds = statisticRepo.findIslandIdsByPlayerIdOrderedByCount(playerId);
        if(islandIds.size()==0){
            return null;
        }
        Integer favIslandId = islandIds.iterator().next();
        Island favIsland = islandService.getByIslandId(favIslandId).get();
        return favIsland;
    }

    // CARD
    @Transactional(readOnly=true)
    public Card getFavoriteCardByPlayerId(Integer playerId) {
        Collection<Integer> cardIds = statisticRepo.findCardIdsByPlayerIdOrderedByCount(playerId);
        if(cardIds.size()==0){
            return null;
        }
        Integer favCardId = cardIds.iterator().next();
        Card favCard = cardService.findCardById(favCardId).get();
        return favCard;
    }

    // WINS
    @Transactional(readOnly=true)
    public Integer getWinsCountByPlayerId(Integer playerId) {
        return statisticRepo.findWinsCountByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Double getAvgWinsByPlayerId(Integer playerId) {
        Integer games = gameService.findGamesCountByPlayerId(playerId);
        Integer wins = statisticRepo.findWinsCountByPlayerId(playerId);
        Double avgWins = (double) wins / games;
        return avgWins;
    }

    // POINTS
    @Transactional(readOnly=true)
    public Integer getPointsByPlayerId(Integer playerId) {
        return statisticRepo.findPointsByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Double getAvgPointsByPlayerId(Integer playerId) {
        return statisticRepo.findAvgPointsByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMaxPointsByPlayerId(Integer playerId) {
        return statisticRepo.findMaxPointsByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMinPointsByPlayerId(Integer playerId) {
        return statisticRepo.findMinPointsByPlayerId(playerId);
    }

    // GAMES
    @Transactional(readOnly=true)
    public Integer getTimePlayedByPlayerId(Integer playerId) {
        return statisticRepo.findTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Double getAvgPlayedByPlayerId(Integer playerId) {
        return statisticRepo.findAvgTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMaxPlayedByPlayerId(Integer playerId) {
        return statisticRepo.findMaxTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMinPlayedByPlayerId(Integer playerId) {
        return statisticRepo.findMinTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Statistic getStatisticByPlayerAndGameId(Integer playerId, Integer gameId) {
        return statisticRepo.findByGameAndPlayerId(playerId, gameId);
    }

    @Transactional
    public void save(Statistic i){
        statisticRepo.save(i);
    }

    @Transactional
    public void insertCardCount(Integer id, Integer cardId){
        statisticRepo.insertCardCount(id, cardId);
    }

    @Transactional
    public void insertinitIslandCount(Integer id,Integer islandId){
        statisticRepo.initIslandCount(id,0, islandId);
    }

    @Transactional
    public void updateIslandCount(Integer id, Integer islandId){
        
        int sum = statisticRepo.getCountingIslandById(id, islandId);
        
        statisticRepo.updateIslandCount(id,islandId,sum + 1);
    }

    
}
