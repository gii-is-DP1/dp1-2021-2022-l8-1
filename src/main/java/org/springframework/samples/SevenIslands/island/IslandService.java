package org.springframework.samples.SevenIslands.island;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IslandService {

    @Autowired
    private IslandRepository islandRepo;

    @Transactional
    public int islandCount() {
        return (int) islandRepo.count();
    }    

    @Transactional
    public void save(Island i){
        islandRepo.save(i);
    }

    @Transactional
    public Optional<Island> getByIslandId(Integer islandId) {
        return islandRepo.findById(islandId);
    }
}

