package org.springframework.samples.SevenIslands.achievement;

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
    public void save(Achievement achievement){
        achievementRepo.save(achievement);
    }

    @Transactional
    public void delete(Achievement achievement){
        achievement.getPlayers().forEach(player -> player.getAchievements().remove(achievement));
        achievementRepo.delete(achievement);
    }

    @Transactional
    public Iterable<Achievement> findByPlayerId(int id) {
        return achievementRepo.getByPlayerId(id);
    }


    @Transactional
    public Iterable<Achievement> findByAdminId(int id) {
        return achievementRepo.findByAdminId(id);
    }
    
}