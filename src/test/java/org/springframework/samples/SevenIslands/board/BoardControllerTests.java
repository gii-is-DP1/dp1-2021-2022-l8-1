package org.springframework.samples.SevenIslands.board;

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
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.game.PRIVACITY;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

@WebMvcTest(controllers = BoardController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class BoardControllerTests {
    
    private static final int TEST_GAME_ID = 10;
    private static final int TEST_PLAYER_ID = 10;
    
    @Autowired
	private BoardController boardController;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BoardService boardService;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

	@MockBean
    private SecurityService securityService;

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
        firstGame.setHas_started(true);
        firstGame.setPlayer(firstPlayer);

        when(this.gameService.findGameById(TEST_GAME_ID)).thenReturn(Optional.of(firstGame));
        // when(this.securityService.getCurrentPlayerId()).thenReturn(TEST_PLAYER_ID);
        when(this.playerService.findPlayerById(TEST_PLAYER_ID)).thenReturn(Optional.of(firstPlayer));
        when(this.securityService.getCurrentPlayerId()).thenReturn(firstPlayer.getId());

        List<Game> listGames = new ArrayList<>();
        listGames.add(firstGame);

        Iterator<Game> iterator = listGames.iterator();

        Iterable<Game>iterable = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0),false).collect(Collectors.toList());

        when(gameService.findGamesByRoomCode(firstGame.getCode())).thenReturn(iterable);
        
    }




    //Method -->leave

    @WithMockUser(value = "spring")
	@Test
	void testCurrentlyPlayingIsPlayer() throws Exception {

        when(boardService.endGame(any(Game.class), any(ModelMap.class), any(HttpServletRequest.class))).thenReturn("games/endGame");

		mockMvc.perform(get("/boards/"+firstGame.getCode()+"/endGame"))
                .andExpect(status().isOk())
				.andExpect(view().name("games/endGame"));
	}

    //Method -->leave

    @WithMockUser(value = "spring")
	@Test
	void testLeaveGame() throws Exception {

        when(boardService.leaveGame(firstPlayer, firstGame.getCode())).thenReturn("redirect:/welcome");

		mockMvc.perform(get("/boards/"+firstGame.getCode()+"/leaveGame"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/welcome"));
	}


}
