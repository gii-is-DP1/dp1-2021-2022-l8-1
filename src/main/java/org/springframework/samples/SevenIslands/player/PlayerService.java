package org.springframework.samples.SevenIslands.player;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.achievement.AchievementRepository;
import org.springframework.samples.SevenIslands.card.CardRepository;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWord;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWordService;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

    @Autowired
    private InappropiateWordService inappropiateWordService;

    @Autowired
    private CardRepository cardRepo;

    @Autowired
    private AchievementRepository achievementRepo;

    @Autowired
	public PlayerService(PlayerRepository playerRepo,
                    UserService userService,
                    AuthoritiesService authoritiesService,
                    CardRepository cardRepo,
                    AchievementRepository achievementRepo) {
        this.playerRepo = playerRepo;
        this.userService = userService;                
        this.cardRepo = cardRepo;
        this.achievementRepo = achievementRepo;
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
    public Iterable<Player> findAll(Pageable pageable){
        return playerRepo.findAll(pageable);
    }

    @Transactional(readOnly=true)
    public Page<Player> findIfPlayerContains(String data, Pageable pageable){
        return playerRepo.findIfPlayerContains(data, pageable);
    }



    @Transactional(readOnly = true)
	public Collection<Player> findPlayerBySurname(String surname) throws DataAccessException {
		return playerRepo.findBySurname(surname);
	}

    @Transactional
    public Optional<Player> findPlayerById(int id){
        return playerRepo.findPlayerById(id);
    }

    @Transactional
    public void save(Player player){
        playerRepo.save(player);
    }

    @Transactional
    public void delete(Player player){
        playerRepo.delete(player);
    }
    
    @Transactional
    public Iterable<Player> findPlayersByGameId(int id) {
        return playerRepo.findByGameId(id);
    }

    //Return invitations of a player with playerId=id
    // @Transactional
    // public Iterable<Player> findInvitationsByPlayerId(int id) {
    //     return playerRepo.findInvitationsByPlayerId(id);
    // }

    // //Return requests of a player with playerId=id
    // @Transactional
    // public Iterable<Player> findRequestsByPlayerId(int id) {
    //     return playerRepo.findRequestsByPlayerId(id);
    // }

     //Return watchGame of a player with playerId=id
     @Transactional
     public Iterable<Player> findWatchGameByPlayerId(int id) {
         return playerRepo.findWatchGameByPlayerId(id);
     }

    @Transactional
    public Iterable<Player> findByForumId(int id) {
        return playerRepo.findByForumId(id);
    }

    @Transactional
	public void savePlayer(Player player) throws DataAccessException {
		//creating owner
		playerRepo.save(player);		
		//creating user
		userService.saveUser(player.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
	}

    // @Transactional(readOnly = true)
    // public Iterable<Card> getCardsByPlayerId(int id) {
    //     return cardRepo.getByPlayerId(id);
    // }

    @Transactional(readOnly = true)
    public Iterable<Achievement> getAchievementsByPlayerId(int id) {
        return achievementRepo.getByPlayerId(id);
    }

    @Transactional(readOnly = true)
    public Integer getIdPlayerByName(String n) {
        return playerRepo.findPlayerIdByName(n);
    }

    @Transactional(readOnly = true)
    public Collection<Player> getPlayerByUsername(String n) {
        return playerRepo.findPlayerByUsername(n); //PUESTO DE PRUEBA

    }

    @Transactional(readOnly = true)
    public Collection<Player> getTwentyBestPlayersByWins() {
        Collection<Player> players = playerRepo.findByPlayerIdOrderedByWins();
        
        Collection<Player> twentyPlayers = players;
        for(int i=0; i<20; i++) {
            // index not exists
            if(i >= players.size()) {
                twentyPlayers.add(new Player());
            }
        }
        return players; // TEMP
    }

    @Transactional(readOnly = true)
    public Collection<Player> getTwentyBestPlayersByPoints() {
        Collection<Player> players = playerRepo.findByPlayerIdOrderedByPoints();
        
        Collection<Player> twentyPlayers = players;
        for(int i=0; i<20; i++) {
            // index not exists
            if(i >= players.size()) {
                twentyPlayers.add(new Player());
            }
        }
        return players; // TEMP
    }
    
    @Transactional(readOnly = true)
    public Boolean playerHasInappropiateWords(Player player){
        String firstName = player.getFirstName().toLowerCase();
        String surName = player.getSurname().toLowerCase();
        String userName = player.getUser().getUsername().toLowerCase();
        Iterable<InappropiateWord> words = inappropiateWordService.findAll();
        List<String> listWords = StreamSupport.stream(words.spliterator(), false).map(x-> x.getName()).collect(Collectors.toList());
        Boolean firstNameHasWords = listWords.stream().anyMatch(word-> firstName.contains(word));
        Boolean surNameHasWords = listWords.stream().anyMatch(word-> surName.contains(word));
        Boolean userNameHasWords = listWords.stream().anyMatch(word-> userName.contains(word));
        return firstNameHasWords || surNameHasWords || userNameHasWords;
     }
}
