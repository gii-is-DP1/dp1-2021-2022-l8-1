package org.springframework.samples.SevenIslands.game;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @GetMapping()
    public String myRooms(ModelMap modelMap) {
        String vista = "games/myRooms";
        
        Authentication authetication = SecurityContextHolder.getContext().getAuthentication();
        if(authetication != null){
            if(authetication.isAuthenticated() && authetication.getPrincipal() instanceof User){
                User currentUser = (User)authetication.getPrincipal();
                System.out.println(currentUser.getUsername());
                System.out.println(playerService.getIdPlayerByName(currentUser.getUsername()));
        }else
               return "/welcome"; //da error creo que es por que request mapping de arriba
    }



        Iterable<Game> games = gameService.findAll();
        modelMap.addAttribute("games", games);
        return vista;
    }

    @GetMapping(path = "/new")
    public String crearJuego(ModelMap modelMap) {
        String view = "games/editarJuego"; // Hacer pagina
        modelMap.addAttribute("game", new Game());
        return view;
    }

    @PostMapping(path = "/save")
    public String salvarEvento(@Valid Game game, BindingResult result, ModelMap modelMap) {
        String view = "games/myRooms";
        if (result.hasErrors()) {
            modelMap.addAttribute("game", game);
            return "games/editarJuego";
        } else {
            gameService.save(game);
            modelMap.addAttribute("message", "Game successfully saved!");
            view = myRooms(modelMap);
        }
        return view;
    }

    @GetMapping(path = "/delete/{gameId}")
    public String borrarJuego(@PathVariable("gameId") int gameId, ModelMap modelMap) {
        Optional<Game> game = gameService.findGameById(gameId); // optional puede ser error el import
        if (game.isPresent()) {
            gameService.delete(game.get());
            modelMap.addAttribute("message", "Game successfully deleted!");
        } else {
            modelMap.addAttribute("message", "Game not Found!");
        }
        String view = myRooms(modelMap);
        return view;
    }

    private static final String VIEWS_GAMES_CREATE_OR_UPDATE_FORM = "games/createOrUpdateGameForm";

    @GetMapping(path = "/edit/{gameId}")
    public String actualizarJuego(@PathVariable("gameId") int gameId, ModelMap model) {
        Game game = gameService.findGameById(gameId).get(); // optional puede ser error el import
        model.put("game", game);
        return VIEWS_GAMES_CREATE_OR_UPDATE_FORM;
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

    @PostMapping(value = "/edit/{gameId}")
	public String processUpdateForm(@Valid Game game, BindingResult result,@PathVariable("gameId") int gameId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("game", game);
			return VIEWS_GAMES_CREATE_OR_UPDATE_FORM;
		}
		else {
                    Game gameToUpdate=this.gameService.findGameById(gameId).get();
			BeanUtils.copyProperties(game, gameToUpdate, "id","game","games","code");                                                                                  
                    try {                    
                        this.gameService.save(gameToUpdate);                    
                    
                    } catch (Exception ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEWS_GAMES_CREATE_OR_UPDATE_FORM;
                    }
			return "redirect:/games";
		}



	}




    //ROOMS VIEW (PUBLIC ONES)
    @GetMapping(path = "/rooms")
    public String publicRooms(ModelMap modelMap) {
        String view = "/welcome"; // Hacer pagina
        Authentication authetication = SecurityContextHolder.getContext().getAuthentication();
        if(authetication != null){
            System.out.println("\n\n\n\n" + authetication.getPrincipal());
            if(authetication.isAuthenticated() && authetication.getPrincipal() instanceof User){
                //If the user has admin perms then:
                if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(x->x.toString().equals("admin"))){
                    view = "games/publicRoomsAdmins"; // Hacer pagina
                }else{
                    view = "games/publicRooms"; // Hacer pagina
                }
        }else{
            return "welcome"; //da error creo que es por que request mapping de arriba
        }    
        }       
        Iterable<Game> games = gameService.findAllPublic();
        modelMap.addAttribute("games", games);
        return view;
    }

}
