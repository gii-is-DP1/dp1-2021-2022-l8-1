package org.springframework.samples.SevenIslands.board;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Controller;
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
    

    @GetMapping(path = "/{code}")
    public String board(@PathVariable("code") String code, Map<String, Object> model, HttpServletResponse response) {
        String vista = "boards/board";
        
        model.put("now", new Date());
		model.put("board",boardService.findById(1).get());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        int playerId = playerService.getIdPlayerByName(currentUser.getUsername()); // Id of player that is logged

        Player pay = playerService.findPlayerById(playerId).get();
        model.put("player", pay);

        //toArray()[0] because there is only going to be one game with that code as its UNIQUE
        model.put("game", gameService.findGamesByRoomCode(code).toArray()[0]);

        return vista;
    }

    
}
