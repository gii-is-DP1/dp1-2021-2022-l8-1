package org.springframework.samples.SevenIslands.board;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.die.Die;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.statistic.StatisticService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired	
	private AdminService adminService;
    
    @Autowired
    private SecurityService securityService;

    @Autowired	
	private DeckService deckService;

    @Autowired	
	private IslandService islandService;

    @Autowired	
	private StatisticService statisticService;

    @GetMapping(path = "/{code}/init")
    public String init(@PathVariable("code") String code, ModelMap modelMap){      

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        return boardService.initBoard(game);

    }

    @GetMapping(path = "/{code}")
    public String board(@PathVariable("code") String code, ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        
        //Refresh 
        //TODO descomentar
        //response.addHeader("Refresh", "4");

        modelMap.addAttribute("message", request.getSession().getAttribute("message"));
        modelMap.addAttribute("options", request.getSession().getAttribute("options"));
        securityService.insertIdUserModelMap(modelMap);

        Game game = gameService.findGamesByRoomCode(code).iterator().next();        
		modelMap.addAttribute("board",game.getBoard()); 
    
        //Game logic
        boardService.gameLogic(securityService.getCurrentPlayer(), game, modelMap, request);
        
        if(game.getPlayers().size()==1){
            boardService.toLobby(game, modelMap, game.getPlayers().size());
        }

        boardService.addDataToModel(game, modelMap,  SecurityContextHolder.getContext().getAuthentication());
        
        request.getSession().removeAttribute("message"); 

        boardService.addPlayerAtStartToSession(game, request);

        return "boards/board";
    }

    @GetMapping(path = "/{gameId}/changeTurn")
    public String changeTurn(@PathVariable("gameId") int gameId, ModelMap modelMap) {

        Game game = gameService.findGameById(gameId).stream().findFirst().get();    
        
        return boardService.changeTurn(game);
    }

    @GetMapping(path = "/{gameId}/rollDie")
    public String rollDie(@PathVariable("gameId") int gameId, ModelMap modelMap, HttpServletRequest request) {

        //TODO Se puede quitar el atributo en game, y pasar el valor del dado al model atributte

        Game game = gameService.findGameById(gameId).stream().findFirst().get();
        String code = game.getCode();

        if(securityService.getCurrentPlayerId()!=game.getPlayers().get(game.getActualPlayer()).getId()){ 
           
            request.getSession().setAttribute("message", "It's not your turn");
            return "redirect:/boards/"+ code;
        }
        if(game.getDieThrows()){
            
            request.getSession().setAttribute("message", "You have already made a roll this turn");
            return "redirect:/boards/"+ code;
        }

        return boardService.rollDie(game);
    }

    @GetMapping(path = "/{code}/actions/{number}")
    public String actions(@PathVariable("number") int number, @PathVariable("code") String code, ModelMap modelMap, HttpServletRequest request) {

        securityService.insertIdUserModelMap(modelMap);
        Game game = gameService.findGamesByRoomCode(code).iterator().next();

        return boardService.calculatePosibilities(game, request, number);
    }
    

    @PostMapping(path = "/{code}/travel")
    public String travel(@RequestParam(name="island") Integer island,@RequestParam(value="card[]", required = false) Integer[] pickedCards,@PathVariable("code") String code, HttpServletRequest request){

    Game game = gameService.findGamesByRoomCode(code).iterator().next();
    int cardsToSpend = Math.abs(game.getValueOfDie()-island);
    
    if(pickedCards!=null){
        if(cardsToSpend != pickedCards.length){
            boardService.doAnIllegalAction(code, island, cardsToSpend, request);
        }
    }else if(pickedCards==null){
        if(cardsToSpend!=0){
            boardService.doAnIllegalAction(code, island, cardsToSpend, request);
        }
    }

        return boardService.doCorrectAction(game,island,pickedCards,request);
    }


    @GetMapping(path = "/{code}/endGame")
    public String endGame(@PathVariable("code") String code, ModelMap modelMap, HttpServletRequest request) {

        securityService.insertIdUserModelMap(modelMap);
        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        
        return boardService.endGame(game, modelMap, request);
    }

    @GetMapping(path = "/{code}/leaveGame")
    public String leave(@PathVariable("code") String code, ModelMap modelMap, HttpServletRequest request) {

        securityService.insertIdUserModelMap(modelMap);
        Player playerWhoLeft = playerService.findPlayerById(securityService.getCurrentPlayerId()).get();
        
        return boardService.leaveGame(playerWhoLeft, code);
    }

}
