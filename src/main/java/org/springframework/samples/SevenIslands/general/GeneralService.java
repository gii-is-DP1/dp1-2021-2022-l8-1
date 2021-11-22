package org.springframework.samples.SevenIslands.general;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    @Autowired
    private GeneralRepository generalRepo;

    @Transactional
    public int generalCount(){
        return (int) generalRepo.count();
    }

    @Transactional
    public int totalDurationAll(){
        return generalRepo.totalDurationAllGames();
    }

    @Transactional
    public int totalGamesCount(){
        return generalRepo.totalGames();
    }


}
