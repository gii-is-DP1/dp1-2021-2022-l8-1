package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.card.CardService;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticService {

    @Autowired
    public StatisticRepository statisticRepo;  

    @Autowired
    public PlayerService playerService;

    @Autowired
    public GameService gameService;

    @Autowired
    public IslandService islandService;

    @Autowired
    public CardService cardService;

    @Autowired
	public StatisticService(StatisticRepository statisticRepo, PlayerService  playerService, IslandService islandService, CardService cardService, GameService gameService) {
        this.statisticRepo = statisticRepo;
        this.playerService = playerService;
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
        if(islandIds.isEmpty()){
            return null;
        }
        Integer favIslandId = islandIds.iterator().next();
        Optional<Island> opt = islandService.getByIslandId(favIslandId);
        if(opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    // CARD
    @Transactional(readOnly=true)
    public Card getFavoriteCardByPlayerId(Integer playerId) {
        Collection<Integer> cardIds = statisticRepo.findCardIdsByPlayerIdOrderedByCount(playerId);
        if(cardIds.isEmpty()){
            return null;
        }
        Integer favCardId = cardIds.iterator().next();
        Optional<Card> opt = cardService.findCardById(favCardId);
        if(opt.isPresent()) {
            return opt.get();
        }
        return null;
        
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
        return (double) wins / games;
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
    public Double getAvgTimePlayedByPlayerId(Integer playerId) {
        return statisticRepo.findAvgTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMaxTimePlayedByPlayerId(Integer playerId) {
        return statisticRepo.findMaxTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMinTimePlayedByPlayerId(Integer playerId) {
        return statisticRepo.findMinTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public List<Statistic> getStatisticByPlayerId(Integer playerId) {
        return statisticRepo.findByPlayerId(playerId);
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

    @Transactional
    public void setFinalStatistics(List<Player> players, LinkedHashMap<Player, Integer> playersByPunctuation, Game game) {
        for(Player p: players){
            Integer punctuation = playersByPunctuation.get(p);
            List<Statistic> s = this.getStatisticByPlayerId(p.getId());
            Optional<Statistic> opt =  s.stream().filter(x->x.getHad_won()==null).findFirst();
            if(opt.isPresent()){
                Statistic filter = opt.get();
                filter.setHad_won(false);
                filter.setPoints(punctuation);
                if(playersByPunctuation.keySet().iterator().next().equals(p)){
                    filter.setHad_won(true);
                }
                playerService.save(p);
            }
        }
    }

    @Transactional
    public void updateCardCount(Integer statsId, Integer cardId){
       
        int sum = statisticRepo.getCountingCardById(statsId, cardId);
        
        statisticRepo.updateCardCount(statsId,cardId,sum + 1);
    }

    @Transactional
    public boolean existsRow(Integer statsId, Integer cardId){
        Integer a = statisticRepo.findExistRow(statsId, cardId);

        return a==1?true:false;

    }

    
}
