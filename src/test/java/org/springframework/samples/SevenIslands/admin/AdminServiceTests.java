package org.springframework.samples.SevenIslands.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.user.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdminServiceTests {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Test
    public void testCountWithInitialData() {
        int count = adminService.getNumberOfAdmins();
        assertEquals(count, 1);
    }

    @Test
    public void testGetIdByName() {
        int p = adminService.getIdAdminByName("ISMP15");
        assertEquals(1, p);
    }

    @Test
    public void testGetAdminByName() {
        Admin a = adminService.getAdminByName("ISMP15").get();
        assertEquals(a.getId(), 1);
    }

    @Test
    public void testGetAdminById() {
        Admin a = adminService.findAdminById(1).get();
        assertEquals(a.getFirstName(), "Ismael");
    }

    @Test
    public void shouldInsertAdmin(){

        Admin a = new Admin();
        a.setEmail("t@gmail.com");
        a.setFirstName("Antonio");
        a.setSurname("Alonso");
        a.setUser(userService.findUser("test1").get());

        
        adminService.save(a);
        assertThat(a.getId().longValue()).isNotEqualTo(0);
        assertThat(adminService.getAdminByName("test1").get().getSurname().toString()).isEqualTo("Alonso");


    }

    
}
