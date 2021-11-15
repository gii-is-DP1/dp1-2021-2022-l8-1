package org.springframework.samples.SevenIslands.player;

import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

    @Transactional(readOnly = true)
    public int playerCount(){
        return (int) playerRepo.count();
    }

    @Transactional(readOnly = true)
    public Iterable<Player> findAll(){
        return playerRepo.findAll();
    }

    @Transactional(readOnly = true)
	public Collection<Player> findPlayerBySurname(String surname) throws DataAccessException {
		return playerRepo.findBySurname(surname);
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
    
    @Transactional
    public Iterable<Player> findPlayersByGameId(int id) {
        return playerRepo.findByGameId(id);
    }

    //Return invitations of a player with playerId=id
    @Transactional
    public Iterable<Player> findInvitationsByPlayerId(int id) {
        return playerRepo.findInvitationsByPlayerId(id);
    }

    //Return requests of a player with playerId=id
    @Transactional
    public Iterable<Player> findRequestsByPlayerId(int id) {
        return playerRepo.findRequestsByPlayerId(id);
    }

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

}
