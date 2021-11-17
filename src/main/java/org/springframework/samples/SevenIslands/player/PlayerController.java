package org.springframework.samples.SevenIslands.player;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        String vista ="players/listPlayers";
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
            System.out.print(result.getAllErrors());
            modelMap.addAttribute("player", player);
            return "players/editPlayer";
        }else{
            playerService.save(player);
            modelMap.addAttribute("message", "Player succesfully saved!");
            view=listadoPlayers(modelMap);
        }
        return view;
    }

    @GetMapping(path="/delete/{playerId}")
    public String deletePlayer(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view= "players/listPlayers";
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            playerService.delete(player.get());
            modelMap.addAttribute("message", "Player successfully deleted!");
        }else{
            modelMap.addAttribute("message", "Player not found");
            view=listadoPlayers(modelMap);
        }
        return view;

    }

    private static final String VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM = "players/createOrUpdatePlayerForm";

    @GetMapping(path="/edit/{playerId}")
    public String updatePlayer(@PathVariable("playerId") int playerId, ModelMap model) {
        Player player = playerService.findPlayerById(playerId).get(); // optional puede ser error el import
        model.put("player", player);
        return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
    }

    /**
     *
     * @param player
     * @param result
     * @param playerId
     * @param model
     * @param surname
     * @param firstName
     * @param model
     * @return
     */

    @PostMapping(value = "/edit/{playerId}")
	public String processUpdateForm(@Valid Player player, BindingResult result,@PathVariable("playerId") int playerId, ModelMap model) {
		if (result.hasErrors()) {
            System.out.print(result.getAllErrors());
			model.put("player", player);
			return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
		}
		else {
                    Player playerToUpdate=this.playerService.findPlayerById(playerId).get();
			BeanUtils.copyProperties(player, playerToUpdate, "id","player","players","code");                                                                                  
                    try {                    
                        this.playerService.save(playerToUpdate);                    
                    
                    } catch (Exception ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
                    }
			return "redirect:/players";
		}
	}


    
}
