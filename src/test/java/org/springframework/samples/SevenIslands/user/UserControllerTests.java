package org.springframework.samples.SevenIslands.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class UserControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private UserService userService;

    
    @WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception{

        
        mockMvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
                .andExpect(view().name("players/createOrUpdatePlayerForm"));
    }


    // H12-E2: Carácteres no alfanuméricos (espacio) en nombre de usuario
    
    @WithMockUser(value = "spring")
    @Test
    void testProcessCreationForm() throws Exception{

        when(playerService.playerHasInappropiateWords(any(Player.class))).thenReturn(false);

        mockMvc.perform(post("/users/new")
                .with(csrf())
                .param("profilePhoto", "https://image.png")
                .param("firstName", "Ismael")
                .param("surname", "Perez")
                .param("email", "ismperort@alum.us.es")
                .param("user.username", "ISMP 15")
                .param("user.password", "Us_2k214dm1n"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
                .andExpect(model().attribute("errorMessage", "Your username can't contain empty spaces. "))
                .andExpect(view().name("players/createOrUpdatePlayerForm"));
 
        

    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormWithInappropiateWords() throws Exception{

        when(playerService.playerHasInappropiateWords(any(Player.class))).thenReturn(true);

        mockMvc.perform(post("/users/new")
                .with(csrf())
                .param("profilePhoto", "https://image.png")
                .param("firstName", "Manuel")
                .param("surname", "Gonzalez")
                .param("email", "mangonmor@alum.us.es")
                .param("user.username", "fuckingManu12")
                .param("user.password", "Us_2k214dm1n"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("player"))
                .andExpect(model().attribute("errorMessage", "Your profile can't contain inappropiate words. Please, check your language."))
                .andExpect(view().name("players/createOrUpdatePlayerForm"));
 
        

    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessCreationFormSuccesfull() throws Exception{

        when(playerService.playerHasInappropiateWords(any(Player.class))).thenReturn(false);

        mockMvc.perform(post("/users/new")
                .with(csrf())
                .param("profilePhoto", "https://image.png")
                .param("firstName", "Manuel")
                .param("surname", "Gonzalez")
                .param("email", "mangonmor@alum.us.es")
                .param("user.username", "manu12")
                .param("user.password", "Us_2k214dm1n"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
 
    
    }
    
}
