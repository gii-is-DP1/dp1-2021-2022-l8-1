package org.springframework.samples.SevenIslands.statistic;

import java.util.Collection;

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
	public StatisticService(StatisticRepository satisticRepo, PlayerRepository playerRepo) {
        this.statisticRepo = statisticRepo;
        this.playerRepo = playerRepo;
	}

    @Transactional(readOnly = true)
    public Collection<Player> getTwentyBestPlayersByWins() {
        Collection<Player> players = statisticRepo.findPlayersOrderedByWins();
        
        Collection<Player> twentyPlayers = players;
        for(int i=0; i<20; i++) {
            // index not exists
            if(i >= players.size()) {
                twentyPlayers.add(new Player());
            }
        }
        return players;
    }

    @Transactional(readOnly = true)
    public Collection<Player> getTwentyBestPlayersByPoints() {
        Collection<Player> players = statisticRepo.findPlayersOrderedByPoints();
        
        Collection<Player> twentyPlayers = players;
        for(int i=0; i<20; i++) {
            // index not exists
            if(i >= players.size()) {
                twentyPlayers.add(new Player());
            }
        }
        return players;
    }
    
}
