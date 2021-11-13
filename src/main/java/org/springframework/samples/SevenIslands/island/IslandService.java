package org.springframework.samples.SevenIslands.island;

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
}
