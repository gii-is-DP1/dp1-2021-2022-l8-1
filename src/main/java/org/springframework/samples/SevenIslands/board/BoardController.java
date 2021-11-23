package org.springframework.samples.SevenIslands.board;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    

    @GetMapping(path = "/{code}")
    public String board(@PathVariable("code") String code, ModelMap modelMap, HttpServletResponse response) {
        String vista = "boards/board";
        gService.insertIdUserModelMap(modelMap);
        // modelMap.put("now", new Date());
		modelMap.addAttribute("board",boardService.findById(1).get());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        int playerId = playerService.getIdPlayerByName(currentUser.getUsername()); // Id of player that is logged

        Player pay = playerService.findPlayerById(playerId).get();
        modelMap.addAttribute("player", pay);

        //toArray()[0] because there is only going to be one game with that code as its UNIQUE
        modelMap.addAttribute("game", gameService.findGamesByRoomCode(code).toArray()[0]);

        return vista;
    }

    
}
