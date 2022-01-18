package org.springframework.samples.SevenIslands.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.ehcache.core.spi.service.StatisticsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.achievement.AchievementRepository;
import org.springframework.samples.SevenIslands.achievement.AchievementService;
import org.springframework.samples.SevenIslands.achievement.PARAMETER;
import org.springframework.samples.SevenIslands.card.CardRepository;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWord;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWordService;
import org.springframework.samples.SevenIslands.statistic.StatisticService;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
        return achievementService.findByPlayerId(id);
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
    public Collection<?> getAuditPlayers(String username) {
        return playerRepo.findAuditPlayers(username);

    }

    @Transactional(readOnly = true)
    public Collection<?> getAuditPlayers2() {
        return playerRepo.findAuditPlayers2();

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
    public List<Integer> calculatePages(int pageNumber) {
        
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

        
        if(pageNumber==0){
            // previousPageNumber=0;
            pages.set(0, 0);

            // nextPageNumber= pageNumber+1;
            pages.set(1, pageNumber+1);
        
        }else if(pageNumber==totalPages-1){ 
            // previousPageNumber=pageNumber-1;
            pages.set(0, pageNumber-1);
            // nextPageNumber = pageNumber;
            pages.set(1, pageNumber);

        } else {
            // previousPageNumber=pageNumber-1;
            pages.set(0, pageNumber-1);
            // nextPageNumber = pageNumber+1;
            pages.set(1, pageNumber+1);

        }

        return pages;
    }


    @Transactional
    public List<Achievement> getAchievements(List<Achievement> notAchieved, List<Achievement> achieved, Player player) {

        int totalPoints = statsService.getPointsByPlayerId(player.getId())==null?0:statsService.getPointsByPlayerId(player.getId());
        int totalGames = player.getGames().size();
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
            return "redirect:/players";
        } else {
            Player playerToUpdate = playerOpt.get();
            int id = playerToUpdate.getUser().getAuthorities().iterator().next().getId();   
            String username = playerToUpdate.getUser().getUsername();   
            Iterable<Player> players = findAll();
            List<String> usernames = StreamSupport.stream(players.spliterator(),false).map(x->x.getUser().getUsername().toString()).collect(Collectors.toList());
            
            BeanUtils.copyProperties(player, playerToUpdate,"id", "profilePhoto","totalGames","totalTimeGames","avgTimeGames","maxTimeGame","minTimeGame","totalPointsAllGames","avgTotalPoints","favoriteIsland","favoriteTreasure","maxPointsOfGames","minPointsOfGames","achievements","cards","watchGames","forums","games","invitations","friend_requests","players_friends","gamesCreador");  //METER AQUI OTRAS PROPIEDADES                                                                                
            
            
            String newUserName = playerToUpdate.getUser().getUsername();    

            try {                    

                // si el NUEVO username está ya en la DB && el NUEVO username NO es el mismo que el viejo
                // se trata de un error, pues estamos editando un usuario que ya existe
                if(usernames.stream().anyMatch(x->x.equals(newUserName) && !newUserName.equals(username))){
                    return "errors/error-500";
                }

                // guardo el nuevo player (recordemos que en playerToUpdate ya se han copiado las propiedades del nuevo player)
                savePlayer(playerToUpdate);

                authoritiesService.deleteAuthorities(id);
                // si el NUEVO username es diferente al antiguo, se elimina el ANTIGUO 
                if(!username.equals(newUserName)){
                    userService.delete(username);
                }    

            } catch (Exception ex) {
                result.rejectValue("name", "duplicate", "already exists");
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
