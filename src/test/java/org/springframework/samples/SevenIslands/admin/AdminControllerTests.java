package org.springframework.samples.SevenIslands.admin;



import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.samples.SevenIslands.user.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;





@WebMvcTest(controllers = AdminController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class AdminControllerTests {

    // private static final int TEST_ACHIEVEMENT_ID = 22;
    // private static final int TEST_NOT_ACHIEVEMENT_ID = 222;

    @Autowired
	private AdminController adminController;

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AdminService adminService;

    @MockBean
	private SecurityService securityService;


    private Admin admin;
    private User user;
    private static final int TEST_ADMIN_ID = 1;

    @BeforeEach
    void setup() {
        admin = new Admin();
        admin.setId(TEST_ADMIN_ID);
        admin.setFirstName("Ismael");
        admin.setSurname("Perez");
        user.setEnabled(true);
        user.setUsername("ismp_19");
        user.setPassword("Us_2k224dm1n");
        admin.setUser(user);
        
    }

    @WithMockUser(value = "spring")
    @Test
    void testProfile() throws Exception {
        mockMvc.perform(get("/admins/profile/{adminId}", TEST_ADMIN_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("admin"))
            .andExpect(view().name("admins/profile"));
        
    }

    
    
}
