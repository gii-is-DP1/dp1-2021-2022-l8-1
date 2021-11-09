package org.springframework.samples.petclinic.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.Provider.Service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testCountWithInitialData() {
        int count = adminService.getNumberOfAdmins();
        assertEquals(count, 1);
    }
    
}
