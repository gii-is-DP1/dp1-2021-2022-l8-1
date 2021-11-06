package org.springframework.samples.petclinic.player;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepo;

    @Transactional
    public int playerCount(){
        return (int) playerRepo.count();
    }

    @Transactional
    public Iterable<Player> findAll(){
        return playerRepo.findAll();
    }

    @Transactional
    public Optional<Player> findGameById(int id){
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
    public void addAchievement(String name) {
        playerRepo.addAchievement(name);
    }

}
