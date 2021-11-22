package org.springframework.samples.SevenIslands.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.stereotype.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AdminServiceTests {

    @Autowired
    private AdminService adminService;

    @Test
    public void testCountWithInitialData() {
        int count = adminService.getNumberOfAdmins();
        assertEquals(count, 1);
    }


    
}
