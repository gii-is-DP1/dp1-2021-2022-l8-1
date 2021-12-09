package org.springframework.samples.SevenIslands.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers = GameController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class GameControllerTests {
    
    private static final int TEST_GAME_ID = 10;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private GameService gameService;

    @BeforeEach
	void setup() {

		Game firstGame = new Game();
		firstGame.setId(TEST_GAME_ID);
        firstGame.setName("First Game");
        firstGame.setCode("AHG28FD9J");
        firstGame.setPrivacity(PRIVACITY.PUBLIC);
        //gameService.save(firstGame);
		//given(this.gameService.findGameById(TEST_GAME_ID).get()).willReturn(firstGame);

	}

    @WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/games/new")).andExpect(status().isOk()).andExpect(model().attributeExists("game"))
				.andExpect(view().name("games/createOrUpdateGameForm"));
	}

	/*@WithMockUser(value = "spring")
	@Test
	void testJoinLobby() throws Exception {
		mockMvc.perform(get("/{code}/lobby", "AHG28FD9J")).andExpect(status().isOk())
				.andExpect(view().name("games/lobby"));
	}*/

	/*@WithMockUser(value = "spring")
	@Test
	void testExitGame() throws Exception {
		mockMvc.perform(get("/exit/{gameId}", TEST_GAME_ID))
				.andExpect(status().isOk())
				.andExpect(view().name("games/publicRooms"));
	}*/

    @WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/games/save").param("name", "My First Game").param("code", "ABCDF1234").param("privacity", "PUBLIC"))
				.andExpect(status().is3xxRedirection());
	}

}
