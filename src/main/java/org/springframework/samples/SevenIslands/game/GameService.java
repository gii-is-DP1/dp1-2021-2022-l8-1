package org.springframework.samples.SevenIslands.game;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWord;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWordService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.stereotype.Service;

@Service
public class GameService {
   
    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private InappropiateWordService inappropiateWordService;

    private static final String REDIRECT_TO_GAMES_ROOMS = "redirect:/games/rooms";

    @Transactional
    public int gameCount(){
        return (int) gameRepo.count();
    }

    @Transactional
    public Iterable<Game> findAll(){
        return gameRepo.findAll();
    }

    @Transactional
    public Iterable<Game> findAllPublic(){
        return gameRepo.findAllPublic();
    }

    @Transactional
    public Iterable<Game> findAllPublicNotPlaying(){
        return gameRepo.findAllPublicNotPlaying();
    }

    @Transactional
    public Collection<Game> findAllPublicPlaying() {
        return gameRepo.findAllPublicPlaying();
    }

    @Transactional
    public Collection<Game> findAllPlaying() {
        return gameRepo.findAllPlaying();
    }

    @Transactional
    public Optional<Game> findGameById(int id){
        return gameRepo.findById(id);
    }

    @Transactional
    public void save(Game game){
        gameRepo.save(game);
    }

    @Transactional
    public void delete(Game game){
        gameRepo.delete(game);
    }

    @Transactional
    public Collection<Game> findByOwnerId(int id){ //Find games where im a owner
        return gameRepo.findByOwnerId(id);
    }

    @Transactional
    public boolean isOwner(int playerId, int gameId){ //Check if im the owner of the game
        Collection<Game> games = findByOwnerId(playerId);
        Optional<Game> opt = findGameById(gameId);
        return opt.isPresent() && games.contains(opt.get());

    }
    
    @Transactional
    public List<Game> findGamesByPlayerId(int id){ //Find games where the player with this Id have played
        return gameRepo.findGamesByPlayerId(id);
    }

    @Transactional(readOnly=true)
    public Integer findGamesCountByPlayerId(int id){
        return gameRepo.findGamesCountByPlayerId(id);
    }
    
    @Transactional
    public Iterable<Game> findGamesByRoomCode(String code){
        return gameRepo.findGamesByRoomCode(code);
    }

    @Transactional
    public String deleteGame(Optional<Game> game, int gameId, ModelMap modelMap, HttpServletRequest request) {

        if(game.isPresent()) {
            securityService.insertIdUserModelMap(modelMap); 

            if(securityService.isAdmin()) {

                if(game.get().isHasStarted()){
                   request.getSession().setAttribute("message", "The game has already started, you can't delete it");
                } else {
                    delete(game.get());
                    request.getSession().setAttribute("message", "Game successfully deleted");
                }
                

            } else if(securityService.isAuthenticatedUser()) {
                int currentPlayerId = securityService.getCurrentPlayerId();   

                if(isOwner(currentPlayerId, gameId)) { 
                    delete(game.get());
                    request.getSession().setAttribute("message", "Game successfully deleted!");
                    
                } else {
                    request.getSession().setAttribute("message", "You are not allowed to delete this game!");
                }

            }   else {
                securityService.redirectToWelcome(request);
            }

        }   else {
            request.getSession().setAttribute("message", "Game not found");
        }

        return REDIRECT_TO_GAMES_ROOMS;
        
    }


    @Transactional
    public void addGamesToModelMap(Iterable<Game> games, ModelMap modelMap) {
        if(games.spliterator().getExactSizeIfKnown()==0l){   // if there are no games with this code
            modelMap.addAttribute("message", "Game not found");
        } else {
            modelMap.addAttribute("games", games);
        }
    }   

    @Transactional
    public String getLobby(Iterable<Game> gameOpt, ModelMap modelMap, HttpServletRequest request) {
        
            securityService.insertIdUserModelMap(modelMap);

            if(gameOpt.spliterator().getExactSizeIfKnown()==1l && !securityService.isAdmin()) { // if the game is present and the user is not an admin, it should be gameOpt.spliterator().getExactSizeIfKnown()==1l
                Game game = gameOpt.iterator().next();  // get the game (first element of the iterable)
                
                
                if(game.isHasStarted()) {
                    return "redirect:/boards/" + game.getCode();
                } 
                
                modelMap.addAttribute("game", game);

                int playerId = securityService.getCurrentPlayerId(); 
                Optional<Player> opt = playerService.findPlayerById(playerId);
                if(opt.isPresent()) {

                    Player player = opt.get();
                    modelMap.addAttribute("player", player);
                    modelMap.addAttribute("totalplayers", game.getPlayers().size());

                    if(!game.getPlayers().contains(player) && game.getPlayers().size()<4) {
                        game.addPlayerinPlayers(player);
                        save(game);
                        player.addGameinGames(game);
                        playerService.save(player);
                        return "games/lobby";
                    
                    } else if(game.getPlayers().contains(player)) {
                        return "games/lobby";
                    } else {
                        return "redirect:/welcome";
                    }
                    
                } else {
                    return "/error";
                }

            } else if(securityService.isAdmin()) {
                request.getSession().setAttribute("message", "You can't join a game if you are an admin!");
                return REDIRECT_TO_GAMES_ROOMS;

            } else {    // when the user is a player and the game is not found
                request.getSession().setAttribute("message", "The game you are trying to join doesn't exist!");
                return REDIRECT_TO_GAMES_ROOMS;
                
            }
    }

    @Transactional
    public String exitGame(Optional<Game> game, HttpServletRequest request) {
    
        if(game.isPresent()) {

            if(securityService.isAdmin()) {
                request.getSession().setAttribute("message", "You can't join or exit a game!");
                
           
            } else {                 
                Game gameToExit = game.get();
                Player currentPlayer = securityService.getCurrentPlayer();

                if(gameToExit.getPlayers().contains(currentPlayer)) {
                    gameToExit.deletePlayerOfGame(currentPlayer);
                    save(gameToExit);
        
                    currentPlayer.getGames().remove(gameToExit);
                    playerService.save(currentPlayer);

                    request.getSession().setAttribute("message", "You have successfully exited the game!");

                } else {
                    request.getSession().setAttribute("message", "You are not a player of this game!");   
                }
            }

        } else {
            request.getSession().setAttribute("message", "The game you are trying to exit does not even exist!");
        }

        return REDIRECT_TO_GAMES_ROOMS;

    }
  
    public boolean gameHasInappropiateWords(Game game){
        Iterable<InappropiateWord> words = inappropiateWordService.findAll();
        List<String> listWords = StreamSupport.stream(words.spliterator(), false).map(InappropiateWord::getName).collect(Collectors.toList());
        return listWords.stream().anyMatch(word-> game.getName().toLowerCase().contains(word));
    }

    @Transactional
    public boolean isWaitingOnRoom(int playerId){ //Check if the player is waiting on a room
        Collection<Game> games = findGamesByPlayerId(playerId);
        if(games.stream().anyMatch(x->!x.isHasStarted())){
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Game waitingRoom(int playerId){ //Return the waiting room
        Collection<Game> games = findGamesByPlayerId(playerId);
        if(games.stream().anyMatch(x->!x.isHasStarted())){


            Optional<Game> gameOpt = games.stream().filter(x->!x.isHasStarted()).findFirst();

            if(gameOpt.isPresent()){
                return gameOpt.get();
            }
            return null;
            
        } else {
            return null;
        }
    }
}
