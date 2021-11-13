package org.springframework.samples.petclinic.player;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepo;

    @Transactional(readOnly = true)
    public int playerCount(){
        return (int) playerRepo.count();
    }

    @Transactional(readOnly = true)
    public Iterable<Player> findAll(){
        return playerRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Player> findPlayerById(int id){
        return playerRepo.findById(id);
    }

    @Transactional
    public void save(Player player){
        playerRepo.save(player);
    }

    @Transactional
    public void delete(Player player){
        playerRepo.delete(player);
    }
    
    @Transactional
    public Iterable<Player> findPlayersByGameId(int id) {
        return playerRepo.findByGameId(id);
    }

    //Return invitations of a player with playerId=id
    @Transactional
    public Iterable<Player> findInvitationsByPlayerId(int id) {
        return playerRepo.findInvitationsByPlayerId(id);
    }

    //Return requests of a player with playerId=id
    @Transactional
    public Iterable<Player> findRequestsByPlayerId(int id) {
        return playerRepo.findRequestsByPlayerId(id);
    }

     //Return watchGame of a player with playerId=id
     @Transactional
     public Iterable<Player> findWatchGameByPlayerId(int id) {
         return playerRepo.findWatchGameByPlayerId(id);
     }

    @Transactional
    public Iterable<Player> findByForumId(int id) {
        return playerRepo.findByForumId(id);
    }

}
