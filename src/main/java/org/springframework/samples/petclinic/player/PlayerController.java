package org.springframework.samples.petclinic.player;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/players")
public class PlayerController {


    @Autowired
    private PlayerService playerService;

    @GetMapping()
    public String listadoPlayers(ModelMap modelMap){
        String vista ="players/listadoPlayers";
        Iterable<Player> players = playerService.findAll();
        modelMap.addAttribute("players", players);
        return vista;

    }

    @GetMapping(path="/new")
    public String createPlayer(ModelMap modelMap){
        String view="players/editPlayer";
        modelMap.addAttribute("player", new Player());
        return view;
    }

    @PostMapping(path="/save")
    public String savePlayer(@Valid Player player, BindingResult result, ModelMap modelMap){
        String view= "players/listPlayers";
        if(result.hasErrors()){
            modelMap.addAttribute("player", player);
            return "players/editPlayer";
        }else{
            playerService.save(player);
            modelMap.addAttribute("message", "Player succesfully saved!");
        }
        return view;
    }

    @GetMapping(path="/delete/{playerId}")
    public String deletePlayer(@PathParam("playerId") int playerId, ModelMap modelMap){
        String view= "players/listPlayers";
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            playerService.delete(player.get());
            modelMap.addAttribute("message", "Player successfully deleted!");
        }else{
            modelMap.addAttribute("message", "Player not found");
        }
        return view;

        return view;
    }


    
}
