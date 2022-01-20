package org.springframework.samples.SevenIslands.player;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.samples.SevenIslands.achievement.AchievementService;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.statistic.StatisticService;
import org.springframework.samples.SevenIslands.user.Authorities;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = PlayerController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class PlayerControllerTests {
    
    private static final int TEST_PLAYER_ID = 22;
    private static final int TEST_NOT_PLAYER_ID= 400;

    @Autowired
	private PlayerController playerController;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PlayerService playerService;

    @MockBean
    private SecurityService securityService;

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

    @MockBean
    private StatisticService statsService;

    private Player otherPlayer;
    private Admin admin;
    private User otherUser;
    private User userAdmin;
    private Authorities auth;
    private Authorities auth2;


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
        userAdmin = new User();
        userAdmin.setUsername("4dm1n");
        userAdmin.setPassword("Us_4dm1n!");
        userAdmin.setEnabled(true);
        auth2 = new Authorities();
        auth2.setAuthority("admin");
        Set<Authorities> st2 = Set.of(auth2);
        userAdmin.setAuthorities(st2);

        admin = new Admin();
        admin.setId(3);
        admin.setEmail("admin@us.es");
        admin.setFirstName("Pepito");
        admin.setSurname("Grillo");
        admin.setUser(userAdmin);


        when(this.playerService.findPlayerById(TEST_PLAYER_ID)).thenReturn(Optional.of(otherPlayer));

	}

    //Method -> listPlayers


    @WithMockUser(value = "spring")
	@Test
	void testListPlayers() throws Exception {

        List<Integer> ls = new ArrayList<>();
        ls.add(0);
        ls.add(1);

        when(securityService.isAdmin()).thenReturn(true);
        when(playerService.calculatePages(any())).thenReturn(ls);

		mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("players"))
                .andExpect(model().attributeExists("filterName"))
                .andExpect(model().attributeExists("pageNumber"))
                .andExpect(model().attributeExists("nextPageNumber"))
                .andExpect(model().attributeExists("previousPageNumber"))
				.andExpect(view().name("players/listPlayers"));
	}


    @WithMockUser(value = "spring")
	@Test
	void testListPlayersNotAdmin() throws Exception {

        List<Integer> ls = new ArrayList<>();
        ls.add(0);
        ls.add(1);

        when(securityService.isAdmin()).thenReturn(false);
        when(playerService.calculatePages(any())).thenReturn(ls);

		mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
				.andExpect(view().name("/error"));
	}

    //Method -> profile

    @WithMockUser(value = "spring")
	@Test
	void testPlayerProfile() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}",TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
				.andExpect(view().name("/players/profile"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPlayerProfileNotPresent() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}",TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
				.andExpect(view().name("/error"));
	}

    //Method -> achievements

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
	void testPlayerProfileNotPresentAchievements() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/achievements", TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
				.andExpect(view().name("/error"));
	}
    

    //Method -> statistics

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
	void testPlayerProfileNotPresentStatistics() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/statistics",TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
				.andExpect(view().name("/error"));
	}

    //Method -> games

    @WithMockUser(value = "spring")
	@Test
	void testRoomsPlayer() throws Exception {
		mockMvc.perform(get("/players/rooms"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("games"))
				.andExpect(view().name("games/publicRooms"));
	}


    //Method -> gamesPlayed

    @WithMockUser(value = "spring")
	@Test
	void testRoomsPlayedInProfilePlayer() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/rooms/played", TEST_PLAYER_ID))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
                .andExpect(model().attributeExists("player"))
				.andExpect(view().name("players/roomsPlayed"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testRoomsPlayedInProfilePlayerNotPresent() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/rooms/played", TEST_NOT_PLAYER_ID))
				.andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
				.andExpect(view().name("/error"));
	}

    //Method -> gamesCreated

    @WithMockUser(value = "spring")
	@Test
	void testRoomsCreatedInProfilePlayer() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/rooms/created", TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("games"))
                .andExpect(model().attributeExists("player"))
				.andExpect(view().name("players/roomsCreated"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testRoomsCreatedInProfilePlayerNotPresent() throws Exception {
		mockMvc.perform(get("/players/profile/{playerId}/rooms/created", TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
				.andExpect(view().name("/error"));
	}

    //Method -> deletePlayer

    @WithMockUser(value="spring")
	@Test
	void testDeletePlayer() throws Exception {

        List<Integer> ls = new ArrayList<>();
        ls.add(0);
        ls.add(1);

        when(playerService.calculatePages(any())).thenReturn(ls);

        when(securityService.isAdmin()).thenReturn(true);

		mockMvc.perform(get("/players/delete/{playerId}", TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player successfully deleted!"))
				.andExpect(view().name("players/listPlayers"));
	}


    @WithMockUser(value="spring")
	@Test
	void testDeletePlayerNotFound() throws Exception {

        List<Integer> ls = new ArrayList<>();
        ls.add(0);
        ls.add(1);

        when(playerService.calculatePages(any())).thenReturn(ls);

        when(securityService.isAdmin()).thenReturn(true);

		mockMvc.perform(get("/players/delete/{playerId}", TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
				.andExpect(view().name("players/listPlayers"));
	}

    @WithMockUser(value="spring")
	@Test
	void testDeletePlayerNotAdmin() throws Exception {

        List<Integer> ls = new ArrayList<>();
        ls.add(0);
        ls.add(1);

        when(playerService.calculatePages(any())).thenReturn(ls);

        when(securityService.isAdmin()).thenReturn(false);

		mockMvc.perform(get("/players/delete/{playerId}", TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
				.andExpect(view().name("/error"));
	}

    //Method -> updatePlayer

    @WithMockUser(value="spring")
    @Test
    void testUpdatePlayer() throws Exception {

        when(securityService.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/players/edit/{playerId}", TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
                .andExpect(view().name("players/createOrUpdatePlayerForm"));

    }

    @WithMockUser(value="spring")
    @Test
    void testUpdatePlayerNotPresent() throws Exception {

        when(securityService.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/players/edit/{playerId}", TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
                .andExpect(view().name("/error"));

    }

    @WithMockUser(value="spring")
    @Test
    void testUpdatePlayerNotAdmin() throws Exception {

        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isAuthenticatedUser()).thenReturn(true);
        when(securityService.getCurrentPlayerId()).thenReturn(TEST_PLAYER_ID);

        mockMvc.perform(get("/players/edit/{playerId}", TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
                .andExpect(view().name("players/createOrUpdatePlayerForm"));

    }

    @WithMockUser(value="spring")
    @Test
    void testUpdateDifferentPlayertNotAdmin() throws Exception {

        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isAuthenticatedUser()).thenReturn(true);
        when(securityService.getCurrentPlayerId()).thenReturn(TEST_PLAYER_ID-1);

        mockMvc.perform(get("/players/edit/{playerId}", TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
                .andExpect(view().name("/error"));

    }

    @WithMockUser(value="spring")
    @Test
    void testUpdatePlayerNotPresentNotAdmin() throws Exception {

        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isAuthenticatedUser()).thenReturn(true);

        mockMvc.perform(get("/players/edit/{playerId}", TEST_NOT_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Player not found"))
                .andExpect(view().name("/error"));

    }

    @WithMockUser(value="spring")
    @Test
    void testUpdatePlayerNotAuthenticatedUser() throws Exception {

        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isAuthenticatedUser()).thenReturn(false);

        mockMvc.perform(get("/players/edit/{playerId}", TEST_PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("/error"));

    }


    //Method -> processUpdateForm

    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePlayerFormWithInappropiateWords() throws Exception {
		
        when(playerService.playerHasInappropiateWords(any())).thenReturn(true);

        mockMvc.perform(post("/players/edit/{playerId}", TEST_PLAYER_ID)
            .with(csrf())
            .param("profilePhoto", "https://imagen.png")
            .param("firstName","Manuel")
            .param("surname","González")
            .param("email","manuelgonzalez@gmail.com"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("player"))
            .andExpect(view().name("players/createOrUpdatePlayerForm"));
	}


    // H15-E1: Invalid username
    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdatePlayerFormWithEmptySpaceInUsername() throws Exception {
		
        when(playerService.playerHasInappropiateWords(any())).thenReturn(false);

        mockMvc.perform(post("/players/edit/{playerId}", TEST_PLAYER_ID)
            .with(csrf())
            .param("profilePhoto", otherPlayer.getProfilePhoto())
            .param("firstName", otherPlayer.getFirstName())
            .param("surname",otherPlayer.getSurname())
            .param("email",otherPlayer.getEmail())
            .param("user.username", otherPlayer.getUser().getUsername() + " editado")
            .param("user.password", otherPlayer.getUser().getPassword()))
            .andExpect(status().isOk())
            .andExpect(model().attribute("errorMessage", "Your username can't contain empty spaces. "))
            .andExpect(view().name("players/createOrUpdatePlayerForm"));
	}


    @WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePlayerFormSuccess() throws Exception {

        when(playerService.playerHasInappropiateWords(any())).thenReturn(false);
        when(playerService.processEditPlayer(any(Player.class), any(Integer.class), any(BindingResult.class))).thenReturn("redirect:/welcome");

		mockMvc.perform(post("/players/edit/{playerId}", TEST_PLAYER_ID)
                .with(csrf())
                .param("profilePhoto", otherPlayer.getProfilePhoto())
                .param("firstName",otherPlayer.getFirstName())
                .param("surname",otherPlayer.getSurname())
                .param("email", otherPlayer.getEmail())
                .param("user.username", otherPlayer.getUser().getUsername() + "9")  //username editado
                .param("user.password", otherPlayer.getUser().getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/welcome"));
	}

    //Method -> playerAuditing

    @WithMockUser(value = "spring")
	@Test
	void testAdminAccessAuditing() throws Exception {
        
        when(securityService.isAdmin()).thenReturn(true);

		mockMvc.perform(get("/players/auditing"))
				.andExpect(status().isOk())
                .andExpect(model().attributeExists("players"))
				.andExpect(view().name("players/auditing"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testPlayerAccessAuditing() throws Exception {

        when(securityService.isAdmin()).thenReturn(false);

		mockMvc.perform(get("/players/auditing"))
				.andExpect(status().isOk())
				.andExpect(view().name("/error"));
	}

}
