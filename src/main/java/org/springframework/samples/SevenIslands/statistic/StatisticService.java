package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.card.CardService;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerRepository;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticService {

    @Autowired
    public StatisticRepository statisticRepo;  // TODO: change to service

    @Autowired
    public PlayerRepository playerRepo; // TODO: change to service

    @Autowired
    public PlayerService playerService;

    @Autowired
    public GameService gameService;

    @Autowired
    public IslandService islandService;

    @Autowired
    public CardService cardService;

    @Autowired
	public StatisticService(StatisticRepository statisticRepo, PlayerRepository playerRepo, PlayerService playerService, IslandService islandService, CardService cardService, GameService gameService) {
        this.statisticRepo = statisticRepo;
        this.playerRepo = playerRepo;
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
        Map<CARD_TYPE, Integer> map = new HashMap<>();
        for(Integer id: cardIds){
            CARD_TYPE cardType = cardService.findCardById(id).get().getCardType();
            if(!map.entrySet().contains(cardType)){
                map.put(cardType, 1);
            }else{
                Integer count = map.get(cardType);
                map.put(cardType, count+1);
            }

        }

        LinkedHashMap<CARD_TYPE, Integer> cardsByPreference = new LinkedHashMap<>();
        // Sort cardsByPreference
         map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue((e1,e2) -> e2.compareTo(e1)))
            .forEachOrdered(x -> cardsByPreference.put(x.getKey(), x.getValue()));



        CARD_TYPE favCardType = cardsByPreference.keySet().iterator().next();
        Iterable<Card> cardIt = cardService.findAll();
        Card favCard = StreamSupport.stream(cardIt.spliterator(), false).filter(x-> x.getCardType().equals(favCardType)).findFirst().get();
        //Card favCard = cardService.findCardById(favCardId).get();
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

    public void setFinalStatistics(List<Player> players, LinkedHashMap<Player, Integer> playersByPunctuation, Game game) {
        
        for(Player p: players){
            Integer punctuation = playersByPunctuation.get(p);
            List<Statistic> s = this.getStatisticByPlayerId(p.getId());

            Optional<Statistic> statisticOp = s.stream().filter(x->x.getHad_won()==null).findFirst();

            if(statisticOp.isPresent()){
                Statistic filter = statisticOp.get();
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
        if(a == 1){
            return true;
        }else{
            return false;
        }
    }

    
}
