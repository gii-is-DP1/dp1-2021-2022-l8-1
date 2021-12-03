package org.springframework.samples.SevenIslands.game;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.AdminController;
import org.springframework.samples.SevenIslands.player.PlayerController;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.samples.SevenIslands.web.WelcomeController;
import org.springframework.stereotype.Service;

@Service
public class GameService {
   
    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private SecurityService securityService;

    @Autowired(required = false)
    private WelcomeController welcomeController;

    @Autowired(required = false)
    private AdminController adminController;

    @Autowired(required = false)
    private PlayerController playerController;

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
        if(games.contains(findGameById(gameId).get())){
            return true;
        } else {
            return false;
        }
    }

    
    @Transactional
    public List<Game> findGamesByPlayerId(int id){ //Find games where the player with this Id have played
        return gameRepo.findGamesByPlayerId(id);
    }
    
    @Transactional
    public Collection<Game> findGamesByRoomCode(String code){
        return gameRepo.findGamesByRoomCode(code);
    }

    @Transactional
    public String deleteGame(Optional<Game> game, int gameId, ModelMap modelMap, HttpServletRequest request) {
        if(securityService.isAdmin()) {
            securityService.insertIdUserModelMap(modelMap); 

            if(game.isPresent()) {
                delete(game.get());
                modelMap.addAttribute("message", "Game successfully deleted");
                
            } else {
                modelMap.addAttribute("message", "Game not found");
            }
            return adminController.rooms(modelMap);
        
        } else if(securityService.isAuthenticatedUser()){
            securityService.insertIdUserModelMap(modelMap); 
            int currentPlayerId = securityService.getCurrentUserId();   

            if(isOwner(currentPlayerId, gameId)) { // if the user is the owner of the game, the game is obviously present
                delete(game.get());
                modelMap.addAttribute("message", "Game successfully deleted!");
                
            } else {
                modelMap.addAttribute("message", "You are not allowed to delete this game!");
                
            }

            return playerController.games(modelMap);

        } else {
            request.getSession().setAttribute("message", "Please, first sign in!");
            // modelMap.addAttribute("message", "Please, first sign in!");

            return "redirect:/welcome";
            // return welcomeController.welcome(modelMap, request);
        }
    }

}
