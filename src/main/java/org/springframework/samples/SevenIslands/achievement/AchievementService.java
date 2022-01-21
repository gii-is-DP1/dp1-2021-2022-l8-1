package org.springframework.samples.SevenIslands.achievement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWord;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AchievementService {
    
    @Autowired
    private AchievementRepository achievementRepo;

    @Autowired
    private InappropiateWordService inappropiateWordService;

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
        if(achievement.getPlayers()!= null){
            achievement.getPlayers().forEach(player -> player.getAchievements().remove(achievement));
        }
        achievementRepo.delete(achievement);
    }

    @Transactional
    public Iterable<Achievement> findByPlayerId(int id) {
        return achievementRepo.getByPlayerId(id);
    }

    @Transactional
    public boolean achievementHasInappropiateWords(Achievement achievement){
        Iterable<InappropiateWord> words = inappropiateWordService.findAll();
        List<String> listWords = StreamSupport.stream(words.spliterator(), false).map(InappropiateWord::getName).collect(Collectors.toList());
        return listWords.stream().anyMatch(word-> achievement.getName().toLowerCase().contains(word) || achievement.getDescription().toLowerCase().contains(word));
    }
    
}