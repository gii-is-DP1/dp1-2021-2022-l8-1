package org.springframework.samples.SevenIslands.player;

import java.lang.StackWalker.Option;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.achievement.AchievementService;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.statistic.StatisticService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired	
	private SecurityService securityService;

    @Autowired
    private GameService gameService;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private StatisticService statsService;

    private static final String PLAYER_NOT_FOUND = "Player not found";
    private static final String PLAYER = "player";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private static final String GAMES = "games";
    private static final String ERROR = "/error";

    @GetMapping()
    public String listPlayers(ModelMap modelMap, @PathParam("filterName") String filterName, @PathParam("pageNumber") Integer pageNumber){   //For admins
        String view ="players/listPlayers";
        securityService.insertIdUserModelMap(modelMap);

        List<Integer> pages = playerService.calculatePages(pageNumber);
        Integer previousPageNumber = pages.get(0);
        Integer nextPageNumber = pages.get(1);
        

        if (securityService.isAdmin()) {    
                    
            List<Player> players = playerService.getPaginatedPlayers(filterName, pageNumber);

            modelMap.addAttribute("players", players);
            modelMap.addAttribute("filterName", filterName==null?"":filterName);
            modelMap.addAttribute("pageNumber", pageNumber==null?0:pageNumber);
            modelMap.addAttribute("nextPageNumber", nextPageNumber);
            modelMap.addAttribute("previousPageNumber", previousPageNumber);

            
        }else{
            view = ERROR;
        }
        return view;

    }
  
    @GetMapping(path="/profile/{playerId}")
    public String profile(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view = "/players/profile";
        
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            modelMap.addAttribute(PLAYER, player.get());
            
            // STATISTIC
            modelMap.addAttribute("totalGames", gameService.findGamesCountByPlayerId(playerId));
            modelMap.addAttribute("timePlayed", statsService.getTimePlayedByPlayerId(playerId));
            modelMap.addAttribute("totalWins", statsService.getWinsCountByPlayerId(playerId));
            modelMap.addAttribute("totalPoints", statsService.getPointsByPlayerId(playerId));
          
            modelMap.addAttribute("favIsland", statsService.getFavoriteIslandByPlayerId(playerId)==null ? "noData" : statsService.getFavoriteIslandByPlayerId(playerId).getIslandNum());
            modelMap.addAttribute("favCard", statsService.getFavoriteCardByPlayerId(playerId)==null ? "noData" : statsService.getFavoriteCardByPlayerId(playerId).getCardType());
        }else{
            modelMap.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
            view = ERROR; 
        }
        securityService.insertIdUserModelMap(modelMap);
        return view;
    }

    @GetMapping(path="/profile/{playerId}/achievements")
    public String achievements(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view = "players/achievements";
       
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            securityService.insertIdUserModelMap(modelMap);
        
            List<Achievement> achieved = StreamSupport.stream(achievementService.findByPlayerId(player.get().getId()).spliterator(), false).collect(Collectors.toList());
            List<Achievement> achievements = StreamSupport.stream(achievementService.findAll().spliterator(), false).collect(Collectors.toList());
            List<Achievement> notAchieved = achievements.stream().filter(x->!achieved.contains(x)).collect(Collectors.toList());
            // all achievements that are not in achieved

            List<Achievement> res = playerService.getAchievements(notAchieved, achieved, player.get());
            
            List<Achievement> finalAchieved = res;

            //We update the values of notAchieved Achievements
            List<Achievement> finalNotAchieved = achievements.stream().filter(x->!finalAchieved.contains(x)).collect(Collectors.toList());


            modelMap.addAttribute("achieved", finalAchieved); 
            modelMap.addAttribute("notAchieved", finalNotAchieved);
            modelMap.addAttribute(PLAYER, player.get());
        }else {
            modelMap.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
            view = ERROR;
        }
        return view;
    }

    @GetMapping(path="/profile/{playerId}/statistics")  
    public String statistics(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view = "players/statistics";
        securityService.insertIdUserModelMap(modelMap);
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            modelMap.addAttribute(PLAYER, player.get());

            // GAMES
            modelMap.addAttribute("totalGames", gameService.findGamesCountByPlayerId(playerId));

            // TIME
            modelMap.addAttribute("maxTime", statsService.getMaxTimePlayedByPlayerId(playerId));
            modelMap.addAttribute("minTime", statsService.getMinTimePlayedByPlayerId(playerId));
            modelMap.addAttribute("avgTime", statsService.getAvgTimePlayedByPlayerId(playerId));
            modelMap.addAttribute("totalTime", statsService.getTimePlayedByPlayerId(playerId));

            // POINTS
            modelMap.addAttribute("maxPoints", statsService.getMaxPointsByPlayerId(playerId));
            modelMap.addAttribute("minPoints", statsService.getMinPointsByPlayerId(playerId));
            modelMap.addAttribute("avgPoints", statsService.getAvgPointsByPlayerId(playerId));
            modelMap.addAttribute("totalPoints", statsService.getPointsByPlayerId(playerId));
            
        }else{
            modelMap.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
            view = ERROR;
        }
        return view;
    }

    @GetMapping(path="/rooms")
    public String games(ModelMap modelMap, HttpServletRequest request) {

        securityService.insertIdUserModelMap(modelMap);
        String view = "games/publicRooms";
        Iterable<Game> games = gameService.findAllPublicNotPlaying();
        modelMap.addAttribute(MESSAGE, request.getSession().getAttribute(MESSAGE));
        StreamSupport.stream(games.spliterator(), false).forEach(x->x.setStartTime(x.getStartTime().truncatedTo(ChronoUnit.SECONDS)));
        modelMap.addAttribute(GAMES, games);

        request.getSession().removeAttribute(MESSAGE);
        return view;
    }

    @GetMapping(path="/profile/{playerId}/rooms/created")
    public String gamesCreated(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view = "players/roomsCreated";
        securityService.insertIdUserModelMap(modelMap);
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            Collection<Game> games = gameService.findByOwnerId(player.get().getId());
            StreamSupport.stream(games.spliterator(), false).forEach(x->x.setStartTime(x.getStartTime().truncatedTo(ChronoUnit.SECONDS)));
            modelMap.addAttribute(GAMES, games);
            modelMap.addAttribute(PLAYER, player.get());
        }else{
            modelMap.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
            view = ERROR;
        }
        return view;
    }

    @GetMapping(path="/profile/{playerId}/rooms/played")
    public String gamesPlayed(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view = "players/roomsPlayed";
        securityService.insertIdUserModelMap(modelMap);
        Optional<Player> player = playerService.findPlayerById(playerId);

        if(player.isPresent()){
            Collection<Game> games = gameService.findGamesByPlayerId(player.get().getId());
            StreamSupport.stream(games.spliterator(), false).forEach(x->x.setStartTime(x.getStartTime().truncatedTo(ChronoUnit.SECONDS)));
            modelMap.addAttribute(GAMES, games);
            modelMap.addAttribute(PLAYER, player.get());
        }else{
            modelMap.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
            view = ERROR;
        }
        return view;
    }

  

    @GetMapping(path="/delete/{playerId}")
    public String deletePlayer(@PathVariable("playerId") int playerId, ModelMap modelMap, HttpServletRequest request){
        String view= "players/listPlayers";
        securityService.insertIdUserModelMap(modelMap);
        if (securityService.isAdmin()) {
            Optional<Player> p = playerService.findPlayerById(playerId);
            if(p.isPresent()){
                Player player = p.get();
                Collection<Game> lg = player.getGames()==null? new ArrayList<>():player.getGames();
                Collection<Game> col = lg.stream().filter(x->x.getPlayer().getId()!=playerId).collect(Collectors.toCollection(ArrayList::new));
                col.stream().forEach(x->x.deletePlayerOfGame(player));
                
                if(!lg.isEmpty()) {

                    player.deleteGames(col);

                }
                
                playerService.delete(player);
                modelMap.addAttribute(MESSAGE, "Player successfully deleted!");

                return listPlayers(modelMap, null, 0);

            }else{
                modelMap.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
                view=listPlayers(modelMap, null, 0);
            }
        }else{
            view = ERROR;
        }
        return view;

    }

    private static final String VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM = "players/createOrUpdatePlayerForm";

    @GetMapping(path="/edit/{playerId}")
    public String updatePlayer(@PathVariable("playerId") int playerId, ModelMap model) {

        Optional<Player> player = playerService.findPlayerById(playerId); 
        String view = VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
        securityService.insertIdUserModelMap(model); 

        if(securityService.isAdmin()) {
            if(player.isPresent()){ 
                model.addAttribute(PLAYER, player.get());
            }else{
                model.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
                view = ERROR;
            }
        }else if (securityService.isAuthenticatedUser()) {

            if(player.isPresent()) {
                int playerLoggedId = securityService.getCurrentPlayerId();
                if(playerLoggedId == playerId) {
                    model.addAttribute(PLAYER, player.get());

                } else{
                    model.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
                    view = ERROR;
                }
            } else{
                model.addAttribute(MESSAGE, PLAYER_NOT_FOUND);
                view = ERROR;
            }

           
        }else{
            view = ERROR;
        }
        return view;
    }

    /**
     *
     * @param player
     * @param result
     * @param playerId
     * @param model
     * @param surname
     * @param firstName
     * @param email
     * @param password
     * @param model
     * @return
     */

    @PostMapping(value = "/edit/{playerId}")
	public String processUpdateForm(@Valid Player player, BindingResult result,@PathVariable("playerId") int playerId, ModelMap model,
                                        @RequestParam(value="version", required = false) Integer version) {

        Player playerBeforeEdit = new Player();
        Optional<Player> playerOpt =  playerService.findPlayerById(playerId);

        if(playerOpt.isPresent()){
            playerBeforeEdit=playerOpt.get();
        }

        if(!playerBeforeEdit.getVersion().equals(version)){    //Version
            model.put(MESSAGE, "Concurrent modification of player! Try again!");
            return updatePlayer(playerBeforeEdit.getId(),model);
        }else if(playerService.playerHasInappropiateWords(player)){
            model.addAttribute(ERROR_MESSAGE, "Your profile can't contains inappropiate words. Please, check your language.");
			model.put(PLAYER, player);
			return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
        }
    
        Boolean isNewUser = false;
        if(playerService.emailAlreadyused(player.email, playerBeforeEdit, isNewUser)){
            model.addAttribute(ERROR_MESSAGE, "Email already used by other user");
            model.put(PLAYER, player);
			return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
        }
        if(playerService.usernameAlreadyused(player.getUser().getUsername(), playerBeforeEdit.getUser(), isNewUser)){
            model.addAttribute(ERROR_MESSAGE, "Username already used by other user");
            model.put(PLAYER, player);
			return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
        }

		if (result.hasErrors()){
			model.put(PLAYER, player);
			return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM; 
		} else {
        
            if(!player.getUser().getUsername().contains(" ")) {
                return playerService.processEditPlayer(player, playerId, result);
            
            } else {
                model.addAttribute(ERROR_MESSAGE, "Your username can't contain empty spaces. ");
                return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
            } 
		}
	}  

    @GetMapping(path="/auditing")
    public String playerAuditing(ModelMap modelMap, @PathParam("filterName") String filterName){
        
        String view= "players/auditing";
        securityService.insertIdUserModelMap(modelMap);

        if (securityService.isAdmin()) {

            if(filterName==null){ 
                filterName = "";
            }
            
            List<?> listPlayers = playerService.getAuditPlayers(filterName).stream().collect(Collectors.toList());
            modelMap.addAttribute("players", listPlayers);
            modelMap.addAttribute("filterName", filterName);
                    
        }else{
            view = ERROR;
        }
        return view;

    }

}
