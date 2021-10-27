package org.springframework.samples.petclinic.game;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping()
    public String listadoPartidas(ModelMap modelMap){
        String vista ="games/listadoPartidas";
        Iterable<Game> games = gameService.findAll();
        modelMap.addAttribute("games" , games);
        return vista;
    }

    @GetMapping(path="/new")
    public String crearJuego(ModelMap modelMap){
        String view = "games/editarJuego"; //Hacer pagina
        modelMap.addAttribute("game", new Game());
        return view;
    }

    @PostMapping(path="/save")
    public String salvarEvento(@Valid Game game, BindingResult result,ModelMap modelMap){
        String view = "games/listadoPartidas";
        if(result.hasErrors())
        {
            modelMap.addAttribute("game", game);
            return "games/editarJuego";
        }else{
            gameService.save(game);
            modelMap.addAttribute("message","Game successfully saved!");
            view = listadoPartidas(modelMap);
        }
        return view;
    }

    @GetMapping(path="/delete/{gameId}")
    public String borrarJuego(@PathVariable("gameId") int gameId,ModelMap modelMap){
        Optional<Game> game = gameService.findGameById(gameId); //optional puede ser error el import
        if(game.isPresent()){
            gameService.delete(game.get());
            modelMap.addAttribute("message","Game successfully deleted!");
        }else{
            modelMap.addAttribute("message","Game not Found!");
        }
        String view = listadoPartidas(modelMap);
        return view;        
    }

}
