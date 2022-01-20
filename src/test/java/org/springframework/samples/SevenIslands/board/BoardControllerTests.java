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
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.springframework.context.annotation.FilterType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
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
        firstGame.setHasStarted(true);
        firstGame.setPlayer(firstPlayer);
        firstGame.setPlayers(List.of(firstPlayer));

        when(this.gameService.findGameById(TEST_GAME_ID)).thenReturn(Optional.of(firstGame));
        // when(this.securityService.getCurrentPlayerId()).thenReturn(TEST_PLAYER_ID);
        when(this.playerService.findPlayerById(TEST_PLAYER_ID)).thenReturn(Optional.of(firstPlayer));
        when(this.securityService.getCurrentPlayerId()).thenReturn(firstPlayer.getId());
        when(this.securityService.getCurrentPlayer()).thenReturn(firstPlayer);

        List<Game> listGames = new ArrayList<>();
        listGames.add(firstGame);
        Iterator<Game> iterator = listGames.iterator();
        Iterable<Game>iterable = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0),false).collect(Collectors.toList());

        when(gameService.findGamesByRoomCode(firstGame.getCode())).thenReturn(iterable);
        
    }

    //Method --> init

    @WithMockUser(value = "spring")
	@Test
	void testInitBoard() throws Exception {

        when(boardService.initBoard(firstGame)).thenReturn("redirect:/boards/"+firstGame.getCode());

		mockMvc.perform(get("/boards/"+firstGame.getCode()+"/init"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()));
	}

    //Method --> init

    @WithMockUser(value = "spring")
	@Test
	void testItWorksSuccesfully() throws Exception {

        firstGame.setPlayers(List.of(firstPlayer,new Player()));

		mockMvc.perform(get("/boards/"+firstGame.getCode()))
                .andExpect(status().isOk())
				.andExpect(view().name("boards/board"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testOnlyAPlayer() throws Exception {

        when(boardService.toLobby(eq(firstGame),any(ModelMap.class),eq(firstGame.getPlayers().size()))).thenReturn("games/lobby");

		mockMvc.perform(get("/boards/"+firstGame.getCode()))
                .andExpect(status().isOk())
				.andExpect(view().name("games/lobby"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testFinishGame() throws Exception {

        when(boardService.gameLogic(eq(firstPlayer),eq(firstGame),any(ModelMap.class),any(HttpServletRequest.class))).thenReturn("redirect:/boards/"+firstGame.getCode()+"/endGame");

		mockMvc.perform(get("/boards/"+firstGame.getCode()))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()+"/endGame"));
	}

    //Method --> changeTurn

    @WithMockUser(value = "spring")
	@Test
	void testChangeTurn() throws Exception {

        when(boardService.changeTurn(firstGame)).thenReturn("redirect:/boards/"+firstGame.getCode());

		mockMvc.perform(get("/boards/"+TEST_GAME_ID+"/changeTurn"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()));
	}

    //Method -->rollDie

    @WithMockUser(value = "spring")
	@Test
	void testRollDieWhenIsNotMyTurn() throws Exception {

        firstGame.setActualPlayer(0);
        when(securityService.getCurrentPlayerId()).thenReturn(90);

		mockMvc.perform(get("/boards/"+TEST_GAME_ID+"/rollDie"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()));
	}

    @WithMockUser(value = "spring")
	@Test
	void testRollDie2Times() throws Exception {

        firstGame.setDieThrows(true);
        firstGame.setActualPlayer(0);
        when(securityService.getCurrentPlayerId()).thenReturn(TEST_PLAYER_ID);

		mockMvc.perform(get("/boards/"+TEST_GAME_ID+"/rollDie"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()));
	}

    @WithMockUser(value = "spring")
	@Test
	void testRollDieSuccesfully() throws Exception {

        firstGame.setDieThrows(false);
        firstGame.setActualPlayer(0);
        when(securityService.getCurrentPlayerId()).thenReturn(TEST_PLAYER_ID);
        when(boardService.rollDie(firstGame)).thenReturn("redirect:/boards/"+firstGame.getCode()+"/actions/"+5);
        
		mockMvc.perform(get("/boards/"+TEST_GAME_ID+"/rollDie"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()+"/actions/"+5));
	}

    //Method -->actions

    @WithMockUser(value = "spring")
	@Test
	void testActions() throws Exception {

        when(boardService.calculatePosibilities(eq(firstGame), any(HttpServletRequest.class), eq(2))).thenReturn("redirect:/boards/"+firstGame.getCode());

		mockMvc.perform(get("/boards/"+firstGame.getCode()+"/actions/"+2))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()));
	}
    //Method -->travel

    @WithMockUser(value = "spring")
	@Test
	void testDoIllegalAction() throws Exception {

        firstGame.setValueOfDie(4);
        when(boardService.doAnIllegalAction(any(),any(),any(),any(HttpServletRequest.class))).thenReturn("redirect:/boards/"+firstGame.getCode());
        
        Integer[] data = {1,2,3}; 

		mockMvc.perform(post("/boards/"+firstGame.getCode()+"/travel")
                .with(csrf())
                .param("island", "3")
                .param("pickedCards", data.toString()))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()));
	}

    @WithMockUser(value = "spring")
	@Test
	void testNotSpendCards() throws Exception {

        firstGame.setValueOfDie(4);
        when(boardService.doAnIllegalAction(any(),any(),any(),any(HttpServletRequest.class))).thenReturn("redirect:/boards/"+firstGame.getCode());
        
        mockMvc.perform(post("/boards/"+firstGame.getCode()+"/travel")
                .with(csrf())
                .param("island", "3")
                .param("pickedCards", ""))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+firstGame.getCode()));
	}

    @WithMockUser(value = "spring")
	@Test
	void testDoCorrectAction() throws Exception {

        firstGame.setValueOfDie(4);
        when(boardService.doCorrectAction(any(),any(),any(),any(HttpServletRequest.class))).thenReturn("redirect:/boards/"+TEST_GAME_ID+"/changeTurn");
        

		mockMvc.perform(post("/boards/"+firstGame.getCode()+"/travel")
                .with(csrf())
                .param("island", "4")
                .param("pickedCards", ""))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/boards/"+TEST_GAME_ID+"/changeTurn"));
	}

    //Method -->endGame

    @WithMockUser(value = "spring")
	@Test
	void testEndGame() throws Exception {

        when(boardService.endGame(eq(firstGame), any(ModelMap.class), any(HttpServletRequest.class))).thenReturn("games/endGame");

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
