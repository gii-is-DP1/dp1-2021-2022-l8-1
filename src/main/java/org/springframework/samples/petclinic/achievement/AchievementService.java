package org.springframework.samples.petclinic.achievement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {
    @Autowired
    private AchievementRepository achievementRepo;

    @Transactional
    public int achievementCount() {
        return (int) achievementRepo.count();
    }

    @Transactional
    public Iterable<Achievement> findAll() {
        return achievementRepo.findAll();
    }

    @Transactional
    public Optional<Achievement> findAchievementById(int id) {
        return achievementRepo.findById(id);
    }
    
}