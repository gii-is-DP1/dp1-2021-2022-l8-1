package org.springframework.samples.SevenIslands.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.achievement.AchievementService;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayerControllerTests {
    
    private static final int TEST_PLAYER_ID = 22;

    @Autowired
	private PlayerController playerController;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PlayerService playerService;

    @MockBean	
	  private GeneralService generalService;

    @MockBean
    private GameService gameService;

    @MockBean	
	  private UserService userService;
    
    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean
    private AchievementService achievementService;




    private Player otherPlayer;

    @BeforeEach
	void setup() {

		otherPlayer = new Player();
        otherPlayer.setId(TEST_PLAYER_ID);
        otherPlayer.setFirstName("Manuel");
        otherPlayer.setSurname("González");
        otherPlayer.setEmail("manuelgonzalez@gmail.com");

	}

    @WithMockUser(value = "spring")
	@Test
	void testPlayerRooms() throws Exception {
		mockMvc.perform(get("/players/rooms")).andExpect(status().isOk())
				.andExpect(view().name("games/publicRooms"));
	}

}
