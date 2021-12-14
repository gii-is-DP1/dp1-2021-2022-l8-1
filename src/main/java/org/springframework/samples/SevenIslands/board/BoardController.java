package org.springframework.samples.SevenIslands.board;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.die.Die;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    @GetMapping(path = "/{code}/init")
    public String init(@PathVariable("code") String code, ModelMap modelMap){      

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        Board board = game.getBoard();
        
        
        //List<Player> players = game.getPlayers();
        //Deck d = game.getDeck();
        
        boardService.initCardPlayers(game);
        boardService.distribute(board, game.getDeck());
        game.setBoard(board);
        gameService.save(game);     
        
        return "redirect:/boards/"+ code;
    }

    @GetMapping(path = "/{code}")
    public String board(@PathVariable("code") String code, ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        
        //Refresh 
        //TODO descomentar
        //response.addHeader("Refresh", "4");

        modelMap.addAttribute("message", request.getSession().getAttribute("message"));
        modelMap.addAttribute("options", request.getSession().getAttribute("options"));
        
        String view = "boards/board";
        securityService.insertIdUserModelMap(modelMap);

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        Board b = game.getBoard();
        
		modelMap.addAttribute("board",b); 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//contador mod(n_jugadores) empieza el jugador 0
      
        
        
        if(!game.isHas_started()){
            List<Player> p = game.getPlayers();
            Collections.shuffle(p);
            game.setPlayers(p);
            game.setNumberOfTurn(0);
            game.setActualPlayer(0);
            game.setHas_started(true);
            game.setTurnTime(LocalDateTime.now());

        }else if(game.getActualPlayer()!=(game.getNumberOfTurn()%game.getPlayers().size())){ //para que aunque refresque uno que no es su turno no incremente el turno
            game.setNumberOfTurn(game.getNumberOfTurn()+1);
            game.setTurnTime(LocalDateTime.now());
            game.setDieThrows(false);
            request.getSession().removeAttribute("message");
            request.getSession().removeAttribute("options");
            
        }else if(ChronoUnit.SECONDS.between(game.getTurnTime(), LocalDateTime.now())>=3600){  //Turn finished by time
            //Same code in changeTurn
            Integer n = game.getPlayers().size();
            game.setNumberOfTurn(game.getNumberOfTurn()+1);
            game.setActualPlayer((game.getActualPlayer()+1)%n);
            game.setDieThrows(false);       //No sabemos si tiró dado o no
            game.setTurnTime(LocalDateTime.now());
            request.getSession().removeAttribute("message");
            request.getSession().removeAttribute("options");

        }

        gameService.save(game);
        modelMap.addAttribute("id_playing", game.getPlayers().get(game.getActualPlayer()).getId());
        Long temp = ChronoUnit.SECONDS.between(game.getTurnTime(), LocalDateTime.now());
        modelMap.addAttribute("tempo", 3600-temp.intValue());
        int n =  game.getPlayers().size();     
        if(n==1){

            //TODO Mirar como se puede juntar este código con el de lobby en GameController 

            modelMap.addAttribute("message", "The game cannot start, there is only one player in the room");
            modelMap.addAttribute("game", game);
            
            int playerId = securityService.getCurrentPlayerId(); // Id of player that is logged

            Player pay = playerService.findPlayerById(playerId).get();
            modelMap.addAttribute("player", pay);
            modelMap.addAttribute("totalplayers", n);
            return "games/lobby";
        }

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .anyMatch(x -> x.toString().equals("admin"))) {

            User currentUser = (User) authentication.getPrincipal();
            int adminId = adminService.getIdAdminByName(currentUser.getUsername()); // Id of admin that is logged
            Admin a = adminService.findAdminById(adminId).get();
            modelMap.addAttribute("player", a);

        }else{
            User currentUser = (User) authentication.getPrincipal();
            int playerId = playerService.getIdPlayerByName(currentUser.getUsername()); // Id of player that is logged
            Player pay = playerService.findPlayerById(playerId).get();
            modelMap.addAttribute("player", pay);
        }
        

        modelMap.addAttribute("game", gameService.findGamesByRoomCode(code).iterator().next());

        request.getSession().removeAttribute("message");    // to delete the message "to travel to island  X you must use Y cards"

        return view;
    }

    @GetMapping(path = "/{gameId}/changeTurn")
    public String changeTurn(@PathVariable("gameId") int gameId, ModelMap modelMap) {

        Game game = gameService.findGameById(gameId).stream().findFirst().get();
        String code = game.getCode();

        Integer n = game.getPlayers().size();

        game.setActualPlayer((game.getActualPlayer()+1)%n);
        gameService.save(game);

        return "redirect:/boards/"+ code;
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

        Die d = new Die();
        int res = d.roll();

        game.setDieThrows(true);
        game.setValueOfDie("Actual value: "+res);
        gameService.save(game);      

        // modelMap.addAttribute("die",res);
        // this.board(code,modelMap,response);
        //return "redirect:/boards/"+ code;
        return "redirect:/boards/"+code+"/actions/"+ res;
    }

    @GetMapping(path = "/{code}/actions/{number}")
    public String actions(@PathVariable("number") int number, @PathVariable("code") String code, ModelMap modelMap, HttpServletRequest request) {

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        int cartasActualmente = game.getPlayers().get(game.getActualPlayer()).getCards().size();
        int m = cartasActualmente;
        
        int limitSup = number + m;
        int limitInf = number - m;
        if(limitSup>7){
            limitSup = 7;
        }
        if(limitInf<=0){
            limitInf = 1;
        }
        List<Integer> posib = IntStream.rangeClosed(limitInf, limitSup)//2 a 7
            .boxed().collect(Collectors.toList());
        
        List<Integer> posibilities = new ArrayList<>();
        for(Integer i: posib){
            if(i>=limitInf-1 && i<=limitSup-1){
                if(game.getBoard().getIslands().get(i-1).getCard()!=null){
                    posibilities.add(i);
                }
            }else if(i==7){
                if(game.getDeck().getCards()!=null){
                    posibilities.add(i);
                }
            }
        
        }    //List<Integer> posibilities = posib.stream().filter(x->game.getBoard().getIslands().get(x).getCard()!=null).collect(Collectors.toList());//COMPROBAR ESTA LINEA PARA MOSTRAR SOLO AQUELLAS ISLAS QUE TENGAN CARTAS

        
        request.getSession().setAttribute("options", posibilities);
        return "redirect:/boards/"+ code;
    }
    
    /*@GetMapping(path = "/{code}/Island/{option}")
    public String chooseIsland(@PathVariable("option") int option, @PathVariable("code") String code, ModelMap modelMap, HttpServletRequest request) {

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        int cartasActualmente = game.getPlayers().get(game.getActualPlayer()).getCards().size();
        List<String> cards = game.getPlayers().get(game.getActualPlayer()).getCards().stream().map(x->x.getCardType().toString()).collect(Collectors.toList());
        int m = cartasActualmente;
        
        int cartasAGastar = Math.abs(Integer.parseInt(game.getValueOfDie().replace("Actual value: ", ""))-option);
        Set<List<Card>> s = new TreeSet<>();

        
        
       
        return "redirect:/boards/"+ code;
    }*/

     @PostMapping(path = "/{code}/travel")
     public String travel(@RequestParam(name="island") Integer island,@RequestParam(value="card[]", required = false) Integer[] l,@PathVariable("code") String code, HttpServletRequest request){

        

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        int cardsToSpend = Math.abs(Integer.parseInt(game.getValueOfDie().replace("Actual value: ", ""))-island); //AQUI TENGO LAS CARTAS QUE TENGO QUE GASTAR
        
        if(l!=null){
            if(cardsToSpend != l.length){
                request.getSession().setAttribute("message", "To travel to island "+island+ " you must use "+cardsToSpend +" cards");
                return "redirect:/boards/"+ code;
            }
        }else if(l==null){
            if(cardsToSpend!=0){
                request.getSession().setAttribute("message", "To travel to island "+island+ " you must use "+cardsToSpend +" cards");
                return "redirect:/boards/"+ code;
            }
           
        }
       
        
        
        Player actualP = playerService.findPlayerById(game.getPlayers().get(game.getActualPlayer()).getId()).get();
        
        List<Card> now = actualP.getCards();
        if(l!=null){
            for(int i=0; i<l.length;i++){
                int n = l[i];
                now.remove(now.stream().filter(x->x.getId()==n).findFirst().get()); 
            }
        }       

        if(island<7){
            Deck d = game.getDeck();
            Card c = d.getCards().stream().findFirst().get();
            Island is = game.getBoard().getIslands().get(island-1);
            now.add(game.getBoard().getIslands().get(island-1).getCard());
            is.setCard(c);
            d.deleteCards(c);
            deckService.save(d);
            islandService.save(is);
            
        }else{
            Deck d = game.getDeck();
            Card c = d.getCards().stream().findFirst().get();
            now.add(c);
            d.deleteCards(c);
            deckService.save(d);
            
        }
        actualP.setCards(now);
        playerService.save(actualP);
        return "redirect:/boards/"+game.getId()+"/changeTurn";
      
     }

}
