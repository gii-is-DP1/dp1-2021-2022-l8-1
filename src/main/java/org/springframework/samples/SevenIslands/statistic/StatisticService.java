package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.player.Player;
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
    
}
