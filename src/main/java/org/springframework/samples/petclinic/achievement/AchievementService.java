package org.springframework.samples.petclinic.achievement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Iterable<Achievement> findByPlayerId(int id) {
        return achievementRepo.findByPlayerId(id);
    }
    
}