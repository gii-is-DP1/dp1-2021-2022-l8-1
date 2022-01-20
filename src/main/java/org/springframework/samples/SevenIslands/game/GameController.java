package org.springframework.samples.SevenIslands.game;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.AdminController;
import org.springframework.samples.SevenIslands.board.BoardService;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerController;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.samples.SevenIslands.web.jsonview.Views;
import org.springframework.samples.SevenIslands.web.model.AjaxGameResponseBody;
import org.springframework.samples.SevenIslands.web.model.AjaxPlayersResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private GameService gameService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private AdminController adminController;

    @Autowired
    private PlayerController playerController;

    @Autowired
    private DeckService deckService;


    private static final String VIEW_CREATE_OR_UPDATE_GAME_FORM = "games/createOrUpdateGameForm";
    
    @GetMapping(path = "/new")
    public String createGame(ModelMap modelMap, HttpServletRequest request) { 
        Player p = securityService.getCurrentPlayer();
        if(securityService.isAdmin()) {
            request.getSession().setAttribute("message", "You must be a player to create a game");
            return "redirect:/games/rooms";
        
        }else if(gameService.isWaitingOnRoom(p.getId())) {
            Game game = gameService.waitingRoom(p.getId());
            return "redirect:/games/" + game.getCode() + "/lobby";
        
        } else if(securityService.isAuthenticatedUser() && !p.getInGame()) {
            modelMap.addAttribute("game", new Game());
            return VIEW_CREATE_OR_UPDATE_GAME_FORM;
        
        }else if(p.getInGame()) {
            Optional<Game> gameOpt =p.getGames().stream().filter(x->x.getEndTime()==null && x.isHasStarted()).findFirst();

            if(gameOpt.isPresent()){
                Game game = gameOpt.get(); //Game you are playing right now
                return "redirect:/boards/"+game.getCode();
            }
            return "/error";
        
        } else {
            return securityService.redirectToWelcome(request);
        }

    }

    @PostMapping(path = "/save")
    public String saveGame(@Valid Game game, BindingResult result, ModelMap modelMap) {   

        Deck deck = deckService.init(game.getName());
        
        if(gameService.gameHasInappropiateWords(game)){
            modelMap.put("game", game);
            modelMap.addAttribute("errorMessage", "The room's name contains inappropiate words. Please, check your language.");
            return VIEW_CREATE_OR_UPDATE_GAME_FORM;

        }
      
        if (result.hasErrors()) {
            modelMap.addAttribute("game", game);
            return VIEW_CREATE_OR_UPDATE_GAME_FORM;
        } else {

            Player currentPlayer = securityService.getCurrentPlayer();
            game.setPlayer(currentPlayer); 
            game.addPlayerinPlayers(currentPlayer);
            currentPlayer.addGameinGames(game);
            game.setDeck(deck);
            

            boardService.init(game); //INITIALIZE BOARD FOR GAME

            gameService.save(game);

            modelMap.addAttribute("game", game);
            modelMap.addAttribute("player", currentPlayer);

        }
        return "redirect:/games/"+game.getCode()+"/lobby";
    }
    
    @GetMapping(path = "/exit/{gameId}")
    public String exitGame(@PathVariable("gameId") int gameId, HttpServletRequest request) { // to exit a game (not started)

        Optional<Game> game = gameService.findGameById(gameId);
        
        if(securityService.isAuthenticatedUser()) {
            return gameService.exitGame(game, request);
        
        } else {
            return securityService.redirectToWelcome(request);
        }
        
    }
  
  
    @GetMapping(path = "/delete/{gameId}")
    public String deleteGame(@PathVariable("gameId") int gameId, ModelMap modelMap, HttpServletRequest request) {

        Optional<Game> game = gameService.findGameById(gameId); 
        securityService.insertIdUserModelMap(modelMap);
        
        return gameService.deleteGame(game, gameId, modelMap, request);
    }


    @GetMapping(path = "/{code}/lobby")
    public String lobby(@PathVariable("code") String gameCode, ModelMap model,
        HttpServletRequest request) {

        securityService.insertIdUserModelMap(model);
        Player p = securityService.getCurrentPlayer();
        Game g = gameService.findGamesByRoomCode(gameCode).iterator().next();
        boolean inGame = securityService.getCurrentPlayer().getInGame();
       
        Game gameWaiting = gameService.waitingRoom(p.getId());
        
        if(inGame && !g.getPlayers().contains(p)){ // When starting a game, as it refreshes, it detects that it is in a game and redirects to error
            return "/error";                                        //Until you finish the game you left you cannot join another one
        } else if(gameWaiting != null && (gameService.isWaitingOnRoom(p.getId()) && !gameService.waitingRoom(p.getId()).getCode().equals(gameCode))){
           
            request.getSession().setAttribute("message", "You are waiting for start a game actually, can´t join an another game");
            return publicRooms(model, request);
          
        }
		model.put("now", new Date());
        Iterable<Game> gameOpt = gameService.findGamesByRoomCode(gameCode);
        
        if(securityService.isAuthenticatedUser()) {
            return gameService.getLobby(gameOpt, model, request);
        
        } else {    
            return securityService.redirectToWelcome(request);        
        }  

    }
    
    @JsonView(Views.Public.class)
    @ResponseBody
    @RequestMapping(value = "/players/{gameId}")
    public AjaxPlayersResponseBody getGamePlayers(@PathVariable("gameId") int gameId) {

            AjaxPlayersResponseBody result = new AjaxPlayersResponseBody();

            Optional<Game> opt = gameService.findGameById(gameId);
            if(opt.isPresent()) {
                Game game = opt.get();
                List<Player> players = game.getPlayers();

                result.setCode("200");
                result.setPlayers(players);

            } else {
                result.setCode("404");
            }

            return result;
            
    }

    @JsonView(Views.Public.class)
    @ResponseBody
    @RequestMapping(value = "/game/{gameId}")
    public AjaxGameResponseBody getGame(@PathVariable("gameId") int gameId) {

            AjaxGameResponseBody result = new AjaxGameResponseBody();

            Optional<Game> opt = gameService.findGameById(gameId);
            if(opt.isPresent()) {
                Game game = opt.get();
                Integer playersNum = game.getPlayers().size();
                Integer actualPlayerId = game.getActualPlayer();

                List<Integer> playersIds = game.getPlayers().stream().map(Player::getId).collect(Collectors.toList());

                result.setCode("200");
                result.setGame(game);
                result.setNumberOfPlayers(playersNum);
                result.setActualPlayer(actualPlayerId);
                result.setPlayersIds(playersIds);

            } else {
                result.setCode("404");
            }

            return result;
    }

    /**
     *
     * @param game
     * @param result
     * @param gameId
     * @param model
     * @param name
     * @param code
     * @param model
     * @return
     */



    // ROOMS VIEW (PUBLIC ONES)
    @GetMapping(path = "/rooms")
    public String publicRooms(ModelMap modelMap, HttpServletRequest request) {
        if (securityService.isAuthenticatedUser()) {
            securityService.insertIdUserModelMap(modelMap);
             
            if (securityService.isAdmin()) {
                return adminController.rooms(modelMap, request);
            } else {
                
                return playerController.games(modelMap, request);
            }
            
        } else {

            return securityService.redirectToWelcome(request);

        }
        
    }

    //Games by room code
    @GetMapping(path = "/rooms/{code}")
    public String gameByCode(@PathVariable("code") String code, ModelMap modelMap, HttpServletRequest request) {
        
        if (securityService.isAuthenticatedUser()) {
            securityService.insertIdUserModelMap(modelMap);

            
            Iterable<Game> game = gameService.findGamesByRoomCode(code);
            
            gameService.addGamesToModelMap(game, modelMap);

            
            return  "games/publicRooms";
        } else {
            return securityService.redirectToWelcome(request);
        }

    }

    // Games currently playing
    @GetMapping(path = "/rooms/playing")
    public String currentlyPlaying(ModelMap modelMap, HttpServletRequest request) {

        Collection<Game> games;

        if(securityService.isAuthenticatedUser()) {
            securityService.insertIdUserModelMap(modelMap); 
            
                // If the user has admin perms then:
                if (securityService.isAdmin()) {
                    games = gameService.findAllPlaying();
                    modelMap.addAttribute("games", games);
                    // here we can see all the games currently being played, both public and private

                } else { 
                    games = gameService.findAllPublicPlaying();
                    modelMap.addAttribute("games", games);
                    // here we can see only the public games which are currently being played, in order to watch it by streaming

                }
            
                return "games/currentlyPlaying";
        
        } else {
            return securityService.redirectToWelcome(request);
        }

    }

}
