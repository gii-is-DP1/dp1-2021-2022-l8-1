package org.springframework.samples.petclinic.player;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.achievement.Achievement;
import org.springframework.samples.petclinic.achievement.AchievementRepository;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.card.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerService {

    private PlayerRepository playerRepo;

    private CardRepository cardRepo;

    private AchievementRepository achievementRepo;

    @Autowired
	public PlayerService(PlayerRepository playerRepo,
                    CardRepository cardRepo) {
		this.playerRepo = playerRepo;
		this.cardRepo = cardRepo;
	}

    @Transactional(readOnly = true)
    public int playerCount(){
        return (int) playerRepo.count();
    }

    @Transactional(readOnly = true)
    public Iterable<Player> findAll(){
        return playerRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Player> findPlayerById(int id){
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

    @Transactional(readOnly = true)
    public Iterable<Card> getCardsByPlayerId(int id) {
        return cardRepo.getByPlayerId(id);
    }

    @Transactional(readOnly = true)
    public Iterable<Achievement> getAchievementsByPlayerId(int id) {
        return achievementRepo.getByPlayerId(id);
    }
}
