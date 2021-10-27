package org.springframework.samples.petclinic.game;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Game;
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

}
