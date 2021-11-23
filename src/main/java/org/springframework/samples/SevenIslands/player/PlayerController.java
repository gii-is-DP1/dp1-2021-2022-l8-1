package org.springframework.samples.SevenIslands.player;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

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

    @Autowired	
	private GeneralService generalService;

    @GetMapping(path="/profile/{playerId}")
    public String profile(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view = "/players/profile";
        generalService.insertIdUserModelMap(modelMap);
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            modelMap.addAttribute("player", player.get());
        }else{
            modelMap.addAttribute("message", "Player not found");
            view = "/error"; //TODO: crear una vista de erro personalizada 
        }
        return view;
    }

    @GetMapping(path="/profile/{playerId}/moreStatistics")
    public String moreStatistics(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view = "players/moreStatistics";
        generalService.insertIdUserModelMap(modelMap);
        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            modelMap.addAttribute("player", player.get());
        }else{
            modelMap.addAttribute("message", "Player not found");
            view = "/error"; //TODO: crear una vista de erro personalizada 
        }
        return view;
    }

    @GetMapping(path="/all")
    public String listadoPlayers(ModelMap modelMap, @PathParam("filterName") String filterName){        //For admins
        String view ="players/listPlayers";
        generalService.insertIdUserModelMap(modelMap);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    
                    Iterable<Player> players = playerService.findAll();
                    modelMap.addAttribute("players", players);
                    modelMap.addAttribute("filterName", filterName);
            
        }else{
            view = "/errors";
        }
        return view;

    }

    //COMPROBAR
    @GetMapping(path="/new")
    public String createPlayer(ModelMap modelMap){
        String view="players/editPlayer";
        generalService.insertIdUserModelMap(modelMap);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    modelMap.addAttribute("player", new Player());
        }else{
            view = "/errors";
        }
        return view;
    }

    //COMPROBAR
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
            view=listadoPlayers(modelMap, null);
        }
        return view;
    }

    @GetMapping(path="/delete/{playerId}")
    public String deletePlayer(@PathVariable("playerId") int playerId, ModelMap modelMap){
        String view= "players/listPlayers";
        generalService.insertIdUserModelMap(modelMap);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    Optional<Player> player = playerService.findPlayerById(playerId);
                    if(player.isPresent()){
                        playerService.delete(player.get());
                        modelMap.addAttribute("message", "Player successfully deleted!");
                    }else{
                        modelMap.addAttribute("message", "Player not found");
                        view=listadoPlayers(modelMap, null);
                    }
        }else{
            view = "/errors";
        }
        return view;

    }

    private static final String VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM = "players/createOrUpdatePlayerForm";

    @GetMapping(path="/edit/{playerId}")
    public String updatePlayer(@PathVariable("playerId") int playerId, ModelMap model) {
        Optional<Player> player = playerService.findPlayerById(playerId); // optional puede ser error el import
        String view = VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
        generalService.insertIdUserModelMap(model);
        //Test if currentplayer is admin or the same id
        // TODO: Comprobar que sea o admin o q el usuer registrado tenga el mismo id q el de la url
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getAuthorities().stream()
            .anyMatch(x -> x.toString().equals("admin"))){
            if(player.isPresent()){
                model.addAttribute("player", player.get());
            }else{
                model.addAttribute("message", "Player not found");
                view = "/error"; //TODO: crear una vista de erro personalizada 
            }
        }else if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            int playerLoggedId = playerService.getIdPlayerByName(currentUser.getUsername());
            if(playerId==playerLoggedId){
                if(player.isPresent()){
                    model.addAttribute("player", player.get());
                }else{
                    model.addAttribute("message", "Player not found");
                    view = "/error"; //TODO: crear una vista de erro personalizada 
                }
            }else{
                view= "/error";
            }
        }else{
            view = "/errors";
        }
        return view;
    }

    /**
     *
     * @param player
     * @param result
     * @param playerId
     * @param model
     * @param surname
     * @param firstName
     * @param email
     * @param password
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
			BeanUtils.copyProperties(player, playerToUpdate,"id", "profilePhoto","totalGames","totalTimeGames","avgTimeGames","maxTimeGame","minTimeGame","totalPointsAllGames","avgTotalPoints","favoriteIsland","favoriteTreasure","maxPointsOfGames","minPointsOfGames","achievements","cards","watchGames","forums","games","invitations","friend_requests","players_friends","gamesCreador");  //METER AQUI OTRAS PROPIEDADES                                                                                
                    try {                    
                        this.playerService.save(playerToUpdate);                    
                    
                    } catch (Exception ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEWS_PLAYERS_CREATE_OR_UPDATE_FORM;
                    }
                    if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                    .anyMatch(x -> x.toString().equals("admin"))){
                        return "redirect:/players/all";
                    }else{
                        return "redirect:/players/profile/{playerId}";
                    }
		}
	}


    
}
