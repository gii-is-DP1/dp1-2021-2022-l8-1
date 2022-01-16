package org.springframework.samples.SevenIslands.achievement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = AchievementController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AchievementControllerTests {
    
    private static final int TEST_ACHIEVEMENT_ID = 22;

    @Autowired
	private AchievementController achievementController;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AchievementService achievementService;

    @MockBean
	private SecurityService securityService;

    private Achievement achievementWins;

    @BeforeEach
	void setup() {

		achievementWins = new Achievement();
        achievementWins.setId(TEST_ACHIEVEMENT_ID);
        achievementWins.setDescription("Wins 1000 games");
        achievementWins.setIcon("http:image.png");
        achievementWins.setAchievementType(ACHIEVEMENT_TYPE.GOLD);
        achievementWins.setName("Amazing");
        achievementWins.setMinValue(1000);
        achievementWins.setParameter(PARAMETER.WINS);
        achievementWins.setVersion(0);
	
        when(securityService.isAdmin()).thenReturn(true);
        when(achievementService.findAchievementById(TEST_ACHIEVEMENT_ID)).thenReturn(Optional.of(achievementWins));
    
    }


    //H23+E1

    @WithMockUser(value = "spring")
	@Test
	void testShowFormNewAchievement() throws Exception {

		mockMvc.perform(get("/achievements/new"))
                .andExpect(status().isOk()).andExpect(model().attributeExists("achievement"))
				.andExpect(view().name("achievements/createOrUpdateAchievementForm"));
	}

    @WithMockUser(value = "spring")
	@Test
	void testNewAchievement() throws Exception {

		mockMvc.perform(post("/achievements/save")
                .with(csrf())
                .param("description", "Wins 1000 games")
                .param("icon", "http:image.png")
                .param("name", "Amazing Achievement")
                .param("minValue", "1000")
                .param("achievementType",ACHIEVEMENT_TYPE.GOLD.toString())
                .param("parameter", PARAMETER.WINS.toString())
                .param("version", "0"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/achievements"));
	}

    //H23+E2

    @WithMockUser(value = "spring")
	@Test
	void testShowFormEditAchievement() throws Exception {

		mockMvc.perform(get("/achievements/edit/{achievementId}", TEST_ACHIEVEMENT_ID))
                .andExpect(model().attributeExists("achievement"))
                .andExpect(view().name("achievements/createOrUpdateAchievementForm"));
	}

    @Disabled
    @WithMockUser(value = "spring")
	@Test
	void testEditAchievement() throws Exception {

		mockMvc.perform(post("/achievements/edit/{achievementId}", TEST_ACHIEVEMENT_ID)
                .with(csrf())
                .param("description", "Wins 2000 games")
                .param("icon", "http:image.png")
                .param("name", "Amazing Achievement 2")
                .param("minValue", "2000")
                .param("achievementType",ACHIEVEMENT_TYPE.GOLD.toString())
                .param("parameter", PARAMETER.WINS.toString())
                .param("version", "1"))
                .andExpect(model().attributeExists("achievement"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/achievements"));
	}

    //H23-E1

    //Check insert repeated achievement

    @Disabled
    @WithMockUser(value = "spring")
	@Test
	void testNewAchievementWithError() throws Exception {

		mockMvc.perform(post("/achievements/save")
                .with(csrf())
                .param("description", "Wins 1000 games")
                .param("icon", "http:image.png")
                .param("name", "Amazing")
                .param("minValue", "1000")
                .param("achievementType",ACHIEVEMENT_TYPE.GOLD.toString())
                .param("parameter", PARAMETER.WINS.toString())
                .param("version", "0"))
                .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/achievements"));
	}


    //H23 - E2

    //Edit an achievement with empty name field
    @WithMockUser(value = "spring")
	@Test
	void testEditAchievementWithError() throws Exception {

		mockMvc.perform(post("/achievements/edit/{achievementId}", TEST_ACHIEVEMENT_ID)
                .with(csrf())
                .param("description", "Wins 1000 games")
                .param("icon", "http:image.png")
                .param("name", "")
                .param("minValue", "1000")
                .param("achievementType",ACHIEVEMENT_TYPE.GOLD.toString())
                .param("parameter", PARAMETER.WINS.toString())
                .param("version", "1"))
                .andExpect(status().isOk())
				.andExpect(view().name("achievements/createOrUpdateAchievementForm"));
	}


}
