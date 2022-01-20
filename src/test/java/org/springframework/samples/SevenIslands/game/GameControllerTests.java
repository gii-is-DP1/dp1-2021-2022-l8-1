package org.springframework.samples.SevenIslands.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.admin.AdminController;
import org.springframework.samples.SevenIslands.board.BoardService;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerController;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.springframework.context.annotation.FilterType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;


@WebMvcTest(controllers = GameController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class GameControllerTests {
    
    private static final int TEST_GAME_ID = 10;
    private static final int TEST_PLAYER_ID = 10;

	@Autowired
	private GameController gameController;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BoardService boardService;

    @MockBean
    private GameService gameService;

	@MockBean
    private SecurityService securityService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private AdminController adminController;

    @MockBean
    private PlayerController playerController;

    @MockBean
    private DeckService deckService;

	private Game firstGame;
    private Player firstPlayer;

    @BeforeEach
	void setup() {

        firstPlayer = new Player();
        firstPlayer.setId(TEST_PLAYER_ID);
        firstPlayer.setProfilePhoto("https://imagen.png");
        firstPlayer.setFirstName("Manuel");
        firstPlayer.setSurname("González");
        firstPlayer.setEmail("manuelgonzalez@gmail.com");

		firstGame = new Game();
		firstGame.setId(TEST_GAME_ID);
        firstGame.setName("First Game");
        firstGame.setCode("AHG28FD9J");
        firstGame.setPrivacity(PRIVACITY.PUBLIC);
        firstGame.setHasStarted(true);
        firstGame.setPlayer(firstPlayer);

        when(this.gameService.findGameById(TEST_GAME_ID)).thenReturn(Optional.of(firstGame));
        // when(this.securityService.getCurrentPlayerId()).thenReturn(TEST_PLAYER_ID);
        when(this.securityService.getCurrentPlayer()).thenReturn(firstPlayer);
        when(securityService.redirectToWelcome(any(HttpServletRequest.class))).thenReturn("redirect:/welcome");
	}

    //Method -> createGame
 
    @WithMockUser(value = "spring")
    @Test
    void testAdminJoinToGame() throws Exception {
 
        when(securityService.isAdmin()).thenReturn(true);
 
        mockMvc.perform(get("/games/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games/rooms"));
    }
 
    @WithMockUser(value = "spring")
    @Test
    void testPlayerIsWaitingOnRoom() throws Exception {
 
        when(this.securityService.getCurrentPlayer()).thenReturn(firstPlayer);
        when(this.gameService.isWaitingOnRoom(any(Integer.class))).thenReturn(true);
        when(this.gameService.waitingRoom(any(Integer.class))).thenReturn(firstGame);
 
        mockMvc.perform(get("/games/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games/AHG28FD9J/lobby"));
    }
 
    @WithMockUser(value = "spring")
    @Test
    void testPlayerCreatesGameSuccessfully() throws Exception {
 
        firstPlayer.setInGame(false);
 
        when(this.securityService.getCurrentPlayer()).thenReturn(firstPlayer);
        when(securityService.isAuthenticatedUser()).thenReturn(true);
 
        mockMvc.perform(get("/games/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("games/createOrUpdateGameForm"));
    }

    @Disabled
    @WithMockUser(value = "spring")
    @Test
    void testPlayerIsInGame() throws Exception {
 
        firstPlayer.setInGame(true);
        firstGame.setHasStarted(true);
        //firstGame.setEndTime(null);
        Collection <Game> cl = new ArrayList<>();
        cl.add(firstGame);

        Player player = mock(Player.class);
        
        when(this.securityService.getCurrentPlayer()).thenReturn(firstPlayer);
        when(player.getGames()).thenReturn(cl);
 
        mockMvc.perform(get("/games/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("games/createOrUpdateGameForm"));
    }

    @WithMockUser(value = "spring")
    @Test
    void testPlayerToWelcome() throws Exception {

        mockMvc.perform(get("/games/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/welcome"));
    }
 
    //Method -> saveGame

    @WithMockUser(value = "spring")
	@Test
	void testCreationGameInappropiateWords() throws Exception {

        when(this.gameService.gameHasInappropiateWords(any())).thenReturn(true);

		mockMvc.perform(post("/games/save")
                .with(csrf())
                .param("name", "Second Game shit")
                .param("code", "AHG28FD8J")
                .param("privacity", PRIVACITY.PUBLIC.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("game"))
                .andExpect(model().attributeExists("errorMessage"))
				.andExpect(view().name("games/createOrUpdateGameForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testCreationGameWithErrors() throws Exception {

		mockMvc.perform(post("/games/save")
                .with(csrf())
                .param("name", "")
                .param("code", "AHG28FD8J")
                .param("privacity", PRIVACITY.PUBLIC.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("game"))
				.andExpect(view().name("games/createOrUpdateGameForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testCreationGameSuccessfully() throws Exception {

        Player player = mock(Player.class);

        when(this.securityService.getCurrentPlayer()).thenReturn(player);

		mockMvc.perform(post("/games/save")
                .with(csrf())
                .param("name", "New Game")
                .param("code", "AHG28FD8J")
                .param("privacity", PRIVACITY.PUBLIC.toString()))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/games/AHG28FD8J/lobby"));
	}

    //Method -> exitGame

    @WithMockUser(value = "spring")
	@Test
	void testExitGameSuccessfully() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(true);
        when(gameService.exitGame(eq(Optional.of(firstGame)),any(HttpServletRequest.class))).thenReturn("redirect:/games/rooms");

		mockMvc.perform(get("/games/exit/{gameId}",TEST_GAME_ID))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/games/rooms"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testExitGameNotAuthenticatedUser() throws Exception {

        mockMvc.perform(get("/games/exit/{gameId}",TEST_GAME_ID))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}

    //Method -> deleteGame

    @WithMockUser(value = "spring")
	@Test
	void testDeleteGame() throws Exception {

        when(gameService.deleteGame(eq(Optional.of(firstGame)),eq(TEST_GAME_ID),any(ModelMap.class),any(HttpServletRequest.class))).thenReturn("redirect:/welcome");

		mockMvc.perform(get("/games/delete/{gameId}",TEST_GAME_ID))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}

    //Method -> publicRooms

    @WithMockUser(value = "spring")
	@Test
	void testPublicRoomsIsAdmin() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(true);
        when(securityService.isAdmin()).thenReturn(true);        
        when(adminController.rooms(any(ModelMap.class),any(HttpServletRequest.class))).thenReturn("admins/rooms");

		mockMvc.perform(get("/games/rooms"))
                .andExpect(status().isOk())
				.andExpect(view().name("admins/rooms"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPublicRoomsIsPlayer() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(true);    
        when(playerController.games(any(ModelMap.class),any(HttpServletRequest.class))).thenReturn("games/publicRooms");

		mockMvc.perform(get("/games/rooms"))
                .andExpect(status().isOk())
				.andExpect(view().name("games/publicRooms"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPublicRoomsIsNotAuthenticatedUser() throws Exception {

		mockMvc.perform(get("/games/rooms"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}

    //Method -> gameByCode

    @WithMockUser(value = "spring")
	@Test
	void testAuthenticatedUserFindGame() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(true);
       
		mockMvc.perform(get("/games/rooms/{code}",firstGame.getCode()))
                .andExpect(status().isOk())
				.andExpect(view().name("games/publicRooms"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testNotAuthenticatedUserFindGame() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(false);

		mockMvc.perform(get("/games/rooms/{code}",firstGame.getCode()))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}

    //Method -> currentlyPlaying

    @WithMockUser(value = "spring")
	@Test
	void testCurrentlyPlayingIsNotAuthenticatedUser() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(false);

		mockMvc.perform(get("/games/rooms/playing"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testCurrentlyPlayingIsAdmin() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(true);
        when(securityService.isAdmin()).thenReturn(true);

		mockMvc.perform(get("/games/rooms/playing"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/currentlyPlaying"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testCurrentlyPlayingIsPlayer() throws Exception {

        when(securityService.isAuthenticatedUser()).thenReturn(true);

		mockMvc.perform(get("/games/rooms/playing"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/currentlyPlaying"));
	}

}
