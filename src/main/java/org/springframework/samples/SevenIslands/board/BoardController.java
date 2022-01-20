package org.springframework.samples.SevenIslands.board;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private SecurityService securityService;

    private static final String MESSAGE = "message";
    private static final String ERROR = "/error";

    @GetMapping(path = "/{code}/init")
    public String init(@PathVariable("code") String code, ModelMap modelMap){      

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        return boardService.initBoard(game);

    }

    @GetMapping(path = "/{code}")
    public String board(@PathVariable("code") String code, ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {

        modelMap.addAttribute(MESSAGE, request.getSession().getAttribute(MESSAGE));
        modelMap.addAttribute("options", request.getSession().getAttribute("options"));
        securityService.insertIdUserModelMap(modelMap);

        Game game = gameService.findGamesByRoomCode(code).iterator().next();        
		modelMap.addAttribute("board",game.getBoard()); 
    
        //Game logic
        String view = boardService.gameLogic(securityService.getCurrentPlayer(), game, modelMap, request);
        
        if(view!=null){
            return view;
        }

        if(game.getPlayers().size()==1){
            return boardService.toLobby(game, modelMap, game.getPlayers().size());
        }

        boardService.addDataToModel(game, modelMap,  SecurityContextHolder.getContext().getAuthentication());
        
        request.getSession().removeAttribute(MESSAGE); 

        boardService.addPlayerAtStartToSession(game, request);

        return "boards/board";
    }

    @GetMapping(path = "/{gameId}/changeTurn")
    public String changeTurn(@PathVariable("gameId") int gameId, ModelMap modelMap) {

        Optional<Game> opt = gameService.findGameById(gameId).stream().findFirst();
        if(opt.isPresent()) {
            return boardService.changeTurn(opt.get());
        
        } else {
            return ERROR;
        }

    }

    @GetMapping(path = "/{gameId}/rollDie")
    public String rollDie(@PathVariable("gameId") int gameId, ModelMap modelMap, HttpServletRequest request) {

        Optional<Game> opt = gameService.findGameById(gameId).stream().findFirst();
        if(!opt.isPresent()) {
            return ERROR;
        
        } else {
            Game game = opt.get();
            String code = game.getCode();

            if(securityService.getCurrentPlayerId()!=game.getPlayers().get(game.getActualPlayer()).getId()){ 
            
                request.getSession().setAttribute(MESSAGE, "It's not your turn");
                return "redirect:/boards/"+ code;
            }
            if(game.getDieThrows()){
                
                request.getSession().setAttribute(MESSAGE, "You have already made a roll this turn");
                return "redirect:/boards/"+ code;
            }

            return boardService.rollDie(game);
        }
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
    
    if((pickedCards!=null && cardsToSpend != pickedCards.length) || (pickedCards==null && cardsToSpend!=0)){
        return boardService.doAnIllegalAction(code, island, cardsToSpend, request);
        
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
        Optional<Player> opt = playerService.findPlayerById(securityService.getCurrentPlayerId());

        if(opt.isPresent()) {
            Player playerWhoLeft = opt.get();
            return boardService.leaveGame(playerWhoLeft, code);
        } else {
            return ERROR;
        }
       
    }

}
