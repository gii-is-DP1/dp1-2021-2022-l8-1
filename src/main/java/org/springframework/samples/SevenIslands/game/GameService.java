package org.springframework.samples.SevenIslands.game;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
   
    @Autowired
    private GameRepository gameRepo;

    @Transactional
    public int gameCount(){
        return (int) gameRepo.count();
    }

    @Transactional
    public Iterable<Game> findAll(){
        return gameRepo.findAll();
    }

    @Transactional
    public Iterable<Game> findAllPublic(){
        return gameRepo.findAllPublic();
    }

    @Transactional
    public Collection<Game> findAllPublicPlaying() {
        return gameRepo.findAllPublicPlaying();
    }

    @Transactional
    public Collection<Game> findAllPlaying() {
        return gameRepo.findAllPlaying();
    }

    @Transactional
    public Optional<Game> findGameById(int id){
        return gameRepo.findById(id);
    }

    @Transactional
    public void save(Game game){
        gameRepo.save(game);
    }

    @Transactional
    public void delete(Game game){
        gameRepo.delete(game);
    }

    @Transactional
    public Collection<Game> findGamesByPlayerId(int id){
        return gameRepo.findGamesByPlayerId(id);
    }

    
    @Transactional
    public List<Game> findGamesWhereIPlayerByPlayerId(int id){
        return gameRepo.findGamesWhereIPlayedByPlayerId(id);
    }

    @Transactional
    public void insertGP(int game_id, int player_id){
        gameRepo.insertGP(game_id,player_id);
    }

    @Transactional
    public Collection<Integer> getPlayersIdOnGame(int game_id){
        return gameRepo.findIdPlayersByGameId(game_id);
    }



}
