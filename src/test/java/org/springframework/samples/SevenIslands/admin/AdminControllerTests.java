package org.springframework.samples.SevenIslands.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.configuration.SecurityConfiguration;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.SevenIslands.user.Authorities;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.Spliterators;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.mockito.Mockito.when;



@WebMvcTest(controllers = AdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminControllerTests {
    @Autowired
	private AdminController adminController;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AdminService adminService;

    @MockBean
	private GameService gameService;

    @MockBean
    private AuthoritiesService authoritiesService;

    @MockBean	
    private UserService userService;



    @MockBean
	private SecurityService securityService;


    private Admin admin;
    private User user;
    private Authorities auth;
    private static final int TEST_ADMIN_ID = 1;
    private static final int TEST_NOT_ADMIN_ID = 10;
    private static final String ERROR = "/error";
    private static final String VIEW_ADMINS_CREATE_OR_UPDATE_FORM = "admins/createOrUpdateAdminForm";

    @BeforeEach
    void setup() {

        user = new User();
        user.setEnabled(true);
        user.setUsername("ismp_19");
        user.setPassword("Us_2k224dm1n");
        auth = new Authorities();
        auth.setAuthority("admin");
        auth.setId(TEST_ADMIN_ID);
        auth.setUser(user);
        Set<Authorities> st2 = Set.of(auth);
        user.setAuthorities(st2);

        admin = new Admin();
        admin.setId(TEST_ADMIN_ID);
        admin.setFirstName("Ismael");
        admin.setSurname("Perez");
        admin.setEmail("ismp_19@us.es");
        admin.setUser(user);


        List<Game> ls = new ArrayList<>();
        ls.add(new Game());
        Iterator<Game> iterator = ls.iterator();
        Iterable<Game> iterable = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0),false).collect(Collectors.toList());



        when(adminService.findAdminById(TEST_ADMIN_ID)).thenReturn(Optional.of(admin));
        when(securityService.isAdmin()).thenReturn(true);
        when(gameService.findAll()).thenReturn(iterable);

    }

    @WithMockUser(value = "spring")
    @Test
    void testProfile() throws Exception {
        mockMvc.perform(get("/admins/profile/{adminId}", TEST_ADMIN_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("admin"))
            .andExpect(view().name("admins/profile"));
        
    }

    @WithMockUser(value = "spring")
    @Test
    void testProfileNotPresent() throws Exception {
        mockMvc.perform(get("/admins/profile/{adminId}", TEST_NOT_ADMIN_ID))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "admin not found"))
            .andExpect(view().name(ERROR));
        
    }

    @WithMockUser(value = "spring")
    @Test
    void testRooms() throws Exception {
        mockMvc.perform(get("/admins/rooms"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("games"))
            .andExpect(view().name("admins/rooms"));
        
    }


    @WithMockUser(value = "spring")
    @Test
    void testUpdateAdmin() throws Exception {
        mockMvc.perform(get("/admins/profile/edit/{adminId}", TEST_ADMIN_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("admin"))
            .andExpect(view().name(VIEW_ADMINS_CREATE_OR_UPDATE_FORM));
        
    }

    @WithMockUser(value = "spring")
    @Test
    void testUpdateAdminNotPresent() throws Exception {
        mockMvc.perform(get("/admins/profile/edit/{adminId}", TEST_NOT_ADMIN_ID))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "admin not found"))
            .andExpect(view().name(ERROR));
        
    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateForm() throws Exception {

        mockMvc.perform(post("/admins/profile/edit/{adminId}", TEST_ADMIN_ID)
            .with(csrf())
            .param("firstName", admin.getFirstName())
            .param("surname", admin.getSurname())
            .param("email", admin.getEmail())
            .param("user.username", admin.getUser().getUsername() + "_v2")
            .param("user.password", admin.getUser().getPassword()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/welcome"));
    }


    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormNotPresent() throws Exception {

        mockMvc.perform(post("/admins/profile/edit/{adminId}", TEST_NOT_ADMIN_ID)
            .with(csrf())
            .param("firstName", admin.getFirstName())
            .param("surname", admin.getSurname())
            .param("email", admin.getEmail())
            .param("user.username", admin.getUser().getUsername() + "_v2")
            .param("user.password", admin.getUser().getPassword()))
            .andExpect(status().isOk())
            .andExpect(model().attribute("message", "admin not found"))
            .andExpect(view().name(ERROR));
    }

    @WithMockUser(value = "spring")
    @Test
    void testProcessUpdateFormNotChangingUsername() throws Exception {

        mockMvc.perform(post("/admins/profile/edit/{adminId}", TEST_ADMIN_ID)
            .with(csrf())
            .param("firstName", admin.getFirstName() + "_v2")
            .param("surname", admin.getSurname())
            .param("email", admin.getEmail())
            .param("user.username", admin.getUser().getUsername())
            .param("user.password", admin.getUser().getPassword()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/admins/profile/" + TEST_ADMIN_ID));
    }


    
    
}
