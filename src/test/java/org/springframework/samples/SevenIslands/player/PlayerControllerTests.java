package org.springframework.samples.SevenIslands.player;

import org.junit.jupiter.api.Test;
import org.hamcrest.beans.HasProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.achievement.AchievementService;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.user.Authorities;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.context.annotation.FilterType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

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
    private User otherUser;
    private Authorities auth;

    @BeforeEach
	void setup() {

		otherPlayer = new Player();
        otherPlayer.setId(TEST_PLAYER_ID);
        otherPlayer.setProfilePhoto("https://imagen.png");
        otherPlayer.setFirstName("Manuel");
        otherPlayer.setSurname("González");
        otherPlayer.setEmail("manuelgonzalez@gmail.com");
        
        otherUser = new User();
        otherUser.setUsername("manugo44");
        otherUser.setPassword("ManueGonza9!");
        otherUser.setEnabled(true);

        auth = new Authorities();
        auth.setAuthority("player");
        Set<Authorities> st = Set.of(auth);

        otherUser.setAuthorities(st);
        otherPlayer.setUser(otherUser);

        when(this.playerService.findPlayerById(TEST_PLAYER_ID)).thenReturn(Optional.of(otherPlayer));
	}

    @Disabled
    @WithMockUser(value = "spring")
	@Test
	void testListPlayers() throws Exception {
		mockMvc.perform(get("/players")).andExpect(status().isOk())
				.andExpect(view().name("players/listPlayers"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPlayerProfile() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}",TEST_PLAYER_ID))
                .andExpect(status().isOk()).andExpect(model().attributeExists("player"))
				.andExpect(view().name("/players/profile"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPlayerProfileAchievements() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/achievements",TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
                .andExpect(model().attributeExists("achieved"))
                .andExpect(model().attributeExists("notAchieved"))
				.andExpect(view().name("players/achievements"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPlayerProfileStatistics() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/statistics",TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
				.andExpect(view().name("players/statistics"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPlayerRooms() throws Exception {
		mockMvc.perform(get("/players/rooms")).andExpect(status().isOk())
				.andExpect(view().name("games/publicRooms"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testRoomsPlayer() throws Exception {
		mockMvc.perform(get("/players/rooms"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/publicRooms"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProfilePlayer() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/rooms/played", TEST_PLAYER_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("player"))
				.andExpect(view().name("players/roomsPlayed"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testProfile2Player() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/rooms/created", TEST_PLAYER_ID))
				.andExpect(view().name("players/roomsCreated"));
	}

    @Disabled
    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePlayerFormSuccess() throws Exception {
		mockMvc.perform(post("/players/edit/{playerId}", TEST_PLAYER_ID)
        .param("profilePhoto", "https://imagen.png")
        .param("firstName","Manuel")
        .param("surname","González")
        .param("email","manuelgonzalez@gmail.com"))
        //.andExpect(status().is3xxRedirection())
        .andExpect(model().attributeExists("player"))
	    .andExpect(view().name("/players/listPlayers"));
	}

}
