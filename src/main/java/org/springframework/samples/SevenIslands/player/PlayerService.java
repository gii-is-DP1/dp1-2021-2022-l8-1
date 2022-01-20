package org.springframework.samples.SevenIslands.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.achievement.AchievementService;
import org.springframework.samples.SevenIslands.achievement.PARAMETER;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWord;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWordService;
import org.springframework.samples.SevenIslands.statistic.StatisticService;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
	private UserService userService;

    @Autowired
    private StatisticService statsService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

    @Autowired
    private InappropiateWordService inappropiateWordService;

    @Autowired
    private SecurityService securityService;


    @Autowired
    private AchievementService achievementService;

    @Autowired
	public PlayerService(PlayerRepository playerRepo,
                    UserService userService,
                    AuthoritiesService authoritiesService,
                    SecurityService securityService,
                    AchievementService achievementService) {
        this.playerRepo = playerRepo;
        this.userService = userService;
        this.securityService = securityService;
        this.achievementService = achievementService;
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

    @Transactional
	public void savePlayer(Player player) throws DataAccessException {

        if(!player.getUser().getUsername().contains(" ")) {
            //creating player
            playerRepo.save(player);		
            //creating user
            userService.saveUser(player.getUser());
            //creating authorities
            authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");
        }
	}

    @Transactional(readOnly = true)
    public Iterable<Achievement> getAchievementsByPlayerId(int id) {
        return achievementService.findByPlayerId(id);
    }

    @Transactional(readOnly = true)
    public Integer getIdPlayerByName(String n) {
        return playerRepo.findPlayerIdByName(n);
    }

    @Transactional(readOnly = true)
    public Collection<Player> getPlayerByUsername(String n) {
        return playerRepo.findPlayerByUsername(n);

    }

    @Transactional(readOnly = true)
    public Collection<?> getAuditPlayers(String username) {
        return playerRepo.findAuditPlayers(username);

    }

    @Transactional(readOnly = true)
    public Boolean emailAlreadyused(String email, Player p, Boolean isNewPlayer){
        List<String> emails = playerRepo.findEmails().stream().collect(Collectors.toList());
        if(!isNewPlayer){
            return emails.contains(email) && !p.getEmail().equals(email);
        }else{
            return emails.contains(email);
        }
    }

    @Transactional(readOnly = true)
    public Boolean usernameAlreadyused(String userName, User u, Boolean isNewUser){
        List<String> userNames = playerRepo.findUsernames().stream().collect(Collectors.toList());
        if(!isNewUser){
            return userNames.contains(userName) && !u.getUsername().equals(userName);
        }else{
            return userNames.contains(userName);
        }
    }
  
    @Transactional(readOnly = true)
    public boolean playerHasInappropiateWords(Player player){
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

    @Transactional
    public List<Integer> calculatePages(Integer pageNumber) { 
        
        if(pageNumber == null){
            pageNumber = 0;
        }

        Integer playerCount = playerCount();
        Integer totalPages;

        if(playerCount %5 == 0) {
            totalPages = playerCount/5;
        } else {
            totalPages = playerCount/5 + 1;
        }

        List<Integer> pages = new ArrayList<>();
        pages.add(0);
        pages.add(0);

        
        if(pageNumber==0){  //first page

            pages.set(0, pageNumber);
            pages.set(1, pageNumber+1);
        
        }else if(pageNumber==totalPages-1){  //last page
            pages.set(0, pageNumber-1);
            pages.set(1, pageNumber);

        } else { //intermediate pages
            pages.set(0, pageNumber-1);
            pages.set(1, pageNumber+1);

        }

        return pages;
    }


    @Transactional
    public List<Achievement> getAchievements(List<Achievement> notAchieved, List<Achievement> achieved, Player player) {

        int totalPoints = statsService.getPointsByPlayerId(player.getId())==null?0:statsService.getPointsByPlayerId(player.getId());
        int totalGames = player.getGames() == null? 0: player.getGames().size();
        int totalWins = statsService.getWinsCountByPlayerId(player.getId())==null?0:statsService.getWinsCountByPlayerId(player.getId());
        int totalLoses = totalGames - totalWins;
    
        for(Achievement a: notAchieved) {

            boolean condition = (a.getParameter() == PARAMETER.POINTS && totalPoints>=a.getMinValue()) || (a.getParameter() == PARAMETER.GAMES_PLAYED && totalGames>=a.getMinValue()) 
                                || (a.getParameter() == PARAMETER.WINS && totalWins>=a.getMinValue()) || (a.getParameter() == PARAMETER.LOSES && totalLoses>=a.getMinValue());

            if(condition) {
                player.getAchievements().add(a);
                save(player);
                achieved.add(a);

            } 
        }

        return achieved;

    }

    @Transactional
    public List<Player> getPaginatedPlayers(String filterName, Integer pageNumber) {

        if(pageNumber == null) {
            pageNumber = 0;
        }
        
        Pageable page = PageRequest.of(pageNumber, 5);

        Iterable<Player> playersPaginated = findAll(page);

            if(filterName != null) {    // if the user has typed something in the search bar
                playersPaginated = findIfPlayerContains(filterName.toLowerCase(), page);
            }

        return StreamSupport.stream(playersPaginated.spliterator(), false).collect(Collectors.toList());

    }


    @Transactional
    public String processEditPlayer(Player player, int playerId, BindingResult result) {

        Optional<Player> playerOpt = findPlayerById(playerId);
        
        if(!playerOpt.isPresent()) {
            if(securityService.isAdmin()) {
                return "redirect:/players"; 
            } else {
                return "errors/error-404";
            }    
        } else {
            Player playerToUpdate = playerOpt.get();
            int id = playerToUpdate.getUser().getAuthorities().iterator().next().getId();   
            String username = playerToUpdate.getUser().getUsername();   
            Iterable<Player> players = findAll();
            List<String> usernames = StreamSupport.stream(players.spliterator(),false).map(x->x.getUser().getUsername()).collect(Collectors.toList());
            
            BeanUtils.copyProperties(player, playerToUpdate,"id", "profilePhoto","totalGames","totalTimeGames","avgTimeGames","maxTimeGame","minTimeGame","totalPointsAllGames","avgTotalPoints","favoriteIsland","favoriteTreasure","maxPointsOfGames","minPointsOfGames","achievements","cards","games","gamesCreador");                                                                              
            
            String newUserName = playerToUpdate.getUser().getUsername();    

            try {                    
                // If the new username is already in the db && the new username is not the same as the old one, 
                // it is an error, because we are editing a user that already exists

                if(usernames.stream().anyMatch(x->x.equals(newUserName) && !newUserName.equals(username))){
                    return "errors/error-500";
                }

                // I save the new player (remember that in playerToUpdate the properties of the new player have already been copied)
                savePlayer(playerToUpdate); 

                authoritiesService.deleteAuthorities(id);
                // If the new username is different from the old one, the old one is deleted
                if(!username.equals(newUserName)){
                    userService.delete(username);
                }    

            } catch (Exception ex) {
                result.rejectValue("name", "duplicate", "already exists");
                log.error("An ERROR Message");
                return "players/createOrUpdatePlayerForm";
            }

            if(securityService.isAdmin()){  
                return "redirect:/players";

            }else{
                if(!username.equals(newUserName)){
                    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
                    return "redirect:/welcome";
                }
                return "redirect:/players/profile/{playerId}";
            }
        }
    } 

}
