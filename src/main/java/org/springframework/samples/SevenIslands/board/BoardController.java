package org.springframework.samples.SevenIslands.board;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;

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
	private GeneralService gService;

    @Autowired	
	private AdminService adminService;
    
    @Autowired
    private SecurityService securityService;

    @GetMapping(path = "/{code}")
    public String board(@PathVariable("code") String code, ModelMap modelMap, HttpServletResponse response) {
        String view = "boards/board";
        gService.insertIdUserModelMap(modelMap);
        // modelMap.put("now", new Date());
		modelMap.addAttribute("board",boardService.findById(1).get()); 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//contador mod(n_jugadores) empieza el jugador 0
        // Game game = gameService.findGamesByRoomCode(code).stream().findFirst().get();
        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        
        if(!game.isHas_started()){
            List<Player> p = game.getPlayers();
            Collections.shuffle(p);
            game.setPlayers(p);
            game.setNumberOfTurn(0);
            game.setActualPlayer(0);
            game.setHas_started(true);
        }else if(game.getActualPlayer()!=(game.getNumberOfTurn()%game.getPlayers().size())){ //para que aunque refresque uno que no es su turno no incremente el turno
            game.setNumberOfTurn(game.getNumberOfTurn()+1);
        }
        gameService.save(game);
        modelMap.addAttribute("id_playing", game.getPlayers().get(game.getActualPlayer()).getId());

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
        

        // .iterator().next() because there is only going to be one game with that code as its UNIQUE
        modelMap.addAttribute("game", gameService.findGamesByRoomCode(code).iterator().next());

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

    
}
