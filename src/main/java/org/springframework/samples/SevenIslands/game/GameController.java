package org.springframework.samples.SevenIslands.game;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.player.Player;
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

    @Autowired	
	private GeneralService gService;

    //Games createdByMe
    @GetMapping()
    public String myRooms(ModelMap modelMap) {
        String vista = "games/myRooms";
        gService.insertIdUserModelMap(modelMap);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
                User currentUser = (User) authentication.getPrincipal();

                if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .anyMatch(x -> x.toString().equals("admin"))) {

                    Iterable<Game> games = gameService.findAll(); // ESTO BUSCA TODOS LOS JUEGOS, PORQUE SOY ADMIN
                    modelMap.addAttribute("games", games);
                    vista = "games/RoomsAdmins";
                    
                } else {
                    int playerId = playerService.getIdPlayerByName(currentUser.getUsername()); // AQUI CONSIGO EL ID DEL
                                                                                               // JUGADOR QUE ESTA AHORA
                                                                                               // MISMO CONECTADO

                    Collection<Game> games = gameService.findGamesByPlayerId(playerId); // ESTO BUSCA TODOS LOS JUEGOS DE LOS QUE SOY DUEÑO                               

                    modelMap.addAttribute("titletext", "Rooms created by me");
                    modelMap.addAttribute("games", games);
                }
                return vista;

            } else
              
                return "/welcome";

        }

        return vista;
    }

    
    //Games whereIPlayed
    @GetMapping("/playedByMe")
    public String roomsPlayedByMe(ModelMap modelMap) {
        String vista = "games/myRooms";
        gService.insertIdUserModelMap(modelMap);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
                User currentUser = (User) authentication.getPrincipal();

                if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .anyMatch(x -> x.toString().equals("admin"))) {

                    Iterable<Game> games = gameService.findAll(); // ESTO BUSCA TODOS LOS JUEGOS, PORQUE SOY ADMIN
                    modelMap.addAttribute("games", games);
                    vista = "games/RoomsAdmins";
                    
                } else {
                    int playerId = playerService.getIdPlayerByName(currentUser.getUsername()); // AQUI CONSIGO EL ID DEL
                                                                                               // JUGADOR QUE ESTA AHORA
                                                                                               // MISMO CONECTADO
                    
                    List<Game> gameWhereIPlayed = gameService.findGamesWhereIPlayerByPlayerId(playerId);                                                                    
                    
                    modelMap.addAttribute("titletext", "Rooms where I played");
                    modelMap.addAttribute("games", gameWhereIPlayed);
                }
                return vista;

            } else
                return "/welcome";
        }

        return vista;
    }

    @GetMapping(path = "/new")
    public String crearJuego(Player player, ModelMap modelMap) {
        String view = "games/editarJuego"; 
        gService.insertIdUserModelMap(modelMap);
        modelMap.addAttribute("game", new Game());
        return view;
    }

    @PostMapping(path = "/save")
    public String salvarEvento(@Valid Game game, BindingResult result, ModelMap modelMap) {
        String view = "games/lobby";
        if (result.hasErrors()) {
            modelMap.addAttribute("game", game);
            return "games/editarJuego";
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User currentUser = (User) authentication.getPrincipal();

            game.setPlayer(playerService.getPlayerByName(currentUser.getUsername()).stream().findFirst().get()); 
            // ESTO ES PARA QUE EN LA TABLA DE QUIEN ES EL CREADOR DE UN JUEGO SALGA DICHA RELACIÓN

            Game juego = game;
            Player jugador = playerService.getPlayerByName(currentUser.getUsername()).stream().findFirst().get();

            juego.addPlayerinPlayers(jugador);
            jugador.addGameinGames(juego);

            gameService.save(juego);

            modelMap.addAttribute("game", juego);
            modelMap.addAttribute("player", jugador);

        }
        return view;
    }

    @GetMapping(path = "/delete/{gameId}")
    public String borrarJuego(@PathVariable("gameId") int gameId, ModelMap modelMap) {
        Optional<Game> game = gameService.findGameById(gameId); 
        gService.insertIdUserModelMap(modelMap);
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
        Game game = gameService.findGameById(gameId).get();
        gService.insertIdUserModelMap(model); 
        model.put("game", game);
        return VIEWS_GAMES_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping(path = "/{gameId}/lobby")
    public String salaJuego(@PathVariable("gameId") int gameId, ModelMap model) {

        String view = "games/lobby";
        gService.insertIdUserModelMap(model);
        if (gameService.findGameById(gameId).isPresent()) {
            Game game = gameService.findGameById(gameId).get(); 
            model.addAttribute("game", game);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = (User) authentication.getPrincipal();
            int playerId = playerService.getIdPlayerByName(currentUser.getUsername()); // Id of player that is logged

            Player pay = playerService.findPlayerById(playerId).get();
            model.addAttribute("player", pay);

            view = "games/lobby";
        } else {
            view = "/errors"; // TODO
        }

        return view;
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
    public String processUpdateForm(@Valid Game game, BindingResult result, @PathVariable("gameId") int gameId,
            ModelMap model) {
        if (result.hasErrors()) {
            model.put("game", game);
            return VIEWS_GAMES_CREATE_OR_UPDATE_FORM;
        } else {
            Game gameToUpdate = this.gameService.findGameById(gameId).get();
            BeanUtils.copyProperties(game, gameToUpdate, "id", "game", "games", "code");
            try {
                this.gameService.save(gameToUpdate);

            } catch (Exception ex) {
                result.rejectValue("name", "duplicate", "already exists");
                return VIEWS_GAMES_CREATE_OR_UPDATE_FORM;
            }
            return "redirect:/games";
        }

    }

    // ROOMS VIEW (PUBLIC ONES)
    @GetMapping(path = "/rooms")
    public String publicRooms(ModelMap modelMap) {

        String view = "/welcome";
        gService.insertIdUserModelMap(modelMap);

        Iterable<Game> games;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
                // If the user has admin perms then:
                if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .anyMatch(x -> x.toString().equals("admin"))) {
                    view = "games/RoomsAdmins"; 
                    games = gameService.findAll();
                    modelMap.addAttribute("games", games);
                } else {

                    view = "games/publicRooms";

                    games = gameService.findAllPublic();
                    modelMap.addAttribute("games", games);
                }
            } else {

                return "redirect:/welcome"; 

            }
        }
        return view;
    }

    //Games by room code
    @GetMapping(path = "/rooms/{code}")
    public String gameByCode(@PathVariable("code") String code, ModelMap modelMap) {
        String view = "/welcome";
        gService.insertIdUserModelMap(modelMap);
        Iterable<Game> games;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {

            view = "games/publicRooms";
            games = gameService.findGamesByRoomCode(code);
            modelMap.addAttribute("games", games);
        
        } else {
            return "welcome"; 
        }
        return view;
    }

    // Games currently playing
    @GetMapping(path = "/rooms/playing")
    public String currentlyPlaying(ModelMap modelMap) {

        gService.insertIdUserModelMap(modelMap);
        Collection<Game> games;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
                // If the user has admin perms then:
                if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .anyMatch(x -> x.toString().equals("admin"))) {
                    games = gameService.findAllPlaying();
                    modelMap.addAttribute("games", games);
                    // here we can see all the games currently being played, both public and private

                } else { 
                    games = gameService.findAllPublicPlaying();
                    modelMap.addAttribute("games", games);
                    // here we can see only the public games which are currently being played, in order to watch it by streaming

                }
            }

        }

        return "games/currentlyPlaying";  

    }

}
