package org.springframework.samples.petclinic.game;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepo; //Error

    @Transactional
    public int gameCount(){
        //int data = (int) gameRepo.count();
        return (int) gameRepo.count();
    }
}
