package org.springframework.samples.SevenIslands.statistic;

import java.time.Duration;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.game.GameRepository;
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
    public GameRepository gameRepo;

    @Autowired
	public StatisticService(StatisticRepository statisticRepo, PlayerRepository playerRepo) {
        this.statisticRepo = statisticRepo;
        this.playerRepo = playerRepo;
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

    // WINS
    @Transactional(readOnly=true)
    public Integer getWinsCountByPlayerId(Integer playerId) {
        return statisticRepo.findWinsCountByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Double getAvgWinsByPlayerId(Integer playerId) {
        Integer games = gameRepo.findGamesCountByPlayerId(playerId);
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
    public Integer getAvgPlayedByPlayerId(Integer playerId) {
        return statisticRepo.findMinTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMaxPlayedByPlayerId(Integer playerId) {
        return statisticRepo.findMaxTimePlayedByPlayerId(playerId);
    }

    @Transactional(readOnly=true)
    public Integer getMinPlayedByPlayerId(Integer playerId) {
        return statisticRepo.findMinTimePlayedByPlayerId(playerId);
    }
    
}
